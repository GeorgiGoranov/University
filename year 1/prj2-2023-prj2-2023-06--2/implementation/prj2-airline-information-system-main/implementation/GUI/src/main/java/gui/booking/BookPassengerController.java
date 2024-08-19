package gui.booking;

import businesslogic.BookingManager;
import businesslogic.PassengerManager;
import datarecords.BookingData;
import datarecords.PassengerData;
import datarecords.PassengerDataBuilder;
import gui.SceneManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import persistence.GetInfoForFlights;
import persistence.GetInfoForPassengers;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;


/**
 * This is a controller for adding and creating passengers for the create booking use case.
 */
public class BookPassengerController implements Initializable {
    @FXML
    private TableColumn<GetInfoForPassengers, String> nameOfPassenger;
    @FXML
    private TableColumn<GetInfoForPassengers, String> lNamePassenger;
    @FXML
    private TableColumn<GetInfoForPassengers, String> dateOfBirthPassenger;
    @FXML
    private TableColumn<GetInfoForPassengers, String> passengerNumber;
    @FXML
    private TableColumn<GetInfoForPassengers, String> passengerID;
    @FXML
    private Button passengerPopup;
    private final BookingManager bookingManager;
    private final PassengerManager passengerManager;
    private final Supplier<SceneManager> sceneManagerSupplier;
    @FXML
    private TableView<GetInfoForPassengers> passengerTable;
    private final BookingDO bookingDO;
    @FXML
    private Button createBooking;
    private final ContextMenu passengerMenu = new ContextMenu();
    private final MenuItem removePassenger = new MenuItem("Remove passenger");
    private Integer menuIndex;
    @FXML
    Button goBackButton;

    public BookPassengerController(Supplier<SceneManager> sceneManagerSupplier, BookingManager bookingManager, PassengerManager passengerManager, BookingDO bookingDO) {
        this.passengerManager = passengerManager;
        this.bookingManager = bookingManager;
        this.sceneManagerSupplier = sceneManagerSupplier;
        this.bookingDO = bookingDO;

    }


    /**
     * Stores the booking information and displays a confirmation message.
     * If the booking data is invalid in other layers, an alert is shown.
     */
    @FXML
    private void storeBooking() {
        Label result = new Label();
        List<String> flightIDs = new ArrayList<>();
        List<String> passengerIDs = new ArrayList<>();
        LocalDate bookingDate = LocalDate.now();
        String customerIDS = bookingDO.getCustomersData().get(0).getId();

        if (!(bookingDO.getFlightsData().isEmpty())) {
            for (GetInfoForFlights item : bookingDO.getFlightsData()) {
                String columnValue = item.getId();
                flightIDs.add(columnValue);
            }
        }
        if (!(passengerTable.getItems().isEmpty())) {
            for (GetInfoForPassengers item : passengerTable.getItems()) {
                String columnValue = passengerID.getCellData(item);
                passengerIDs.add(columnValue);
            }
        }
        String bookingID;


        if (passengerIDs.size() != 0 && flightIDs.size() != 0) {
            BookingData bookingData = new BookingData(bookingDate, customerIDS, flightIDs, passengerIDs);
            BookingData addBooking = bookingManager.add(bookingData);

            bookingID = bookingManager.insertBookingData(bookingData);
            //If data is invalid in other layers
            if (bookingID == null){
                alertSystem("Invalid booking data");
                return;
            }


            result.setText("Booking added! \nDate: " + addBooking.bookingDate() + "\nCustomer: "
                    + addBooking.customerID() + "\nFlights: " + addBooking.flightIDs() + "\nPassengers: "
                    + addBooking.passengerIDs() + "\nPrice: " + bookingManager.getBookingPrice(bookingID) + "$");

        }


        bookingDO.clearAllData();
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(result.getText());
        a.getButtonTypes().removeAll();
        a.setOnCloseRequest(c -> sceneManagerSupplier.get().changeScene("viewFlightController"));
        a.showAndWait();

    }


    public static boolean checkDuplicates(ObservableList<GetInfoForPassengers> passengers) {
        Map<String, Integer> countMap = new HashMap<>();

        for (GetInfoForPassengers passenger : passengers) {
            countMap.put(passenger.getId(), countMap.getOrDefault(passenger.getId(), 0) + 1);
        }

        for (int count : countMap.values()) {
            if (count > 1) {
                return true; // Contains duplicates
            }
        }
        return false; // No duplicates
    }


    /**
     * Checks if there are any duplicate passengers or if no passengers are selected.
     * If there are duplicates, or no passengers are selected, an alert is shown.
     * Otherwise, a confirmation dialog is displayed, and if confirmed, the booking is stored.
     */
    public void checkInformation() {
        if (checkDuplicates(passengerTable.getItems())) {
            alertSystem("There is a duplicate flight ");
        } else if (passengerTable.getItems().isEmpty()) {
            alertSystem("No passengers selected");
        } else {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Save booking?", ButtonType.YES);
            confirmation.getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                storeBooking();
            } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                confirmation.close();
            }
        }
    }


    /**
     * Initializes the controller by setting up the table columns and context menu for passengers.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameOfPassenger.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));
        lNamePassenger.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getLastName()));
        dateOfBirthPassenger.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBirth()));
        passengerNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPersonalNumber()));
        passengerID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));

        passengerTable.setItems(bookingDO.getPassengerData());
        passengerTable.setOnMouseClicked(e -> menuIndex = passengerTable.getSelectionModel().getSelectedIndex());
        removePassenger.setOnAction(a -> {
            passengerTable.getItems().remove(passengerTable.getItems().get(menuIndex));
            updatePassengerData(passengerTable.getItems());
        });
        passengerMenu.getItems().add(removePassenger);
        passengerTable.setContextMenu(passengerMenu);
    }

    /**
     * Opens a popup for adding a new passenger by using EntityPopupHelper.
     * The popup allows entering passenger details, and if a new passenger is added, it is updated in the passenger table.
     */
    @FXML
    void addPassengerPopup() {
        //Create a table view to display selected passengers in the table
        TableView<GetInfoForPassengers> table = new TableView<>();
        ObservableList<GetInfoForPassengers> passengerList = passengerManager.getPassengerData();

        // Configure the table with columns and cell value factories...
        TableColumn<GetInfoForPassengers, String> passengerName = new TableColumn<>();
        TableColumn<GetInfoForPassengers, String> passengerLName = new TableColumn<>();
        TableColumn<GetInfoForPassengers, String> passengerNumber = new TableColumn<>();
        TableColumn<GetInfoForPassengers, String> passengerID = new TableColumn<>();
        TableColumn<GetInfoForPassengers, Date> dob = new TableColumn<>();

        passengerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        passengerLName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        passengerNumber.setCellValueFactory(new PropertyValueFactory<>("personalNumber"));
        passengerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        dob.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        table.getColumns().add(passengerID);
        table.getColumns().add(passengerName);
        table.getColumns().add(passengerLName);
        table.getColumns().add(passengerNumber);
        table.getColumns().add(dob);

        PassengerData passengerData = new PassengerDataBuilder()
                .setname("John")
                .setlastName("Doe")
                .setpersonalNumber("001001001")
                .setdateOfBirth("01-01-1989")
                .build();
        PassengerDataBuilder builder = new PassengerDataBuilder();

        //Creating a popup for selecting passengers, by using entity popup helper
        EntityPopupHelper<GetInfoForPassengers, PassengerData, PassengerManager, PassengerDataBuilder> popupHelper = new EntityPopupHelper<>(passengerList, table, "Passenger", passengerData, passengerManager, builder);
        GetInfoForPassengers returnedData = popupHelper.createPopup();
        if (returnedData != null) {
            passengerTable.getItems().add(returnedData);
            updatePassengerData(passengerTable.getItems());
        }

    }


    /**
     * Navigates to the next page.
     */
    @FXML
    public void goToNextPage() {
        sceneManagerSupplier.get().changeScene("createBookingController");
    }


    /**
     * Navigates to the previous page.
     */
    @FXML
    private void goBackToPreviousPage() {
        sceneManagerSupplier.get().changeScene("BookCustomerView");
    }

    /**
     * Shows an alert with the given message.
     *
     * @param message the message to display in the alert
     */
    private void alertSystem(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Updates the passenger data in the booking data object.
     *
     * @param updatedPassengerData the updated passenger data
     */
    public void updatePassengerData(ObservableList<GetInfoForPassengers> updatedPassengerData) {
        bookingDO.savePassengerData(updatedPassengerData);
    }
}