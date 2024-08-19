package businesslogic;

import businesslogic.exceptions.InvalidDataBusinessLogicException;
import datarecords.CustomerData;
import javafx.collections.ObservableList;
import persistence.CustomerStorageService;
import persistence.GetInfoForCustomers;


/**
 * Manages customers in the business logic.
 * Linking pin between GUI and persistence. Connected to customerStorageService 
 * in order to retrieve customers and to persist changes.
 * 
 * @author Informatics Fontys Venlo
 */
public class CustomerManager {
    
    private final CustomerStorageService customerStorageService;

    public CustomerManager( CustomerStorageService customerStorageService ) {
        this.customerStorageService = customerStorageService;
    }



    public CustomerData add( CustomerData customerData ){

        isValid(customerData);
        return customerStorageService.add(customerData);
    }
    public boolean insertCustomerData(CustomerData customerData) {
        isValid(customerData);
        return customerStorageService.insertCustomerData(customerData);
    }
    public ObservableList<GetInfoForCustomers> getCustomerData(){
        return customerStorageService.getDataForCustomers();}

    public void isValid(CustomerData customerData){
        try {
            if (customerData == null) {
                throw new InvalidDataBusinessLogicException("Customer data can not be null");
            }
            if (customerData.name().isEmpty() || customerData.lastName().isEmpty() || customerData.personalNumber().isEmpty()) {
                throw new InvalidDataBusinessLogicException("All fields must be filled in");
            }
            if (!customerData.name().matches("[a-zA-Z]+") || !customerData.lastName().matches("[a-zA-Z]+")) {
                throw new InvalidDataBusinessLogicException("Name and/or last name should only contain letters");
            }
            if (!(customerData.personalNumber().matches("[0-9]+"))) {
                throw new InvalidDataBusinessLogicException("Personal number should only contain digits");
            }
        }catch (InvalidDataBusinessLogicException e){
            throw new InvalidDataBusinessLogicException(e.getMessage(), e.getCause());
        }

    }
}
