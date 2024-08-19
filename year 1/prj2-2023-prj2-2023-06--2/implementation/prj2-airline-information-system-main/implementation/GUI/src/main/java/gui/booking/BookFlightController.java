package gui.booking;

import businesslogic.FlightManager;
import datarecords.CustomerDataBuilder;
import datarecords.FlightData;
import gui.SceneManager;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import persistence.GetInfoForFlights;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Supplier;

/**
 * Controller class for booking flights.
 */
public class BookFlightController implements Initializable {
    @FXML
    private TableColumn<GetInfoForFlights, String> flightsArrival;

    @FXML
    private TableColumn<GetInfoForFlights, String> flightsDeparture;

    @FXML
    private TableColumn<GetInfoForFlights, String> flightsDOA;

    @FXML
    private TableColumn<GetInfoForFlights, String> flightsDOD;

    @FXML
    private TableColumn<GetInfoForFlights, String> flightsID;

    @FXML
    private TableColumn<GetInfoForFlights, String> flightsETA;

    @FXML
    private TableColumn<GetInfoForFlights, String> flightsETD;

    @FXML
    private TableColumn<GetInfoForFlights, Float> priceId;
    @FXML
    private Button exitBtn;


    @FXML
    private Button flightPopup;
    Integer menuIndex = null;
    @FXML
    public TableView<GetInfoForFlights> flightsTable;

    ContextMenu flightMenu = new ContextMenu();
    MenuItem removeFlight = new MenuItem("Remove flight");
    @FXML
    Button continueButton;
    private final Supplier<SceneManager> sceneManagerSupplier;
    private final FlightManager flightManager;

    private final BookingDO bookingDO;


    /**
     * Constructor for BookFlightController.
     *
     * @param sceneManagerSupplier The supplier for SceneManager.
     * @param flightManager        The instance of FlightManager.
     * @param bookingDO            The instance of BookingDO.
     */
    public BookFlightController(Supplier<SceneManager> sceneManagerSupplier, FlightManager flightManager, BookingDO bookingDO) {
        this.sceneManagerSupplier = sceneManagerSupplier;
        this.bookingDO = bookingDO;
        this.flightManager = flightManager;


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        flightsID.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getId()));
        flightsDeparture.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeparture()));
        flightsArrival.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrival()));
        flightsETD.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepTime()));
        flightsETA.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepTime()));
        flightsDOD.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepDate()));
        flightsDOA.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrDate()));
        priceId.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getPrice()));


        flightsTable.setItems(bookingDO.getFlightsData());
        flightsTable.setOnMouseClicked(e -> menuIndex = flightsTable.getSelectionModel().getSelectedIndex());
        removeFlight.setOnAction(a -> {
            flightsTable.getItems().remove(flightsTable.getItems().get(menuIndex));
            updateFlightData(flightsTable.getItems());
        });
        flightMenu.getItems().add(removeFlight);
        flightsTable.setContextMenu(flightMenu);
        updateFlightData(flightsTable.getItems());

    }

    /**
     * Handles the add flight popup.
     * This method is responsible for displaying a popup using EntityPopupHelper.
     */
    @FXML
    void addFlightPopup() {
        //Create a table view to display selected flights in the table
        TableView<GetInfoForFlights> table = new TableView<>();
        ObservableList<GetInfoForFlights> flightList = flightManager.getDataForFlights();

        // Configure the table with columns and cell value factories...
        TableColumn<GetInfoForFlights, String> flightID = new TableColumn<>();
        TableColumn<GetInfoForFlights, String> depAirport = new TableColumn<>();
        TableColumn<GetInfoForFlights, String> arrAirport = new TableColumn<>();
        TableColumn<GetInfoForFlights, String> DOD = new TableColumn<>();
        TableColumn<GetInfoForFlights, String> DOA = new TableColumn<>();
        TableColumn<GetInfoForFlights, String> ETD = new TableColumn<>();
        TableColumn<GetInfoForFlights, String> ETA = new TableColumn<>();
        TableColumn<GetInfoForFlights, String> price = new TableColumn<>();

        flightID.setCellValueFactory(new PropertyValueFactory<>("id"));
        depAirport.setCellValueFactory(new PropertyValueFactory<>("departure"));
        arrAirport.setCellValueFactory(new PropertyValueFactory<>("arrival"));
        ETD.setCellValueFactory(new PropertyValueFactory<>("depTime"));
        ETA.setCellValueFactory(new PropertyValueFactory<>("arrTime"));
        DOD.setCellValueFactory(new PropertyValueFactory<>("depDate"));
        DOA.setCellValueFactory(new PropertyValueFactory<>("arrDate"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().add(flightID);
        table.getColumns().add(depAirport);
        table.getColumns().add(arrAirport);
        table.getColumns().add(ETD);
        table.getColumns().add(ETA);
        table.getColumns().add(DOA);
        table.getColumns().add(DOD);
        table.getColumns().add(price);

        FlightData flightData = new FlightData("FL1", "Vilnius", "Barcelona", "Sofia","00:10", "01:30",null, null, false, false, 100.0f );
        CustomerDataBuilder builder = new CustomerDataBuilder();

        //Creating a popup for selecting flights, by using entity popup helper
        EntityPopupHelper<GetInfoForFlights, FlightData, FlightManager, CustomerDataBuilder> popupHelper = new EntityPopupHelper<>(flightList, table, "Flight", flightData, flightManager, builder);
        GetInfoForFlights returnedData = popupHelper.createPopup();
        if(returnedData != null){
            flightsTable.getItems().add(returnedData);
            updateFlightData(flightsTable.getItems());
        }

    }


    /**
     * Updates the flight data.
     *
     * @param updatedFlightData The updated flight data.
     */
    public void updateFlightData(ObservableList<GetInfoForFlights> updatedFlightData) {

        bookingDO.saveFlightData(updatedFlightData);
    }


    /**
     * Goes to the next page.
     */
    public void goToNextPage(){
        sceneManagerSupplier.get().changeScene("BookCustomerView");
    }


    /**
     * Checks for duplicate flights in the table.
     *
     * @param flights The flights list to check.
     * @return True if duplicates are found, false otherwise.
     */
    public static boolean checkDuplicates(ObservableList<GetInfoForFlights> flights) {
        Map<String, Integer> countMap = new HashMap<>();

        for (GetInfoForFlights flight : flights) {
            countMap.put(flight.getId(), countMap.getOrDefault(flight.getId(), 0) + 1);
        }

        for (int count : countMap.values()) {
            if (count > 1) {
                return true; // Contains duplicates
            }
        }
        return false; // No duplicates
    }



    /**
     * Checks the information before proceeding to the next page.
     * This method checks for duplicate flights and empty flight selection before navigating to the next page.
     */
    public void checkInformation(){
        if(checkDuplicates(flightsTable.getItems())){alertSystem("There is a duplicate flight");}
        else if(flightsTable.getItems().isEmpty()){alertSystem("No flights selected");}
        else{goToNextPage();}
    }


    /**
     * Navigates back to the previous page, and clears the
     * data from bookingDO.
     */
    @FXML
    private void goBackToPreviousPage() {
        sceneManagerSupplier.get().changeScene("ViewFlightController");
        bookingDO.clearAllData();
    }

    /**
     * Displays an alert message.
     *
     * @param message The alert message.
     */
    public void alertSystem(String message){
        Alert alert =  new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
    }


}
