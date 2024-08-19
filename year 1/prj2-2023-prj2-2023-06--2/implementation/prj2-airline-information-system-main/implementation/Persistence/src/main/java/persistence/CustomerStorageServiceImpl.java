package persistence;

import datarecords.CustomerData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * This class knows everything about storing and retrieving customers from
 * the database. At the moment only returns dummy object with an id that is set.
 * Normally it will connect to a database and do all the handling.
 * 
 * @author Informatics Fontys Venlo
 */
class CustomerStorageServiceImpl implements CustomerStorageService {

    private final DataSource db = DatabaseConnector.getDataSource("prj2_db");
    private final Connection conn = db.getConnection();

    private final PersistenceBookingValidator persistenceBookingValidator = new PersistenceBookingValidator();

    CustomerStorageServiceImpl() throws SQLException {}

    @Override
    public CustomerData add(CustomerData customerData) {
        
        return new CustomerData( customerData.name(), customerData.lastName(), customerData.personalNumber());
        
    }

    @Override
    public List<CustomerData> getAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean insertCustomerData(CustomerData customerData) {
        if (persistenceBookingValidator.isInValidData(customerData, "Customer")){
            return false;
        }
        try  {
            // Prepare a SQL statement to insert a new row into the Customer table
            String sql = "INSERT INTO Customer (customerid, customerName, customerLname, customerIdNumber) " +
                    "VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Set the values of the parameters in the SQL statement
                stmt.setString(1, "");
                stmt.setString(2, customerData.name());
                stmt.setString(3, customerData.lastName());
                stmt.setString(4, customerData.personalNumber());
                // Execute the SQL statement to insert the new row
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the SQLException within the method
            System.out.println("Failed to insert customer data: " + e.getMessage());
        }
        return true;
    }


    public ObservableList<GetInfoForCustomers> getDataForCustomers() {
        ObservableList<GetInfoForCustomers> list = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Customer ORDER BY CAST(SUBSTRING(customerID, 4) AS VARCHAR);");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new GetInfoForCustomers(rs.getString("customerID"),
                        rs.getString("customerName"),
                        rs.getString("customerLname"),
                        rs.getString("customerIdNumber")));
            }

        } catch (Exception e) {
            System.out.println("Failed to retrieve data from the database.");
            e.printStackTrace();
        }

        return list;
    }
//    public boolean isValid(CustomerData customerData){
//        if(customerData == null){
//            System.out.println("Customer data can not be null");
//            return false;
//        }
//        if (customerData.name().isEmpty() || customerData.lastName().isEmpty() || customerData.personalNumber().isEmpty()){
//            System.out.println("all fields must be filled");
//            return false;
//        }
//        if (!customerData.name().matches("[a-zA-Z]+") || !customerData.lastName().matches("[a-zA-Z]+")){
//            System.out.println("Customer name and last name should only contain characters");
//            return false;
//        }
//        if (!(customerData.personalNumber().matches("[0-9]+"))){
//            System.out.println("Personal number should only contain digits");
//            return false;
//        }
//        else return true;
//
//    }




}
