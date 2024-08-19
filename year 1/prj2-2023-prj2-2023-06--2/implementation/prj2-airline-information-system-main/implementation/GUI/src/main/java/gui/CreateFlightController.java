package gui;


import businesslogic.FlightManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Supplier;


public class CreateFlightController implements Initializable {


    @FXML
    Button exitBtn;
    @FXML
    ImageView imageView;
    @FXML
    ComboBox<String> depAirport;
    @FXML
    ComboBox<String> arrAirport;
    @FXML
    ComboBox<String> depTime;
    @FXML
    ComboBox<String> depTime1;
    @FXML
    ComboBox<String> arrTime;
    @FXML
    ComboBox<String> arrTime1;
    @FXML
    DatePicker DoD; //Day of Departure
    @FXML
    DatePicker DoA; //Day of Arrival
    @FXML
    TextField Price;

    @FXML
    private TextField txt_flightID;

    @FXML
    ComboBox<String> planeId = new ComboBox<>();

    private final Supplier<SceneManager> sceneManagerSupplier;

    private final FlightManager flightManager;






    public CreateFlightController(Supplier<SceneManager> sceneManagerSupplier, FlightManager flightManager) {
        this.sceneManagerSupplier = sceneManagerSupplier;
        this.flightManager =  flightManager;
    }
    @FXML
    public void initialize(URL url, ResourceBundle rb) {


        List<String> hList = flightManager.getHours();
        List<String> mList = flightManager.getMinutes();
        List<String> aList = flightManager.getAirports();
        List<String> pList = flightManager.getPlaneIDs();


        this.depAirport.setTooltip(new Tooltip());
        this.arrAirport.setTooltip(new Tooltip());
       // this.typeOfAirplane.getItems().addAll(aircraftOpt);
        this.planeId.getItems().addAll(pList);
        this.depAirport.getItems().addAll(aList);
        this.arrAirport.getItems().addAll(aList);
        new ComboBoxAutoComplete<String>(depAirport);
        new ComboBoxAutoComplete<String>(arrAirport);

        this.depTime.getItems().addAll(hList);
        this.arrTime.getItems().addAll(hList);
        this.depTime1.getItems().addAll(mList);
        this.arrTime1.getItems().addAll(mList);


        exitBtn.setOnAction(exit -> goBackToPreviousPage());


        // Set the position and size of the ImageView
        imageView.setX(400);
        imageView.setY(60);
        imageView.setFitWidth(200);
        imageView.setFitHeight(300);




    }
    @FXML
    private void goBackToPreviousPage(){
        sceneManagerSupplier.get().changeScene("ViewFlightController");
    }
    @FXML
    private void checkFlight(){

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
            storeFlight();
            clear_fields();

        }
    }
    @FXML
    private void storeFlight() {

        boolean statdiscount = checkDateDec();

        flightManager.storeFlightData(txt_flightID.getText().toUpperCase(),planeId.getValue(), depAirport.getValue(), arrAirport.getValue(), ActualDepTime(), ActualArrTime(), DoD.getValue(), DoA.getValue(), statdiscount, false, price() );


    }
    public void clear_fields(){
        txt_flightID.setText(null);
        planeId.valueProperty().set(null);
        depAirport.valueProperty().set(null);
        arrAirport.valueProperty().set(null);
        depTime.valueProperty().set(null);
        arrTime.valueProperty().set(null);
        depTime1.valueProperty().set(null);
        arrTime1.valueProperty().set(null);
        DoD.valueProperty().set(null);
        DoA.valueProperty().set(null);
        Price.setText(null);

    }
    private String ActualDepTime(){
        if(depTime.getValue() == null && depTime1.getValue()== null){
            return null;
        } else{
            String Deptime =  depTime.getValue() +":"+ depTime1.getValue();
            return Deptime;
        }
    }
    private String ActualArrTime() {
        if(arrTime.getValue() == null && arrTime1.getValue()== null){
            return null;
        } else {
            String ArrTime = arrTime.getValue() + ":" + arrTime1.getValue();
            return ArrTime;
        }
    }
    private boolean depBeforeArr() {
        int arrivalHour = Integer.parseInt(arrTime.getValue());
        int arrivalMinute = Integer.parseInt(arrTime1.getValue());
        int departureHour = Integer.parseInt(depTime.getValue());
        int departureMinute = Integer.parseInt(depTime1.getValue());

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
    private float price(){
        float price = Float.parseFloat(Price.getText());
        return price;
    }
    private boolean checkDateDec() {

        LocalDate date = DoD.getValue();
        int month = date.getMonthValue(); // Get the month value (Note: January is 0 in Java's Calendar class)
        if(month == 12){
            return true;
        }
        return false;
    }
    private void alertSystem(String message){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(message);
        a.show();
    } // add in module thing
}

