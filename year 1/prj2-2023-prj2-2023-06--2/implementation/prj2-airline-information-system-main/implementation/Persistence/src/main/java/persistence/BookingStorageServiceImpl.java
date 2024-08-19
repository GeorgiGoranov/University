package persistence;

import datarecords.BookingData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingStorageServiceImpl implements BookingStorageService{
    private final DataSource db = DatabaseConnector.getDataSource("prj2_db");
    private final Connection conn = db.getConnection();

    private final PersistenceBookingValidator persistenceBookingValidator = new PersistenceBookingValidator();

    public BookingStorageServiceImpl() throws SQLException {
    }

    @Override
    public BookingData add(BookingData bookingData) {
        if (persistenceBookingValidator.isInValidData(bookingData, "Booking")){
            return null;
        }

        return new BookingData(bookingData.bookingDate(), bookingData.customerID(), bookingData.flightIDs(), bookingData.passengerIDs());
    }

    @Override
    public List<BookingData> getAll() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    //Database connection info
//    static final String DB_URL = "jdbc:postgresql://localhost:5434/postgres";
//    static final String DB_USER = "G6";
//    static final String DB_PASS = "PRJ2ftw";

//    private DataSource db = DatabaseConnector.getDataSource("prj2_db");
//    private Connection conn = db.getConnection();

//    public static Connection connectDb(){
//        try{
//            Class.forName("org.postgresql.Driver");
//            Connection conn = DriverManager.getConnection(DB_URL,DB_USER, DB_PASS);
//            //JOptionPane.showMessageDialog(null,"ConnectionEstablished");
//            return conn;
//
//        }catch (Exception e){
//            JOptionPane.showMessageDialog(null,e);
//            return null;
//        }
//    }
    public String insertBookingData(BookingData bookingData) {
        if (persistenceBookingValidator.isInValidData(bookingData, "Booking")){
            return null;
        }
        // Open a connection to the database
        // Assuming you have collected the necessary information in your JavaFX application
        Date bookingDate = Date.valueOf(bookingData.bookingDate());
        String customerID = bookingData.customerID();
        List<String> flightIDs = bookingData.flightIDs();
        List<String> passengerIDs = bookingData.passengerIDs();
        float price = 0.0f;
        String bookingID = null;

        try  {
            // Insert into the Booking table
            String bookingQuery = "INSERT INTO BookingData (bookingDate, customerID) VALUES (?,?)";
            PreparedStatement bookingStatement = conn.prepareStatement(bookingQuery, Statement.RETURN_GENERATED_KEYS);
            bookingStatement.setDate(1, bookingDate);
            bookingStatement.setString(2, customerID);
            bookingStatement.executeUpdate();

            // Get the generated booking ID
            ResultSet generatedKeys = bookingStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                bookingID = generatedKeys.getString(1);
            } else {
                throw new SQLException("Failed to retrieve generated booking ID.");
            }
            generatedKeys.close();
            bookingStatement.close();

            // Insert into the BookingFlight table
            String bookingFlightQuery = "INSERT INTO BookingFlight (bookingID, flightID) VALUES (?, ?)";
            PreparedStatement bookingFlightStatement = conn.prepareStatement(bookingFlightQuery);
            for (String flightID : flightIDs) {
                bookingFlightStatement.setString(1, bookingID);
                bookingFlightStatement.setString(2, flightID);
                bookingFlightStatement.addBatch();
            }
            bookingFlightStatement.executeBatch();
            bookingFlightStatement.close();

            // Insert into the BookingPassenger table
            String bookingPassengerQuery = "INSERT INTO BookingPassenger (bookingID, passengerID) VALUES (?, ?)";
            PreparedStatement bookingPassengerStatement = conn.prepareStatement(bookingPassengerQuery);
            for (String passengerID : passengerIDs) {
                bookingPassengerStatement.setString(1, bookingID);
                bookingPassengerStatement.setString(2, passengerID);
                bookingPassengerStatement.addBatch();
            }
            bookingPassengerStatement.executeBatch();
            bookingPassengerStatement.close();

            for (String flight : flightIDs) {
                float flightPrice = this.getPrice(flight);
                price += flightPrice * passengerIDs.size();
            }

            // Insert price into bookingData table
            String bookingPriceQuery = "UPDATE BookingData SET price = ? WHERE bookingID = ?";
            PreparedStatement bookingPricePS = conn.prepareStatement(bookingPriceQuery);
            bookingPricePS.setFloat(1, price);
            bookingPricePS.setString(2, bookingID);
            bookingPricePS.executeUpdate();

            System.out.println("Booking created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException within the method
            System.out.println("Failed to create booking: " + e.getMessage());
            bookingID = null; // Set bookingID to null since the booking creation failed
        }

        return bookingID;
    }

    public Float getBookingPrice(String bookingID) {
        Float price = null;
        try {
            String query = "SELECT price FROM BookingData WHERE bookingID = ?";
            PreparedStatement psl = conn.prepareStatement(query);
            psl.setString(1, bookingID);
            ResultSet rsl = psl.executeQuery();

            if (rsl.next()) {
                price = rsl.getFloat("price");
            }

            rsl.close();
            psl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }

    public ObservableList<GetInfoForBookings> getDataForBookings(){


        ObservableList<GetInfoForBookings> list = FXCollections.observableArrayList(
//               new GetInfoForBookings(1,2,"b",4)
        );
        try(
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM BookingData");){
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new GetInfoForBookings(rs.getString("bookingID"),
                        rs.getString("customerID"),
                        rs.getDate("bookingDate").toLocalDate()));
            }

        }catch (Exception e){

        }

        return list;
    }

    public BookingData getPassenger(String BookingId){
        //TODO: no idea what you need to do with the queries however i have guess that is that you are not retrieving anything becaue of the ('" + BookingId +"'")
        try(
            PreparedStatement ps1 = conn.prepareStatement("SELECT flightID FROM BookingFlight WHERE bookingID = '" + BookingId +"'");
            PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM BookingData WHERE bookingID = '" + BookingId +"'");
            PreparedStatement ps3 = conn.prepareStatement("SELECT passengerID FROM BookingPassenger WHERE bookingID = '" + BookingId +"'");
        ){
            List<String> flightIDs = new ArrayList<>();
            List<String> passengerIDs = new ArrayList<>();


            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()){

                String cID = rs2.getString(2);
                Date bookingDate = rs2.getDate(3);

                ResultSet rs3 = ps3.executeQuery();
                while (rs3.next()){
                    passengerIDs.add(rs3.getString(1));
                }
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()){
                    flightIDs.add(rs1.getString(1));
                }

                return new BookingData(bookingDate.toLocalDate(), cID, flightIDs, passengerIDs);
            }


        }catch (Exception e){

        }

        return null;
    }

    //method for data validation
//    public boolean validData(BookingData bookingData){
//        if (bookingData == null) {
//            return false;
//
//        }
//        if (bookingData.passengerIDs().isEmpty()){
//            return false;
//
//        }
//        if (bookingData.flightIDs().isEmpty()){
//
//            return false;
//        }
//
//        LocalDate todaysDate = LocalDate.now();
//        return !bookingData.bookingDate().isAfter(todaysDate);
//    }
    public  float getPrice(String providedFlightID) {
//        Connection conn = ConnectDb();
        Float price = null;
        try {
            String query = "SELECT price FROM FlightData WHERE flightID = ?";
            PreparedStatement psl = conn.prepareStatement(query);
            psl.setString(1, providedFlightID);
            ResultSet rsl = psl.executeQuery();

            if (rsl.next()) {
                price = rsl.getFloat("price");
            }

            rsl.close();
            psl.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }



}
