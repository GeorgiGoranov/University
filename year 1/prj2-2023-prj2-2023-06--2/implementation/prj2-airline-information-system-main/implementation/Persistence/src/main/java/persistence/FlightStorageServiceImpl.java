package persistence;

import datarecords.FlightData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.exceptions.PersistenceException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

class FlightStorageServiceImpl implements FlightStorageService {
//    static final String DB_URL = "jdbc:postgresql://localhost:5434/postgres";
//    static final String DB_USER = "G6";
//    static final String DB_PASS = "PRJ2ftw";
    private final DataSource db = DatabaseConnector.getDataSource("prj2_db");
    private  final Connection conn = db.getConnection();
    FlightStorageServiceImpl() throws SQLException{};

//    Connection conn = null;
//
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



    public void insertFlightIntoFlightData(FlightData flightData) {
//        Connection conn = ConnectDb();
        try{
            PreparedStatement ps = conn.prepareStatement("INSERT INTO FlightData (flightid,planeid,departureAirport, arrivalAirport, actualDepartureTime, actualArrivalTime, dateOfDeparture, dateOfArrival, sdiscount, ddiscount, price) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1,flightData.flightId());
            ps.setString(2,flightData.planeID());
            ps.setString(3,flightData.depAirport());
            ps.setString(4,flightData.arrAirport());
            ps.setString(5,flightData.depTime());
            ps.setString(6,flightData.arrTime());
            ps.setDate(7, Date.valueOf(flightData.DoD()));
            ps.setDate(8, Date.valueOf(flightData.DoA()));
            ps.setBoolean(9,flightData.sdiscount());
            ps.setBoolean(10,flightData.ddiscount());
            ps.setFloat(11,flightData.price());
            ps.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void insertIntoRecurringFlights(GetInfoForRecurringFlights getInfoForRecurringFlights) {
//        Connection conn = ConnectDb();
        try {
            PreparedStatement selectDateOfDepartureAndArrival = conn.prepareStatement("SELECT dateOfDeparture, dateOfArrival FROM flightdata WHERE flightid = ?");
            selectDateOfDepartureAndArrival.setString(1, getInfoForRecurringFlights.getDuplicatedFlightId());
            ResultSet rss = selectDateOfDepartureAndArrival.executeQuery();

            // Move the cursor to the first row
            if (rss.next()) {
                PreparedStatement selectPs = conn.prepareStatement("SELECT * FROM flightdata WHERE flightid = ? AND dateOfDeparture = ? AND dateOfArrival = ?");
                selectPs.setString(1, getInfoForRecurringFlights.duplicatedFlightId);
                selectPs.setDate(2, Date.valueOf(rss.getString("dateOfDeparture")));
                selectPs.setDate(3, Date.valueOf(rss.getString("dateOfArrival")));
                ResultSet rs = selectPs.executeQuery();

                PreparedStatement ps = conn.prepareStatement("INSERT INTO FlightData(flightid,planeid,departureAirport, arrivalAirport, actualDepartureTime, actualArrivalTime, dateOfDeparture, dateOfArrival, sdiscount, ddiscount, price)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                while (rs.next()) {
                    ps.setString(1, getInfoForRecurringFlights.getDuplicatedFlightId());
                    ps.setString(2, rs.getString("planeid"));
                    ps.setString(3, rs.getString("departureairport"));
                    ps.setString(4, rs.getString("arrivalairport"));
                    ps.setString(5, rs.getString("actualdeparturetime"));
                    ps.setString(6, rs.getString("actualarrivaltime"));
                    ps.setDate(7, Date.valueOf(getInfoForRecurringFlights.getDateOfDeparture()));
                    ps.setDate(8, Date.valueOf(getInfoForRecurringFlights.getDateOfArrival()));
                    ps.setBoolean(9, rs.getBoolean("sdiscount"));
                    ps.setBoolean(10, rs.getBoolean("ddiscount"));
                    ps.setInt(11, rs.getInt("price"));
                    ps.executeUpdate();
                }
            } else {
                // Handle the case when no rows are returned
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  ObservableList<GetInfoForCanceledFlights> getDataForDeletedFlights() {

//        Connection conn = ConnectDb();
        ObservableList<GetInfoForCanceledFlights> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM canceledFlights");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new GetInfoForCanceledFlights(rs.getInt("idofdeletedflight"),
                        rs.getString("flightid"),
                        rs.getString("reasonforcancelation")));
            }

        } catch (Exception e) {

        }

        return list;

    }

    public  ObservableList<GetInfoForFlights> getDataForFlights() {

//        Connection conn = ConnectDb();
        ObservableList<GetInfoForFlights> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM FlightData ORDER BY flightid ASC");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new GetInfoForFlights(rs.getString("flightid"),
                        rs.getString("planeid"),
                        rs.getString("departureairport"),
                        rs.getString("arrivalairport"),
                        rs.getString("actualdeparturetime"),
                        rs.getString("actualarrivaltime"),
                        rs.getString("dateofdeparture"),
                        rs.getString("dateofarrival"),
                        rs.getBoolean("sdiscount"),
                        rs.getBoolean("ddiscount"),
                        rs.getInt("price")));
            }

        } catch (Exception e) {

        }

        return list;
    }

    public  void insertIntoDeletedTable(String deletedFlightID, String reason) {
//        Connection conn = ConnectDb();
        try{

            PreparedStatement selectDateOfDepartureAndArrival = conn.prepareStatement("SELECT dateOfDeparture, dateOfArrival FROM flightdata WHERE flightid = ?");
            selectDateOfDepartureAndArrival.setString(1, deletedFlightID);
            ResultSet rss = selectDateOfDepartureAndArrival.executeQuery();

            if (rss.next()){
                PreparedStatement selectPs = conn.prepareStatement("SELECT * FROM flightdata WHERE flightid = ? AND dateOfDeparture = ? AND dateOfArrival = ?");
                selectPs.setString(1, deletedFlightID);
                selectPs.setDate(2, Date.valueOf(rss.getString("dateOfDeparture")));
                selectPs.setDate(3, Date.valueOf(rss.getString("dateOfArrival")));
                ResultSet rs = selectPs.executeQuery();

                // Insert the retrieved data into canceledFlights table
                PreparedStatement insertPs = conn.prepareStatement("INSERT INTO canceledFlights (flightID, planeid,departureairport, arrivalairport, actualdeparturetime, actualarrivaltime, dateofdeparture, dateofarrival, sdiscount, ddiscount, price, reasonforcancelation) VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?,?)");
                while (rs.next()) {
                    insertPs.setString(1, rs.getString("flightid"));
                    insertPs.setString(2,rs.getString("planeid"));
                    insertPs.setString(3, rs.getString("departureairport"));
                    insertPs.setString(4, rs.getString("arrivalairport"));
                    insertPs.setString(5, rs.getString("actualdeparturetime"));
                    insertPs.setString(6, rs.getString("actualarrivaltime"));
                    insertPs.setDate(7, Date.valueOf(rs.getString("dateofdeparture")));
                    insertPs.setDate(8, Date.valueOf(rs.getString("dateofarrival")));
                    insertPs.setBoolean(9, rs.getBoolean("sdiscount"));
                    insertPs.setBoolean(10, rs.getBoolean("ddiscount"));
                    insertPs.setInt(11, rs.getInt("price"));
                    insertPs.setString(12,reason);
                    insertPs.executeUpdate();
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void deleteFlight(String id) {
//        Connection conn = ConnectDb();
        try {
            PreparedStatement selectDateOfDepartureAndArrival = conn.prepareStatement("SELECT dateOfDeparture, dateOfArrival FROM flightdata WHERE flightid = ?");
            selectDateOfDepartureAndArrival.setString(1, id);
            ResultSet rss = selectDateOfDepartureAndArrival.executeQuery();

            if (rss.next()) {
                PreparedStatement selectPs = conn.prepareStatement("SELECT * FROM flightdata WHERE flightid = ? AND dateOfDeparture = ? AND dateOfArrival = ?");
                selectPs.setString(1, id);
                selectPs.setDate(2, Date.valueOf(rss.getString("dateOfDeparture")));
                selectPs.setDate(3, Date.valueOf(rss.getString("dateOfArrival")));
                ResultSet rs = selectPs.executeQuery();


                // Delete the flight from the FlightData table
                PreparedStatement deleteFlight = conn.prepareStatement("DELETE FROM flightdata WHERE flightid = ? AND dateOfDeparture = ? AND dateOfArrival = ?");
                deleteFlight.setString(1, id);
                deleteFlight.setDate(2, Date.valueOf(rss.getString("dateOfDeparture")));
                deleteFlight.setDate(3, Date.valueOf(rss.getString("dateOfArrival")));
                deleteFlight.executeUpdate();


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> getTimeOptionsForHour() {
//        Connection conn = ConnectDb();

        ObservableList<String> list = FXCollections.observableArrayList();


        try {
            PreparedStatement ps = conn.prepareStatement("SELECT houroption FROM timeoptions");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] values = rs.getString("houroption").split(",");
                for (String value : values) {
                    list.add(value.trim());
                }
            }

        } catch (Exception e) {

        }
        return list;
    }

    public ObservableList<String> getTimeOptionsForMinutes() {
//        Connection conn = ConnectDb();

        ObservableList<String> list1 = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT minuteoptions FROM timeoptions");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String[] values = rs.getString("minuteoptions").split(",");
                for (String value : values) {
                    list1.add(value.trim());
                }
            }

        } catch (Exception e) {

        }
        return list1;
    }

    public  ObservableList<String> getAirports() {
//        Connection conn = ConnectDb();

        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM airports");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("nameofairport"));
            }

        } catch (Exception e) {

        }
        return list;
    }

    public  ObservableList<String> getPlaneIDs() {
//        Connection conn = ConnectDb();
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            PreparedStatement psl = conn.prepareStatement("SELECT planeID FROM planes");
            ResultSet rsl = psl.executeQuery();

            while (rsl.next()) {
                list.add(rsl.getString("planeID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateFlightData(FlightData flightData, String selectedFlightID) {
//        Connection conn = ConnectDb();

            // Prepare a SQL statement to insert a new row into the FlightData table
            //add sortation for flight ID order
            String sql = "UPDATE FlightData SET departureAirport = ?, arrivalAirport = ?, actualDepartureTime = ?, actualArrivalTime = ?, dateOfDeparture = ?, dateOfArrival = ?, planeID = ?, sdiscount = ?, ddiscount = ?, price = ? WHERE flightid = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Set the values of the parameters in the SQL statement
                stmt.setString(1, flightData.depAirport());
                stmt.setString(2, flightData.arrAirport());
                stmt.setString(3, flightData.depTime());
                stmt.setString(4, flightData.arrTime());
                stmt.setDate(5, Date.valueOf(flightData.DoD()));
                stmt.setDate(6, Date.valueOf(flightData.DoA()));
                stmt.setString(7, flightData.planeID());
                stmt.setBoolean(8, (flightData.sdiscount()));
                stmt.setBoolean(9, (flightData.ddiscount()));
                stmt.setFloat(10, flightData.price());
                stmt.setString(11, selectedFlightID);



                // Execute the SQL statement to insert the new row
                stmt.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }

    }

    @Override
    public FlightData add(FlightData flightData){

        return new FlightData(flightData.flightId(),flightData.planeID(), flightData.depAirport(), flightData.arrAirport(), flightData.depTime(), flightData.arrTime(), flightData.DoD(), flightData.DoA(), flightData.sdiscount(),flightData.ddiscount(), flightData.price());

    }

    public ObservableList<GetPlaneInfo> GetPlaneInfo(){
//        Connection conn = ConnectDb();
        ObservableList<GetPlaneInfo> list = FXCollections.observableArrayList();
        try{
            PreparedStatement psl = conn.prepareStatement("SELECT * FROM planes");
            ResultSet rsl = psl.executeQuery();

            while (rsl.next()) {
                list.add(new GetPlaneInfo(rsl.getString("planeID"),
                        rsl.getString("planeType"),
                        rsl.getInt("seats")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


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
        } finally {
            // Close the database connection
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return price;
    }



    public void changeStaticDiscountt(String id, boolean newSDiscount) throws SQLException {
        // Open a connection to the database
        try {
            // Prepare a SQL statement to insert a new row into the FlightData table
            //add sortation for flight ID order
            String sql = "UPDATE FlightData SET sdiscount = ? WHERE flightid = ? ";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Set the values of the parameters in the SQL statement

                stmt.setBoolean(1, (newSDiscount));
                stmt.setString(2,(id));

                // Execute the SQL statement to insert the new row
                stmt.executeUpdate();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void changeDynamicDiscount(String id, boolean newDDiscount) throws SQLException {
        // Open a connection to the database
        try{
            // Prepare a SQL statement to insert a new row into the FlightData table
            //add sortation for flight ID order
            String sql = "UPDATE FlightData SET ddiscount = ? WHERE flightid = ? ";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Set the values of the parameters in the SQL statement

                stmt.setBoolean(1, (newDDiscount));
                stmt.setString(2,(id));

                // Execute the SQL statement to insert the new row
                stmt.executeUpdate();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
//    public void refreshDatabase()  {
//        // Open a connection to the database
//        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
//            String sql = "";
//            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//                stmt.executeUpdate();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    public void hasDiscount(FlightData flightData, boolean newSDiscount, boolean newDDiscount) throws SQLException {
        // Open a connection to the database
        try {
            // Prepare a SQL statement to insert a new row into the FlightData table
            //add sortation for flight ID order
            String sql = "UPDATE FlightData SET sdiscount = ?, ddiscount = ? WHERE flightid = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Set the values of the parameters in the SQL statement

                stmt.setBoolean(1, (newSDiscount));
                stmt.setBoolean(2, (newDDiscount));
                stmt.setString(3,("FL1"));



                // Execute the SQL statement to insert the new row
                stmt.executeUpdate();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    //TODO Pino methods

    public double priceFetcher(String id) {

        double price = 0;
        try {
            String sql = "SELECT price FROM FlightData WHERE flightid = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                price = resultSet.getDouble("price");
            }
            resultSet.close();

        } catch (SQLException e) {
           // LOGGER.log(Level.SEVERE, "failed to retrieve  price");
            throw new PersistenceException("Error retrieving price: " + e.getMessage(), e);
        }

        return price;
    }
    public  double discountFetcher(String id) {
        double discount = 0;
        try {
            String sql = "SELECT sdiscount FROM FlightData WHERE flightid = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                discount = resultSet.getDouble("pdiscount");
            }
            resultSet.close();

        } catch (SQLException e) {
           // Logger.log(Level.SEVERE, "failed to retrieve  discount");
            throw new PersistenceException("Error retrieving discount: " + e.getMessage(), e);
        }

        return discount;
    }


    public  void changeStaticDiscount(String id, double price, double discount)  {


        // Open a connection to the database
        try{
            // Prepare a SQL statement to insert a new row into the FlightData table
            //add sortation for flight ID order
            String sql = "UPDATE FlightData SET price = ?, sdiscount = ? WHERE flightid = ? ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // Set the values of the parameters in the SQL statement
            System.out.println(price);
            updateStaticDiscountBool(id,true);
            stmt.setDouble(1, (price));
            stmt.setDouble(2, (discount));
            stmt.setString(3,(id));

            // Execute the SQL statement to insert the new row
            stmt.executeUpdate();

        } catch (SQLException e) {
            //LOGGER.log(Level.SEVERE, "failed to update Static discount");
            throw new PersistenceException("Error updating static discount: " + e.getMessage(), e);
        }

    }

    public  void eliminateStaticDiscount(String id, double price, double discount)  {

        // Open a connection to the database
        try {
            // Prepare a SQL statement to insert a new row into the FlightData table
            //add sortation for flight ID order
            String sql = "UPDATE FlightData SET price = ?, sdiscount = sdiscount - ? WHERE flightid = ? ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // Set the values of the parameters in the SQL statement
            System.out.println(price);
            updateStaticDiscountBool(id,false);
            stmt.setDouble(1, (price));
            stmt.setDouble(2, (discount));
            stmt.setString(3,(id));

            // Execute the SQL statement to insert the new row
            stmt.executeUpdate();

        } catch (SQLException e) {
           // LOGGER.log(Level.SEVERE, "failed to eliminate Static discount");
            throw new PersistenceException("Error eliminating static discount: " + e.getMessage(), e);
        }

    }

    private  void updateStaticDiscountBool(String id, boolean value) {
        // Open a connection to the database
        try {
            // Prepare a SQL statement to insert a new row into the FlightData table
            //add sortation for flight ID order
            String sql = "UPDATE FlightData SET sdiscount = ? WHERE flightid = ? ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // Set the values of the parameters in the SQL statement

            stmt.setBoolean(1, (value));
            stmt.setString(2,(id));

            // Execute the SQL statement to insert the new row
            stmt.executeUpdate();

        } catch (SQLException e) {
            //Persistence exception
          //  LOGGER.log(Level.SEVERE, "failed to update boolean value of Static discount");
            throw new PersistenceException("Error updating static discount: " + e.getMessage(), e);
        }

    }


    public  void changeDynamicDiscount(String id, boolean newDDiscount, double trueprice, double dpercent)  {
        // Open a connection to the database
        try {
            // Prepare a SQL statement to insert a new row into the FlightData table
            //add sortation for flight ID order
            String sql = "UPDATE FlightData SET ddiscount = ? , sdiscount = ? , price = ? WHERE flightid = ? ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            // Set the values of the parameters in the SQL statement

            stmt.setBoolean(1, (newDDiscount));
            stmt.setDouble(2, (dpercent));
            stmt.setDouble(3, (trueprice));
            stmt.setString(4,(id));

            // Execute the SQL statement to insert the new row
            stmt.executeUpdate();

        } catch (SQLException e) {
            //Persistence exception
           // LOGGER.log(Level.SEVERE, "failed to update Dynamic discount");
            throw new PersistenceException("Error updating Dynamic discount: " + e.getMessage(), e);
        }

    }

    public  ObservableList<GetInfoForFlights> getFlightsbyId(List<String> id){
        ObservableList<GetInfoForFlights> list = FXCollections.observableArrayList(
//               new GetInfoForBookings(1,2,"b",4)
        );
        for (String ids : id){
            try(
                 PreparedStatement ps = conn.prepareStatement("SELECT * FROM FlightData WHERE flightID = '" + ids + "'");){
                ResultSet rs = ps.executeQuery();

                while (rs.next()){
                    list.add(new GetInfoForFlights(rs.getString("flightid"),
                            rs.getString("planeid"),
                            rs.getString("departureairport"),
                            rs.getString("arrivalairport"),
                            rs.getString("actualdeparturetime"),
                            rs.getString("actualarrivaltime"),
                            rs.getString("dateofdeparture"),
                            rs.getString("dateofarrival"),
                            rs.getBoolean("sdiscount"),
                            rs.getBoolean("ddiscount"),
                            rs.getInt("price")));
                }

            }catch (Exception e){

            }
        }


        return list;
    }
}
