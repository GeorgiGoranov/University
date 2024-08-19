package gui;

import businesslogic.TicketManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class CreateTicketController implements Initializable {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private DatePicker dob;
    @FXML
    private ComboBox<String> seats;
    @FXML
    private ComboBox<String> options;
    @FXML
    private ComboBox<String> ticketType;
    @FXML
    private ComboBox<Integer> luggageWeight;
    @FXML
    private Button saveButton;
    @FXML
    private Label result;

    private final Supplier<SceneManager> sceneManagerSupplier;
    private final TicketManager ticketManager;

    private ArrayList<String> plainSeats;


    public CreateTicketController(Supplier<SceneManager> sceneManagerSupplier, TicketManager ticketManager) {
        this.sceneManagerSupplier = sceneManagerSupplier;
        this.ticketManager = ticketManager;
        this.plainSeats = new ArrayList<>();
    }
//
////    @FXML
////    private void toSecondary() {
////        Consumer<ViewFlightController> consumer
////                = (c) -> c.setPreviousView("Customer");
////        sceneManagerSupplier.get().changeScene("secondary", consumer);
////    }
//
//    ObservableList<String> extraOptions =
//            FXCollections.observableArrayList(
//                    "Special Meal",
//                    "Extra Leg Room"
//            );
//
//    ObservableList<String> ticketTypeOptions =
//            FXCollections.observableArrayList(
//                    "Random 1",
//                    "Random 2"
//            );
//
//    ObservableList<Integer> luggageOptions =
//            FXCollections.observableArrayList(
//                    10, 20, 30
//            );
//
//    private ObservableList<String> seatOptions() {
////        if(plainTpe = this){
//        Collections.addAll(this.plainSeats, "2A", "3A", "4A", "5A", "6A", "7A", "8A", "9A", "10A", "10C", "2F", "3F", "4F", "5F", "6F", "7F", "8F", "9F", "10F");
//        return FXCollections.observableArrayList(plainSeats);
////        }
////        Collections.addAll(this.plainSeats, "2A", "3A", "4A", "5A", "6A", "7A", "8A", "9A", "10A","11A", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D","11D", "2F", "3F", "4F", "5F", "6F", "7F", "8F", "9F", "10F", "11F");
////        return FXCollections.observableArrayList(plainSeats);
////        Collections.addAll(this.plainSeats, "2A", "3A", "4A", "5A", "6A", "7A", "8A", "9A", "10A","11A", "12A", "12C","2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D","11D","12D", "2F", "3F", "4F", "5F", "6F", "7F", "8F", "9F", "10F", "11F", "12F");
////        return FXCollections.observableArrayList(plainSeats);
////
//    }
//
//
//    @FXML
//    private void storeTicket() {
//        TicketData ticketData = new TicketData(0, firstName.getText(), lastName.getText(), dob.getValue(), seats.getValue(), options.getValue(), ticketType.getValue(), luggageWeight.getValue(), 1);
//        TicketData addedTicket = ticketManager.add(ticketData);
//        TicketDataDao dao = new TicketDataDao();
//        try {
//            dao.insertTicketData(ticketData);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        result.setText("Ticket added: " + addedTicket.toString());
//    }
//
//    @FXML
//    private void checkTicket() {
//        this.plainSeats.remove(seats.getValue());
//        TicketData ticketData = new TicketData(0, firstName.getText(), lastName.getText(), dob.getValue(), seats.getValue(), options.getValue(), ticketType.getValue(), luggageWeight.getValue(), 1);
//        boolean access = true;
//
//        //if baby is about to be born but you book ticket for after that, than it could happen that birthdate is in future
//        //so we would need the date of the flight instead of todays date, but we need booking for that
//        //and than baby needs to be atleast 6 months old
//        if (dob.getValue().isAfter(LocalDate.now())) {
//            access = false;
//            alertSystem("Date of Birth cannot be after the current date");
//
//        } else if (firstName.getText() == null
//                || lastName.getText() == null
//                || dob.getValue() == null
//                || seats.getValue() == null
//                || options.getValue() == null
//                || ticketType.getValue() == null
//                || luggageWeight.getValue() == null) {
//            access = false;
//            alertSystem("All fields need to be filled out");
//        }
//
//        //check name
//        boolean name = false;
//        //check that first Name starts with upper letter and consists of only letters
//        for (int i = 0; i < firstName.getText().length(); i++) {
//            char character = firstName.getText().charAt(i);
//            if ((!Character.isUpperCase(firstName.getText().charAt(0))) || (Character.isDigit(character)) || ((!Character.isDigit(character)) && (!Character.isLetter(character)))) {
//                name = true;
//            }
//        }
//        //check that last Name starts with upper letter and consists of only letters
//        for (int i = 0; i < lastName.getText().length(); i++) {
//            char character = lastName.getText().charAt(i);
//            if ((!Character.isUpperCase(lastName.getText().charAt(0))) || (Character.isDigit(character)) || ((!Character.isDigit(character)) && (!Character.isLetter(character)))) {
//                name = true;
//            }
//        }
//        //error message when name invalid
//        if (name) {
//            access = false;
//            alertSystem("Name is invalid");
//        }
//
//
//        if (access) {
//            storeTicket();
//        }
//    }
//
//    private void alertSystem(String message) {
//        Alert a = new Alert(Alert.AlertType.ERROR);
//        a.setContentText(message);
//        a.show();
//    }
//
//
//    /**
//     * Initializes the controller class.
//     *
//     * @param url
//     * @param rb
//     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        this.options.getItems().addAll(extraOptions);
//        this.ticketType.getItems().addAll(ticketTypeOptions);
//        this.luggageWeight.getItems().addAll(luggageOptions);
//        this.seats.getItems().addAll(seatOptions());

    }
//
//    //seats: need flight id for airplane type, have 3 different layouts for types, with List with all possible seats
//    //when seat choosen delete out of list
//
//
//
//


}






