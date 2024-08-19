package persistence;

import datarecords.PassengerData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerStorageServiceImpl implements PassengerStorageService{

    private final DataSource db = DatabaseConnector.getDataSource("prj2_db");
    private  final Connection conn = db.getConnection();
    private final PersistenceBookingValidator persistenceBookingValidator = new PersistenceBookingValidator();

    PassengerStorageServiceImpl() throws SQLException{}



    public boolean insertPassengerData(PassengerData passengerData) {
        if (persistenceBookingValidator.isInValidData(passengerData, "Passenger")){
            System.out.println("invalid data");
            return false;
        }
        try{
            // Prepare a SQL statement to insert a new row into the Passenger table
            String sql = "INSERT INTO Passenger (passengerid, passengerFname, passengerIdNr, passengerBD, passengerLname) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Set the values of the parameters in the SQL statement
                stmt.setString(1, "");
                stmt.setString(2, passengerData.name());
                stmt.setString(3, passengerData.personalNumber());
                stmt.setString(4, passengerData.dateOfBirth());
                stmt.setString(5, passengerData.lastName());
                // Execute the SQL statement to insert the new row
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException within the method
            System.out.println("Failed to insert passenger data: " + e.getMessage());
        }

       return true;
    }

    public ObservableList<GetInfoForPassengers> getDataForPassengers(){
        ObservableList<GetInfoForPassengers> list = FXCollections.observableArrayList();

        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Passenger ORDER BY passengerIdNr");
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                list.add(new GetInfoForPassengers(rs.getString("passengerID"),
                        rs.getString("passengerFname"),
                        rs.getString("passengerLname"),
                        rs.getString("passengerIdNr"),
                        rs.getString("passengerBD")
                ));
            }

        }catch (Exception e){
            System.out.println("Failed to retrieve data from the database.");
        }

        return list;
    }


    public ObservableList<GetInfoForPassengers> getPassengerById(ArrayList<String> id){
        ObservableList<GetInfoForPassengers> list = FXCollections.observableArrayList(
//               new GetInfoForBookings(1,2,"b",4)
        );
        for (String ids : id){
            try(
                 PreparedStatement ps = conn.prepareStatement("SELECT * FROM Passenger WHERE passengerID = '" + ids + "'")){
                ResultSet rs = ps.executeQuery();

                while (rs.next()){
                    list.add(new GetInfoForPassengers(rs.getString("passengerID"),
                            rs.getString("passengerFname"),
                            rs.getString("passengerLname"),
                            rs.getString("passengerIdNr"),
                            rs.getString("passengerBD")));
                    //TODO: changed rs.getDate("passengerBD") to rs.getString("passengerBD") because you need string not date
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }


        return list;
    }
//    public boolean isValid(PassengerData passengerData){
//        if(passengerData == null){
//            System.out.println("Customer data can not be null");
//            return false;
//        }
//        if (passengerData.name().isEmpty() || passengerData.lastName().isEmpty() || passengerData.personalNumber().isEmpty()){
//            System.out.println("all fields must be filled");
//            return false;
//        }
//        if (!passengerData.name().matches("[a-zA-Z]+") || !passengerData.lastName().matches("[a-zA-Z]+")){
//            System.out.println("Customer name and last name should only contain characters");
//            return false;
//        }
//        if (!(passengerData.personalNumber().matches("[0-9]+"))){
//            System.out.println("Personal number should only contain digits");
//            return false;
//        }
//        if (!(isValidDate(passengerData.dateOfBirth()))){
//            System.out.println("invalid birth date");
//            return false;
//        }
//        else return true;
//
//    }
//    public static boolean isValidDate(String date) {
//        // Check if the date string has the format 'dd-mm-yyyy'
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        try {
//            LocalDate parsedDate = LocalDate.parse(date, dateFormatter);
//
//            // Check if the date is not a future date
//            LocalDate currentDate = LocalDate.now();
//            if (parsedDate.isAfter(currentDate)) {
//                return false;
//            }
//
//            // Check if the person is not more than 122 years old (oldest person's age)
//            LocalDate oldestAllowedDate = currentDate.minusYears(122);
//            if (parsedDate.isBefore(oldestAllowedDate)) {
//                return false;
//            }
//
//            // All constraints are satisfied
//            return true;
//        } catch (Exception e) {
//            return false; // Invalid date format
//        }
//    }

}



