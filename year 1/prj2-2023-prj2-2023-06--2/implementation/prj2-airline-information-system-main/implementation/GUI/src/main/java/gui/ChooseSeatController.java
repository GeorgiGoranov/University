package gui;

import businesslogic.BookingManager;
import businesslogic.FlightManager;
import businesslogic.TicketManager;
import datarecords.TicketData;
import gui.booking.BookingDO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import persistence.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;

public class ChooseSeatController implements Initializable {

    private final Supplier<SceneManager> sceneManagerSupplier;
    private final FlightManager flightManager;
    private final TicketManager ticketManager;
    private final BookingManager bookingManager;
    private final String bookingId;
    private final String passengerId;

    private BookingDO bookingDO;
    private ObservableList<GetInfoForPassengers> passengerInfo;
    @FXML
    private Label name;
    @FXML
    private Label nameLabel;
    @FXML
    private Label chooseSeat;
    @FXML
    private ComboBox<String> letterSeat;
    @FXML
    private ComboBox<Integer> numberSeat;
    @FXML
    private Button confirm;
    @FXML
    private Button randomSeat;
    @FXML
    private ImageView planeImage;
    @FXML
    private TableView<GetInfoForFlights> flightsTable;
    @FXML
    private TableColumn<GetInfoForFlights, String> colFlightId;
    @FXML
    private TableColumn<GetInfoForFlights, String> colDepAirport;
    @FXML
    private TableColumn<GetInfoForFlights, String> colArrAirport;
    @FXML
    private TableColumn<GetInfoForFlights, LocalDate> colFlightDepartureDate;
    @FXML
    private TableColumn<GetInfoForFlights, LocalDate> colFlightArrivalDate;
    @FXML
    private TableColumn<GetInfoForFlights, String> colFlightDepartureTime;
    @FXML
    private TableColumn<GetInfoForFlights, String> colFlightArrivalTime;
    @FXML
    private Label choosenFlightLabel;
    @FXML
    private Label flightIdLabel;
    @FXML
    private Label departureDateLabel;
    @FXML
    private Label arrivalDateLabel;
    @FXML
    private Label departureTimeLabel;
    @FXML
    private Label arrivalTimeLabel;
    @FXML
    private Label toLabel;
    @FXML
    private Label arrivalLabel;
    @FXML
    private Label departureLabel;
    @FXML
    private Label idLabelText;
    @FXML
    private Label departureDateLabelText;
    @FXML
    private Label arrivalDateLabelText;
    @FXML
    private Label departureTimeLabelText;
    @FXML
    private Label arrivalTimeLabelText;
    @FXML
    private Label seatLetterLabel;
    @FXML
    private Label seatNumberLabel;
    @FXML
    private Button A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21,
            B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21,
            C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15, C16, C17, C18, C19, C20, C21,
            D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, D11, D12, D13, D14, D15, D16, D17, D18, D19, D20, D21,
            E1, E2, E3, E4, E5, E6, E7, E8, E9, E10, E11, E12, E13, E14, E15, E16, E17, E18, E19, E20, E21,
            F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12, F13, F14, F15, F16, F17, F18, F19, F20, F21;


    ObservableList<GetInfoForFlights> flightList;


    public ChooseSeatController(Supplier<SceneManager> sceneManagerSupplier, BookingDO bookingDO, FlightManager flightManager, TicketManager ticketManager, BookingManager bookingManager) {
        this.sceneManagerSupplier = sceneManagerSupplier;
        this.bookingDO = bookingDO;
        this.flightManager = flightManager;
        this.ticketManager = ticketManager;
        this.bookingManager = bookingManager;
        this.bookingId = BookingDO.getBookingID();
        this.passengerId = BookingDO.getPassengerID();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showFlightsTable();
        showFlightInfo();
        chooseSeatOption();
        confirm.setOnAction(e -> checkSeatAndFlight());
        randomSeat.setOnAction(e -> {
            seatLetterLabel.setText(ticketManager.randomSeat());
            checkSeatAndFlight();
            chosenSeatPopUp();
        });

    }

    @FXML
    void showFlightsTable() {
        colFlightId.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, String>("id"));
        colDepAirport.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, String>("departure"));
        colArrAirport.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, String>("arrival"));
        colFlightDepartureTime.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, String>("depTime"));
        colFlightArrivalTime.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, String>("arrTime"));
        colFlightDepartureDate.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, LocalDate>("depDate"));
        colFlightArrivalDate.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, LocalDate>("arrDate"));

        flightList = flightManager.getFlightsById(getFlightId());
        flightsTable.setItems(flightList);
    }

    @FXML
    void showFlightInfo() {
        flightsTable.setOnMouseClicked(MouseEvent -> {
            int indexF = flightsTable.getSelectionModel().getSelectedIndex();
            if (indexF <= -1) {
                return;
            } else if (ticketManager.getChosenSeatsForPassenger(bookingId, colFlightId.getCellData(indexF), passengerId) != null) {
                seatLetterLabel.setText(ticketManager.getSpecifficTicket(bookingId, colFlightId.getCellData(indexF), passengerId).seat());
                idLabelText.setText(colFlightId.getCellData(indexF));
                arrivalLabel.setText(colArrAirport.getCellData(indexF));
                departureLabel.setText(colDepAirport.getCellData(indexF));
//                departureDateLabelText.setText(colFlightDepartureDate.getCellData(indexF).toString());
//                arrivalDateLabelText.setText(colFlightArrivalDate.getCellData(indexF).toString());
                departureTimeLabelText.setText(colFlightDepartureTime.getCellData(indexF));
                arrivalTimeLabelText.setText(colFlightArrivalTime.getCellData(indexF));
                chosenSeatPopUp();
            } else {
                idLabelText.setText(colFlightId.getCellData(indexF));
                arrivalLabel.setText(colArrAirport.getCellData(indexF));
                departureLabel.setText(colDepAirport.getCellData(indexF));
//                departureDateLabelText.setText(colFlightDepartureDate.getCellData(indexF).toString());
//                arrivalDateLabelText.setText(colFlightArrivalDate.getCellData(indexF).toString());
                departureTimeLabelText.setText(colFlightDepartureTime.getCellData(indexF));
                arrivalTimeLabelText.setText(colFlightArrivalTime.getCellData(indexF));
                List<String> chosenSeatIds = ticketManager.getChosenSeats(bookingId, idLabelText.getText());
                for (String id : chosenSeatIds) {
                    Button button = (Button) getButtonById(id);
                    button.setVisible(false);
                }
            }
        });


    }

    EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            //make invisible, has to check all ids in database so do later
            Button button = (Button) event.getSource();
            seatLetterLabel.setText(button.getId());
        }
    };

    @FXML
    void chooseSeatOption() {

        for (char letter = 'A'; letter <= 'F'; letter++) {
            for (int number = 1; number <= 21; number++) {
                String buttonId = String.valueOf(letter) + number;
                Button button = (Button) getButtonById(buttonId);
                if (button != null) {
                    button.setOnAction(buttonHandler);
                }
            }
        }

    }

    void checkSeatAndFlight() {
        boolean access = true;
        if (seatLetterLabel.getText().isEmpty()) {
            alertSystem("Please choose a seat.");
            access = false;
        } else if (idLabelText.getText().isEmpty()) {
            alertSystem("Please choose a flight.");
            access = false;
        }

        if(access){
            saveSeats();
            chosenSeatPopUp();
        }
    }

    private void alertSystem(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
    }

    void saveSeats() {
        ticketManager.saveTicket(seatLetterLabel.getText(), null, idLabelText.getText(), bookingId, passengerId);
    }

    private Button getButtonById(String buttonId) {
        try {
            return (Button) getClass().getDeclaredField(buttonId).get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<String> getFlightId() {
        return bookingManager.getPassenger(bookingId).flightIDs();
    }

    @FXML
    public void chosenSeatPopUp() {
        Stage newStage = new Stage();
        
        GridPane gridPane = new GridPane();
        Button close = new Button("Close");

        gridPane.add(new Label("Seat:"), 0, 0);
        gridPane.add(new Label("Flight:"), 0, 1);
        gridPane.add(new Label("Passenger:"), 0, 2);

        gridPane.add(new Label(seatLetterLabel.getText()), 1, 0);
        gridPane.add(new Label(departureLabel.getText() + " - " + arrivalLabel.getText()),1,1);
        gridPane.add(new Label(name.getText()), 1, 2);

        gridPane.add(close, 2, 4);

        close.setOnAction(e -> {
            newStage.close();
        });

        Scene newScene = new Scene(gridPane, 200, 100);
        newStage.setScene(newScene);
        newStage.showAndWait();


    }

}
