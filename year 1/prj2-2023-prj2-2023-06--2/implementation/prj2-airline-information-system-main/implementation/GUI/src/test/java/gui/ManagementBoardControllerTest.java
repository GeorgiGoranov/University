package gui;

import businesslogic.FlightManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import persistence.GetInfoForFlights;
import persistence.GetInfoForRecurringFlights;

import java.time.LocalDate;
import java.util.Date;

import static com.sun.javafx.fxml.expression.Expression.equalTo;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

class ManagementBoardControllerTest extends ApplicationTest {

    private ManagementBoardController controller;
    private FlightManager flightManager;
    private GetInfoForRecurringFlights getInfoForFlights;
    @Before
    void setUp() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagementBoardController.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        controller.initialize(null,null);
        stage.show();
    }

    @Test
    void createRecurringFlight() {
        // Set up the input fields
        DatePicker departureDate = lookup("#date_departure_auto").query();
        DatePicker arrivalDate = lookup("#date_arrival_auto").query();
        TextField flightId = lookup("#text_flight_id").query();
        TextField interval = lookup("#text_interval").query();
        DatePicker daylimit = lookup("#date_last_recurrence").query();

        // Set the values for the input fields
        departureDate.setValue(LocalDate.now());
        arrivalDate.setValue(LocalDate.now().plusDays(2));
        daylimit.setValue(LocalDate.now().plusDays(15));
        flightId.setText("ABC123");
        interval.setText("5");


        // Trigger the button click
        Button createButton = lookup("#createRecurringFlightButton").query();
        clickOn(createButton);



        flightManager.createRecurringFlights(String.valueOf(flightId),Integer.valueOf(String.valueOf(interval)), departureDate.getValue(),arrivalDate.getValue(),daylimit.getValue());
        getInfoForFlights = new GetInfoForRecurringFlights(String.valueOf(flightId),Integer.valueOf(String.valueOf(interval)), departureDate.getValue(),arrivalDate.getValue(),daylimit.getValue());



    }

    @Test
    void checkInput() {
    }

    @Test
    void getItems() {
    }

    @Test
    void getPopUp() {
    }

    @Test
    void deleteChosenFlight() {
    }

    @Test
    void initialize() {
    }

    @Test
    void exitBtn() {
    }

    @Test
    void goToCreateBooking() {
    }

    @Test
    void goToCreateFlight() {
    }

    @Test
    void goToManagementBoard() {
    }

    @Test
    void goToSearchFlight() {
    }
}