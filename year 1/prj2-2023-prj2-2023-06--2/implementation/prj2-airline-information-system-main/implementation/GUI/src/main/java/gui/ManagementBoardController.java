package gui;

import businesslogic.FlightManager;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import persistence.GetInfoForCanceledFlights;
import persistence.GetInfoForFlights;
import persistence.GetInfoForRecurringFlights;

import javax.swing.*;
import java.net.InterfaceAddress;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Supplier;


public class ManagementBoardController implements Initializable {

    private final Supplier<SceneManager> sceneManagerSupplier;
    private final FlightManager flightManager;

    public ObservableList<GetInfoForFlights> list;


    public ObservableList<GetInfoForCanceledFlights> listOfDeletedFlights;


    @FXML
    private TableColumn<GetInfoForFlights, String> col_a;

    @FXML
    private TableColumn<GetInfoForFlights, String> col_d;

    @FXML
    private TableColumn<GetInfoForFlights, String> col_d_a;

    @FXML
    private TableColumn<GetInfoForFlights, String> col_d_d;

    @FXML
    private TableColumn<GetInfoForFlights, String> col_id;

    @FXML
    private TableColumn<GetInfoForFlights, String> col_t_a;

    @FXML
    private TableColumn<GetInfoForFlights, String> col_t_d;

    @FXML
    private TableColumn<GetInfoForFlights, Float> priceId;
    @FXML
    public TableView<GetInfoForFlights> table_DB;


    @FXML
    private TableColumn<GetInfoForFlights, Boolean> static_discount;
    @FXML
    private TableColumn<GetInfoForFlights, Boolean> dynamic_discount;

    @FXML
    private ImageView burgerM;

    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private AnchorPane pane3;

    @FXML
    private TableView<GetInfoForCanceledFlights> canceled_table;

    @FXML
    private TableColumn<GetInfoForCanceledFlights, String> col_canceled_id;

    @FXML
    private TableColumn<GetInfoForCanceledFlights, Integer> col_id_deleted;


    @FXML
    private DatePicker date_departure_auto;

    @FXML
    private DatePicker date_last_recurrence;

    @FXML
    private DatePicker date_arrival_auto;


    @FXML
    private TextField text_flight_id;

    @FXML
    private TextField text_interval;


    @FXML
    void createRecurringFlight(ActionEvent event) {


        if (date_departure_auto.getValue() == null) {
            JOptionPane.showMessageDialog(null, "To make recurring flight/s you need pick a flight!");
        } else if (text_interval.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "To make recurring flight/s you need type date interval!");
        } else if (date_last_recurrence.getValue() == null) {
            JOptionPane.showMessageDialog(null, "To make recurring flight/s you need pick date limit!");
        } else if (date_last_recurrence.getValue().isBefore(date_departure_auto.getValue()) || date_last_recurrence.getValue().equals(date_departure_auto.getValue())) {
            JOptionPane.showMessageDialog(null, "To make recurring flight/s you need have date in the future!");
        } else {
            int num = Integer.parseInt(text_interval.getText());
            flightManager.createRecurringFlights(text_flight_id.getText(), num, date_departure_auto.getValue(), date_arrival_auto.getValue(), date_last_recurrence.getValue());
            updateTable();
        }

    }

    @FXML
    void checkInput(KeyEvent event) {
        if (event.getCharacter().matches("[^\\e\t\r\\d+$]")) {
            event.consume();
            text_interval.setStyle("-fx-border-color: red");
        } else {
            text_interval.setStyle("-fx-border-color: green");
        }
    }

    @FXML
    void getFlight(MouseEvent event) throws ParseException {

        int index = table_DB.getSelectionModel().getSelectedIndex();

        if (index <= -1) {
            return;
        }


        String selectedDepartureDate = flightManager.getDataForAllFlights().get(index).getDepDate(); // accessing the GetInfoForFlights thought the businessLogic
        String selectedFlightID = flightManager.getDataForAllFlights().get(index).getId();
        String selectedArrivalDate = flightManager.getDataForAllFlights().get(index).getArrDate();

        if (selectedFlightID == null) {
            return;
        }

        Date departureDate = new SimpleDateFormat("yyyy-MM-dd").parse(selectedDepartureDate);
        Instant instant = departureDate.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        Date arrivalDate = new SimpleDateFormat("yyyy-MM-dd").parse(selectedArrivalDate);
        Instant instant2 = arrivalDate.toInstant();
        LocalDate localDate2 = instant2.atZone(ZoneId.systemDefault()).toLocalDate();

        date_departure_auto.setValue(localDate);
        date_arrival_auto.setValue(localDate2);
        text_flight_id.setText(selectedFlightID);


    }

    @FXML
    void getPopUp(MouseEvent event) {

        int index1 = canceled_table.getSelectionModel().getSelectedIndex();


        if (index1 <= -1) {
            return;
        }

        String reasonForCancellation = flightManager.getDataForDeletedFlights().get(index1).getReasonForCancellation();
        Stage newStage = new Stage();

        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.setTitle("");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        //make it blurry for bettwe visuals
        GaussianBlur blurEffect = new GaussianBlur(5);
        Scene scene = table_DB.getScene();
        scene.getRoot().setEffect(blurEffect);


        Label ID = new Label();
        Label Flightid = new Label();
        Label reasonForCancel = new Label();

        TextArea textArea = new TextArea();
        textArea.setPrefSize(10, 20);

        Label IDh = new Label("ID: ");
        Label Flightidh = new Label("Flight ID: ");
        Label reasonForCancelh = new Label("Reason For Cancellation ");


        ID.setText(col_id_deleted.getCellData(index1).toString());
        Flightid.setText(col_canceled_id.getCellData(index1));
        reasonForCancel.setText(reasonForCancellation);

        Button closeButton = new Button("CLOSE");

        closeButton.setOnAction(e -> {
            // close the new stage
            newStage.close();
            scene.getRoot().setEffect(null);
        });

        gridPane.add(ID, 1, 0);
        gridPane.add(Flightid, 1, 1);
        gridPane.add(reasonForCancel, 1, 2);

        //names of the labels
        gridPane.add(IDh, 0, 0);
        gridPane.add(Flightidh, 0, 1);
        gridPane.add(reasonForCancelh, 0, 2);


        gridPane.add(closeButton, 1, 3, 2, 1); // Add the close button to the bottom row


        // Add a black border around the GridPane
        Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));
        gridPane.setBorder(border);

        Scene newScene = new Scene(gridPane, 500, 200);
        newStage.setScene(newScene);
        newStage.showAndWait();
    }


    @FXML
    void deleteChosenFlight(ActionEvent event) {

        int selectedIndex = table_DB.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            Stage newStage = new Stage();

            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.setTitle("");

            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(5, 5, 5, 5));

            //make it blurry for bettwe visuals
            GaussianBlur blurEffect = new GaussianBlur(5);
            Scene scene = table_DB.getScene();
            scene.getRoot().setEffect(blurEffect);

            TextField reason = new TextField();

            TextArea textArea = new TextArea();
            textArea.setPrefSize(10, 20);

            Label Reasonh = new Label("Reason ");

            reason.setPromptText("e.g Due to bad weather conditions.");
            reason.setPrefSize(300, 10);

            Button deleteButton = new Button("Delete");

            deleteButton.setOnAction(e -> {
                //delete function
                if (reason.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "To delete you need to enter a reason!");
                } else {
                    String getFlightID = flightManager.getDataForAllFlights().get(selectedIndex).getId();
                    String getReason = reason.getText();
                    flightManager.insertFlightsIntoDeletedTable(getFlightID, getReason);
                    flightManager.deleteSelectedFlight(getFlightID);
                    newStage.close();
                    scene.getRoot().setEffect(null);
                }
            });

            gridPane.add(reason, 1, 0);

            //name of the label
            gridPane.add(Reasonh, 0, 0);

            gridPane.add(deleteButton, 1, 3, 2, 1); // Add the Save button to the bottom row

            // Add a black border around the GridPane
            Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));
            gridPane.setBorder(border);

            Scene newScene = new Scene(gridPane, 400, 350);
            newStage.setScene(newScene);
            newStage.showAndWait();

        } else {
            if (table_DB.getItems().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Table is empty");
            } else {
                JOptionPane.showMessageDialog(null, "Please select row!");
            }
        }
        updateTable();
        updateCanceledTable();

    }


    public ManagementBoardController(Supplier<SceneManager> sceneManagerSupplier, FlightManager flightManager) {
        this.sceneManagerSupplier = sceneManagerSupplier;
        this.flightManager = flightManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transition();
        updateTable();
        updateCanceledTable();
    }

    private void updateTable() {

        col_id.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, String>("id"));
        col_d.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, String>("departure"));
        col_a.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, String>("arrival"));
        col_t_d.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, String>("depTime"));
        col_t_a.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, String>("arrTime"));
        col_d_d.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, String>("depDate"));
        col_d_a.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, String>("arrDate"));
        static_discount.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, Boolean>("sdiscount"));
        dynamic_discount.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, Boolean>("ddiscount"));
        priceId.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights, Float>("price"));

        list = flightManager.getDataForAllFlights();
        table_DB.setItems(list);
    }


    private void updateCanceledTable() {

        col_id_deleted.setCellValueFactory(new PropertyValueFactory<GetInfoForCanceledFlights, Integer>("id"));
        col_canceled_id.setCellValueFactory(new PropertyValueFactory<GetInfoForCanceledFlights, String>("fid"));

        listOfDeletedFlights = flightManager.getDataForDeletedFlights();
        canceled_table.setItems(listOfDeletedFlights);
    }


    private void transition() {

        pane1.setVisible(false);
        pane3.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.3), pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        burgerM.setOnMouseClicked(event -> {

            pane1.setVisible(true);

            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.3), pane1);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.3), pane2);
            translateTransition1.setByX(+600);
            translateTransition1.play();
            pane3.setVisible(true);
        });

        pane1.setOnMouseClicked(event -> {

            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.3), pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                pane1.setVisible(false);
                pane3.setVisible(false);
            });


            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.3), pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });
    }

    @FXML
    private void goBackToPreviousPage() {
        sceneManagerSupplier.get().changeScene("SearchFlight");
    }

    private void pathToCreateFlight() {
        sceneManagerSupplier.get().changeScene("CreateFlightController");
    }

    private void pathToCreateBooking() {
        sceneManagerSupplier.get().changeScene("bookFlightView");
    }

    private void pathToSearchFlight() {
        sceneManagerSupplier.get().changeScene("SearchFlight");
    }

    private void pathToManagementBoard() {
        sceneManagerSupplier.get().changeScene("ManagementBoardController");
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
    void goToCreateFlight(MouseEvent event) {
        pathToCreateFlight();
    }

    @FXML
    void goToManagementBoard(MouseEvent event) {
        pathToManagementBoard();
    }

    @FXML
    void goToSearchFlight(MouseEvent event) {
        pathToSearchFlight();
    }



}
