package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login{

    Button exitBtn;
    @FXML
    private Button employeeButton;
    @FXML
    private Button officerButton;
    @FXML
    private Button managerButton;

    private final Supplier<SceneManager> sceneManagerSupplier;

    public Login(Supplier<SceneManager> sceneManagerSupplier) {
        this.sceneManagerSupplier = sceneManagerSupplier;
    }

    @FXML
    private void switchToViewFlights() throws IOException {
        sceneManagerSupplier.get().changeScene("viewFlightController");
    }



}
