package gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.Random;

@ExtendWith(ApplicationExtension.class)
public class SearchFlightTest {
//    SalesOfficerController flightController = new SalesOfficerController();
//    FlightServiceImpl businessLogic = BusinessLogicFactoryImpl.createFlightService();


    @BeforeAll
    public static void initToolkit() {
        // Initialize JavaFX
        try {
            Platform.startup(() -> {
            });
        } catch (Exception e) {
            // ignore already initialized exception
        }
    }

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createFlightController.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        stage.toFront();
    }

    //registration of the default period flight
    @Test
    public void testRegisterFlight (FxRobot robot) throws IOException {
        Random random = new Random();



    }

    }