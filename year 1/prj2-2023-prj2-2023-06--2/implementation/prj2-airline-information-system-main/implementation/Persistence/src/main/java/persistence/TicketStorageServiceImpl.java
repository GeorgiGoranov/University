package persistence;

import datarecords.TicketData;

import javax.sql.DataSource;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketStorageServiceImpl implements TicketStorageService{
    private final DataSource db = DatabaseConnector.getDataSource("prj2_db");
    private  final Connection conn = db.getConnection();
    TicketStorageServiceImpl() throws SQLException{};
//    static final String DB_URL = "jdbc:postgresql://localhost:5434/postgres";
//    static final String DB_USER = "G6";
//    static final String DB_PASS = "PRJ2ftw";

//    public static Connection ConnectDb() {
//        try {
//            Class.forName("org.postgresql.Driver");
//            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//            //JOptionPane.showMessageDialog(null,"ConnectionEstablished");
//            return conn;
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//            return null;
//        }
//    }

    public void saveTicket(TicketData ticketData){
        try  {

        //need flight ID

        // Prepare a SQL statement to insert a new row into the FlightData table
        String sql = "INSERT INTO TicketData (seat, options, flightId, bookingId, passengerId) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set the values of the parameters in the SQL statement

            stmt.setString(1, ticketData.seat());
            stmt.setString(2, ticketData.options());
            stmt.setString(3, ticketData.flightId());
            stmt.setString(4, ticketData.bookingId());
            stmt.setString(5, ticketData.passengerId());

            // Execute the SQL statement to insert the new row
            stmt.executeUpdate();
        }
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TicketData add(TicketData ticketData){
        return new TicketData(ticketData.seat(),ticketData.options(), ticketData.flightId(), ticketData.bookingId(), ticketData.passengerId());
    }
    @Override
    public List<TicketData> getAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }



    public String getChosenSeatsForPassenger(String bookingId, String flightId, String passengerId){

        try(
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM TicketData WHERE bookingId ='" + bookingId +"' AND flightId = '" + flightId +"' AND passengerId ='"+ passengerId +"'");){

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                return rs.getString("seat");
            }

        }catch (Exception e){

        }

        return null;
    }

    public TicketData getSpecifficTicket(String bookingId, String flightId, String passengerId){


        try(
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM TicketData WHERE bookingId ='" + bookingId +"' AND flightId = '" + flightId +"' AND passengerId ='"+ passengerId +"'");){
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String seat = rs.getString("seat");
                String options = rs.getString("options");
                String flightIdColum = rs.getString("flightId");
                String bookingIdColum = rs.getString("bookingId");
                String passengeridColum = rs.getString("passengerId");

                return new TicketData(seat, options, flightIdColum, bookingIdColum, passengeridColum);

            }

        }catch (Exception e){

        }

        return null;
    }

    public List<String> getChosenSeats(String bookingId, String flightId){
        List<String> values = new ArrayList<>();

        try(
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM TicketData WHERE bookingId ='" + bookingId +"' AND flightId = '" + flightId +"'");){

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                values.add(rs.getString("seat"));

            }

        }catch (Exception e){

        }

        return values;
    }
}
