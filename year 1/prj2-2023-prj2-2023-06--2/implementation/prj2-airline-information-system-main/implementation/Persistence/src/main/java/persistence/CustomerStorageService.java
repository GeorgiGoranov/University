package persistence;

import datarecords.CustomerData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.*;
import java.util.List;

/**
 * Interface that describes all services offered by the CustomerStorageService.
 * @author Informatics Fontys Venlo
 */
public interface CustomerStorageService {
    CustomerData add( CustomerData customerData);
    List<CustomerData> getAll();
    boolean insertCustomerData(CustomerData customerData);
     ObservableList<GetInfoForCustomers> getDataForCustomers();
}
