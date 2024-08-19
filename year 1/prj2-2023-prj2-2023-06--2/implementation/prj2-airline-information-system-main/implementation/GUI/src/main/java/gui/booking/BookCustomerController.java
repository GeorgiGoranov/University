package gui.booking;

import businesslogic.CustomerManager;
import datarecords.CustomerData;
import datarecords.CustomerDataBuilder;
import gui.SceneManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import persistence.GetInfoForCustomers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

/**
 * This is a controller for adding and creating customers for the create booking use case.
 */

public class BookCustomerController implements Initializable {
    @FXML
    private TableColumn<GetInfoForCustomers, String> customerID;
    @FXML
    private TableColumn<GetInfoForCustomers, String> customerName;
    @FXML
    private TableColumn<GetInfoForCustomers, String> customerLName;
    @FXML
    private TableColumn<GetInfoForCustomers, String> customerIdNumber;

    @FXML
    private Button customerPopup;
    @FXML
    private TableView<GetInfoForCustomers> customerTable;
    @FXML
    Button goBackButton;
    @FXML
    Button continueButton;
    private final Supplier<SceneManager> sceneManagerSupplier;
    private final BookingDO bookingDO;
    private final CustomerManager customerManager;


    ContextMenu customerMenu = new ContextMenu();
    MenuItem removeCustomer = new MenuItem("Remove customer");
    private Integer menuIndex;


    public BookCustomerController(Supplier<SceneManager> sceneManagerSupplier, BookingDO bookingDO, CustomerManager customerManager) {
        this.sceneManagerSupplier = sceneManagerSupplier;
        this.bookingDO = bookingDO;
        this.customerManager = customerManager;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerID.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getId()));
        customerName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));
        customerLName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getLastName()));
        customerIdNumber.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPersonalNumber()));

        customerTable.setItems(bookingDO.getCustomersData());
        customerTable.setOnMouseClicked(e -> menuIndex = customerTable.getSelectionModel().getSelectedIndex());
        removeCustomer.setOnAction(a -> {
            customerTable.getItems().remove(customerTable.getItems().get(menuIndex));
            updateCustomerData(customerTable.getItems());
        });
        customerMenu.getItems().add(removeCustomer);
        customerTable.setContextMenu(customerMenu);

    }

    /**
     * Handles the add customer popup.
     * This method is responsible for displaying a popup using EntityPopupHelper.
     */
    @FXML
    void addCustomerPopup() {
        //Create a table view to display selected customers in the table
        TableView<GetInfoForCustomers> table = new TableView<>();
        ObservableList<GetInfoForCustomers> customerList = customerManager.getCustomerData();

        // Configure the table with columns and cell value factories...
        TableColumn<GetInfoForCustomers, String> cusId = new TableColumn<>();
        TableColumn<GetInfoForCustomers, String> cusName = new TableColumn<>();
        TableColumn<GetInfoForCustomers, String> cusLName = new TableColumn<>();
        TableColumn<GetInfoForCustomers, String> cusIdNumber = new TableColumn<>();

        cusId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cusName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cusLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        cusIdNumber.setCellValueFactory(new PropertyValueFactory<>("personalNumber"));

        table.getColumns().add(cusId);
        table.getColumns().add(cusName);
        table.getColumns().add(cusLName);
        table.getColumns().add(cusIdNumber);

        CustomerData customerData = new CustomerDataBuilder()
                .setname("John")
                .setlastName("Doe")
                .setpersonalNumber("001001001")
                .build();
        CustomerDataBuilder builder = new CustomerDataBuilder();

        //Creating a popup for selecting flights, by using entity popup helper
        EntityPopupHelper<GetInfoForCustomers, CustomerData, CustomerManager, CustomerDataBuilder> popupHelper = new EntityPopupHelper<>(customerList, table,"Customer", customerData, customerManager, builder);
        GetInfoForCustomers returnedData = popupHelper.createPopup();
        if(returnedData != null){
            customerTable.getItems().add(returnedData);
            updateCustomerData(customerTable.getItems());
        }

    }

    /**
     * Updates the customer data and saves the changes made to the bookingDO.
     *
     * @param updatedCustomerData The updated customer data.
     */
    public void updateCustomerData(ObservableList<GetInfoForCustomers> updatedCustomerData) {
        bookingDO.saveCustomerData(updatedCustomerData);
    }


    /**
     * Navigates to the next page for booking.
     */
    @FXML
    private void goToNextPage() {
        sceneManagerSupplier.get().changeScene("bookPassengerView");
    }


    /**
     * Navigates to the previous page for booking.
     */
    @FXML
    private void goBackToPreviousPage() {
        sceneManagerSupplier.get().changeScene("bookFlightView");
    }


    /**
     * Checks the entered customer information and proceeds to the next page if valid.
     * Displays an alert message if the information is not valid.
     */
    public void checkInformation() {
        if (customerTable.getItems().size() > 1) {
            alertSystem("Only one customer allowed");
        } else if (customerTable.getItems().isEmpty()) {
            alertSystem("No customers selected");
        } else {
            goToNextPage();
        }
    }


    /**
     * Displays an alert with the given message.
     *
     * @param message The alert message.
     */
    private void alertSystem(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
    }
}
