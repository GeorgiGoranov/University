package gui;

import businesslogic.FlightManager;
import datarecords.FlightData;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import persistence.GetInfoForFlights;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

public class EditFlightController implements Initializable{

    @FXML
    private TableColumn<GetInfoForFlights, String> col_a;

    @FXML
    private TableColumn<GetInfoForFlights, String> col_d;

    @FXML
    private TableColumn<GetInfoForFlights, String> col_id;


    @FXML
    private TableView<GetInfoForFlights> table_DB;


    @FXML
    private TextField filterField;

    @FXML
    private ImageView exitBtn,burgerM;

    private SceneManager sceneManager;

    @FXML
    private AnchorPane pane1, pane2,pane3;

    @FXML
    private Button createFlightBtn,searchFlightBtn,createBookingBtn;

    @FXML
    private ComboBox<String> depAirport;
    @FXML
    private ComboBox<String> arrAirport;
    @FXML
    private ComboBox<String> depTime;
    @FXML
    private ComboBox<String> arrTime;
    @FXML
    private DatePicker DoD; //Day of Departure
    @FXML
    private DatePicker DoA; //Day of Arrival
    @FXML
    private TextField Price;
    @FXML
    private Button saveButton;
    @FXML
    private Button thirdButton;
    @FXML
    private Label prevLabel;

    @FXML
    private Button Booking;
    @FXML
    private Button selectButton;

    @FXML
    ComboBox<String> planeId = new ComboBox<>();
    @FXML
    private TextField flightId;
    private String selectedFlightID;
    private final Supplier<SceneManager> sceneManagerSupplier;

    private final FlightManager flightManager;

    public EditFlightController(Supplier<SceneManager> sceneManagerSupplier, FlightManager flightManager) {
        this.sceneManagerSupplier = sceneManagerSupplier;
        this.flightManager =  flightManager;
    }

    ObservableList<GetInfoForFlights> list;
    ObservableList<GetInfoForFlights> dataList;

    Integer index;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transition();
        displayInfoOfFlights();
        search_flight();

        this.depAirport.setTooltip(new Tooltip());
        this.arrAirport.setTooltip(new Tooltip());
        planeId.setItems(flightManager.getPlaneIDs());

        this.depAirport.getItems().addAll(LISTA);
        this.arrAirport.getItems().addAll(LISTA);
        new ComboBoxAutoComplete<String>(depAirport);
        new ComboBoxAutoComplete<String>(arrAirport);

        this.depTime.getItems().addAll(timeOpt);
        this.arrTime.getItems().addAll(timeOpt);
        selectButton.disableProperty().bind(table_DB.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    void exitBtn(MouseEvent event) {
        goBackToPreviousPage();
    }

    @FXML
    void goToCreateBooking(MouseEvent event) {
        pathToCreateBooking();
    }

    @FXML
    void goToSearchFlight(MouseEvent event) {
        pathToSearchFlight();
    }

    @FXML
    private void pathToCreateFlight(){
        sceneManagerSupplier.get().changeScene("CreateFlightController");
    }
    @FXML
    private void pathToCreateBooking(){
        sceneManagerSupplier.get().changeScene("bookFlightView");
    }

    @FXML
    private void goBackToPreviousPage(){
        sceneManagerSupplier.get().changeScene("ViewFlightController");
    }

    @FXML
    private void pathToSearchFlight(){
        sceneManagerSupplier.get().changeScene("SearchFlight");
    }


    @FXML
    void goToCreateFlight(MouseEvent event) {
        pathToCreateFlight();
    }


    @FXML
    void getItems(MouseEvent event) {
        index = table_DB.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }
    }
    @FXML
    void displayInfoOfFlights(){
        col_id.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("id"));
        col_d.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("departure"));
        col_a.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("arrival"));

        list = flightManager.getDataForFlights();
        table_DB.setItems(list);

    }

    @FXML
    void search_flight(){
        col_id.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("id"));
        col_d.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("departure"));
        col_a.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("arrival"));

        dataList = flightManager.getDataForFlights();
        table_DB.setItems(dataList);

        FilteredList<GetInfoForFlights> filteredListData = new FilteredList<>(dataList, b ->true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredListData.setPredicate(flight -> {
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(flight.getDeparture().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }else if (flight.getArrival().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                } else if (flight.getDepDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (flight.getArrDate().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else if (flight.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                else{
                    return false;
                }

            });
        });

        SortedList<GetInfoForFlights> sortedList = new SortedList<>(filteredListData);
        sortedList.comparatorProperty().bind(table_DB.comparatorProperty());
        table_DB.setItems(sortedList);

    }

    private void transition(){

        pane1.setVisible(false);
        pane3.setVisible(false);

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.3),pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.3),pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        burgerM.setOnMouseClicked(event -> {

            pane1.setVisible(true);

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.3),pane1);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.3),pane2);
            translateTransition1.setByX(+600);
            translateTransition1.play();
            pane3.setVisible(true);
        });

        pane1.setOnMouseClicked(event -> {

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.3),pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                pane1.setVisible(false);
                pane3.setVisible(false);
            });

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.3),pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });
    }

    private String ActualDepTime(){
        if(depTime.getValue() == null){
            return null;
        } else{
            String Deptime =  depTime.getValue();
            return Deptime;
        }
    }

    private String ActualArrTime() {
        if(arrTime.getValue() == null){
            return null;
        } else {
            String ArrTime = arrTime.getValue();
            return ArrTime;
        }
    }

    private boolean depBeforeArr() {
        int arrivalHour = Integer.parseInt(arrTime.getValue().split(":")[0]);
        int arrivalMinute = Integer.parseInt(arrTime.getValue().split(":")[1]);
        int departureHour = Integer.parseInt(depTime.getValue().split(":")[0]);
        int departureMinute = Integer.parseInt(depTime.getValue().split(":")[1]);

        if (departureHour < arrivalHour || (departureHour == arrivalHour && departureMinute < arrivalMinute)) {
            return true;
        } else if (departureHour == arrivalHour && departureMinute == arrivalMinute) {
            return true; // changed to true
        } else if (departureHour > arrivalHour) {
            return false;
        } else {
            return false;
        }
    }
    private boolean checkDateDec() {

        LocalDate date = DoD.getValue();
        int month = date.getMonthValue(); // Get the month value (Note: January is 0 in Java's Calendar class)
        if(month == 12){
            return true;
        }
        return false;
    }
    @FXML
    private void updateFlight() throws SQLException {

        boolean statdiscount = checkDateDec();

        flightManager.updateFlightData(col_id.getText(),planeId.getValue(), depAirport.getValue(), arrAirport.getValue(), ActualDepTime(), ActualArrTime(), DoD.getValue(), DoA.getValue(), statdiscount, false, price(),selectedFlightID);


    }

    private float price(){

        if (Price.getText().isEmpty())
            alertSystem("Price cannot be empty");



        float price = Float.parseFloat(Price.getText());
        return price;
    }
    @FXML
    private void checkFlight() throws SQLException {

        FlightData flightData = new FlightData(col_id.getText(),planeId.getValue(), depAirport.getValue(), arrAirport.getValue(), ActualDepTime(), ActualArrTime(), DoD.getValue(), DoA.getValue(),false,false, price() );

        boolean access = true;
        //Check if airports are the same

        if((depAirport.getValue() == null)
                || (arrAirport.getValue() == null)
                || (ActualDepTime() == null)
                || (ActualArrTime() == null)
                || (DoD.getValue() == null)
                || (DoA.getValue() == null)
                || (Price.getText() == null)){


            access = false;
            alertSystem("All fields need to be filled out");
        }else if (arrAirport.getValue().equals(depAirport.getValue())){
            access = false;
            alertSystem("Airports cannot be the same");
        }else if(depBeforeArr() == false && DoA.getValue().isBefore(DoD.getValue())) {
            //check if the day of departure is earlier than the day of arrival
            access = false;
            alertSystem("Arrival time can not be earlier than departing time");
        }else if(depBeforeArr() == true && DoA.getValue().isBefore(DoD.getValue())) {
            access = false;
            alertSystem("Arrival time can not be earlier than departing time");
        }else if(depBeforeArr() == false && DoA.getValue().isEqual(DoD.getValue())) {
            access = false;
            alertSystem("Arrival time can not be earlier than departing time");
        }
        //change to valid flight
        if(access) {
            updateFlight();
            clear_fields();
            saveButton.setVisible(false);
        }
    }

    public void clear_fields(){
        planeId.valueProperty().set(null);
        depAirport.valueProperty().set(null);
        arrAirport.valueProperty().set(null);
        depTime.valueProperty().set(null);
        arrTime.valueProperty().set(null);
        DoD.valueProperty().set(null);
        DoA.valueProperty().set(null);
        Price.setText(null);

    }

    private void alertSystem(String message){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(message);
        a.show();
    } // add in module thing

    private static final String[] LISTA = { "VNO/Vilnius", "LHR/London", "CDG/Paris",
            "FRA/Frankfurt", "AMS/Amsterdam", "FCO/Rome", "MAD/Madrid", "MUC/Munich", "BCN/Barcelona" };
    ObservableList<String> timeOpt =
            FXCollections.observableArrayList(
                    "00:00",
                    "00:10",
                    "00:20",
                    "00:30",
                    "00:40",
                    "00:50",
                    "01:00",
                    "01:10",
                    "01:20",
                    "01:30",
                    "01:40",
                    "01:50",
                    "02:00",
                    "02:10",
                    "02:20",
                    "02:30",
                    "02:40",
                    "02:50",
                    "03:00",
                    "03:10",
                    "03:20",
                    "03:30",
                    "03:40",
                    "03:50",
                    "04:00",
                    "04:10",
                    "04:20",
                    "04:30",
                    "04:40",
                    "04:50",
                    "05:00",
                    "05:10",
                    "05:20",
                    "05:30",
                    "05:40",
                    "05:50",
                    "06:00",
                    "06:10",
                    "06:20",
                    "06:30",
                    "06:40",
                    "06:50",
                    "07:00",
                    "07:10",
                    "07:20",
                    "07:30",
                    "07:40",
                    "07:50",
                    "08:00",
                    "08:10",
                    "08:20",
                    "08:30",
                    "08:40",
                    "08:50",
                    "09:00",
                    "09:10",
                    "09:20",
                    "09:30",
                    "09:40",
                    "09:50",
                    "10:00",
                    "10:10",
                    "10:20",
                    "10:30",
                    "10:40",
                    "10:50",
                    "11:00",
                    "11:10",
                    "11:20",
                    "11:30",
                    "11:40",
                    "11:50",
                    "12:00",
                    "12:10",
                    "12:20",
                    "12:30",
                    "12:40",
                    "12:50",
                    "13:00",
                    "13:10",
                    "13:20",
                    "13:30",
                    "13:40",
                    "13:50",
                    "14:00",
                    "14:10",
                    "14:20",
                    "14:30",
                    "14:40",
                    "14:50",
                    "15:00",
                    "15:10",
                    "15:20",
                    "15:30",
                    "15:40",
                    "15:50",
                    "16:00",
                    "16:10",
                    "16:20",
                    "16:30",
                    "16:40",
                    "16:50",
                    "17:00",
                    "17:10",
                    "17:20",
                    "17:30",
                    "17:40",
                    "17:50",
                    "18:00",
                    "18:10",
                    "18:20",
                    "18:30",
                    "18:40",
                    "18:50",
                    "19:00",
                    "19:10",
                    "19:20",
                    "19:30",
                    "19:40",
                    "19:50",
                    "20:00",
                    "20:10",
                    "20:20",
                    "20:30",
                    "20:40",
                    "20:50",
                    "21:00",
                    "21:10",
                    "21:20",
                    "21:30",
                    "21:40",
                    "21:50",
                    "22:00",
                    "22:10",
                    "22:20",
                    "22:30",
                    "22:40",
                    "22:50",
                    "23:00",
                    "23:10",
                    "23:20",
                    "23:30",
                    "23:40",
                    "23:50"
            );
    //to be deleted (table for that is created in database)
    ObservableList<String> aircraftOpt =
            FXCollections.observableArrayList(
                    "Boeing_747",
                    "Boeing_777",
                    "Boeing_737"
            );

    @FXML
    private void selectFlight(ActionEvent event) {
        GetInfoForFlights selectedFlight = table_DB.getSelectionModel().getSelectedItem();

        if (selectedFlight != null) {
            // Display the flight information in the adjacent layout
            // You can access the flight details from the selectedFlight object
            // and update the necessary fields or labels in your layout
            // For example:
            planeId.setValue(selectedFlight.getPlaneid());
            depAirport.setValue(selectedFlight.getDeparture());
            arrAirport.setValue(selectedFlight.getArrival());
            depTime.setValue(selectedFlight.getDepTime());
            arrTime.setValue(selectedFlight.getArrTime());
            DoD.setValue(LocalDate.parse(selectedFlight.getDepDate()));
            DoA.setValue(LocalDate.parse(selectedFlight.getArrDate()));
            Price.setText(String.valueOf(selectedFlight.getPrice()));

            selectedFlightID = selectedFlight.getId();
            saveButton.setVisible(true);
        }
    }
}