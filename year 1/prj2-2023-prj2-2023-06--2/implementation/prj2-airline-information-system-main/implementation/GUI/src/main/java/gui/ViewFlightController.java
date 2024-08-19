package gui;

import java.util.function.Supplier;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


/**
 * Just for demo purposes, Controller of other view (viewFlightController.fxml).
 * 
 * @author Informatics Fontys Venlo
 */
public class ViewFlightController {
    
    @FXML
    private Button secondaryButton;
    @FXML
    private Button createBookingButton, searchFlightBtn, editFlight, ViewBookings;



    private final Supplier<SceneManager> sceneManagerSupplier;

    public ViewFlightController( Supplier<SceneManager> sceneManagerSupplier) {
        this.sceneManagerSupplier = sceneManagerSupplier;

    }
    
    @FXML
    private void switchToCreateFlight() {
        sceneManagerSupplier.get().changeScene("createFlightController");
    }
    @FXML
    private void switchToCreateBooking(){
        sceneManagerSupplier.get().changeScene("bookFlightView");
    }

    @FXML
    private void switchToSearchFlight(){
        sceneManagerSupplier.get().changeScene("searchFlight");
    }

    @FXML
    private void switchToEditFlight(){sceneManagerSupplier.get().changeScene("editFlightController");}

    @FXML
    private void switchToViewBooking(){sceneManagerSupplier.get().changeScene("viewBooking");}
}