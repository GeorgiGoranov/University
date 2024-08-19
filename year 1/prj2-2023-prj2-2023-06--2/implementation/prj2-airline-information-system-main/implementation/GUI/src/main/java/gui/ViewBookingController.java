package gui;

import businesslogic.BookingManager;
import businesslogic.PassengerManager;
import gui.booking.BookingDO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistence.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class ViewBookingController implements Initializable {

    @FXML
    private TableColumn<GetInfoForBookings, String> colID;
    @FXML
    private TableColumn<GetInfoForBookings, LocalDate> colBookingDate;
    @FXML
    private TableColumn<GetInfoForBookings, String> colCustomerID;
    @FXML
    private TableView<GetInfoForBookings> tableBookings;
    @FXML
    private TextField filterField;
    private final Supplier<SceneManager> sceneManagerSupplier;
    private final BookingManager bookingManager;
    private final PassengerManager passengerManager;
    private final BookingDO bookingObserver;

    private TableView<GetInfoForPassengers> passengers = new TableView<>();

    ObservableList<GetInfoForBookings> bookingList;
    Integer index;

    public ViewBookingController(Supplier<SceneManager> sceneManagerSupplier, BookingManager bookingManager, PassengerManager passengerManager, BookingDO bookingObserver) {
        this.sceneManagerSupplier = sceneManagerSupplier;
        this.bookingManager = bookingManager;
        this.passengerManager = passengerManager;
        this.bookingObserver = bookingObserver;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayBookingInfo();
    }

    @FXML
    void displayBookingInfo() {
        colID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        colCustomerID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerID()));
        colBookingDate.setCellValueFactory(new PropertyValueFactory<GetInfoForBookings, LocalDate>("bookingDate"));

        bookingList = bookingManager.getDataForAllBookings();
        tableBookings.setItems(bookingList);
    }

    @FXML
    void getItems(MouseEvent event) {
        //if clicked with mouse whole row is selected
        index = tableBookings.getSelectionModel().getSelectedIndex();
        //if nothing is clicked with mouse everything is unselected
        if (index <= -1) {
            return;
        }

        BookingDO.saveBookingID(colID.getCellData(index));

        Stage newStage = new Stage();

        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.setTitle("");

        HBox hBox = new HBox();
//        hBox.setPadding(new Insets(100, 20, 20, 20));
        hBox.setAlignment(Pos.BASELINE_CENTER);

        GridPane gridPane = new GridPane();
//        gridPane.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();

        GaussianBlur blurEffect = new GaussianBlur(5);
        Scene scene = tableBookings.getScene();
        scene.getRoot().setEffect(blurEffect);

        Label IDLabel = new Label();
        Label bookingDateLabel = new Label();

        Label IDLabelText = new Label("Booking ID:");
        Label bookingDateLabelText = new Label("Date of Booking:");

        IDLabel.setText(colID.getCellData(index));
        bookingDateLabel.setText(colBookingDate.getCellData(index).toString());

        gridPane.add(IDLabel, 1, 0);
        gridPane.add(bookingDateLabel, 1, 1);
        gridPane.add(IDLabelText, 0, 0);
        gridPane.add(bookingDateLabelText, 0, 1);

        Label passengerText = new Label("Passengers:");
        TableView<GetInfoForPassengers> passengers = passengerTable(colID.getCellData(index));

        Button saveButton = new Button("Close");
        saveButton.setOnAction(e -> {
            // Save data and close the new stage
            displayBookingInfo();
            newStage.close();
            scene.getRoot().setEffect(null);

        });

        vBox.getChildren().addAll(passengerText,passengers,saveButton);

        hBox.getChildren().addAll(gridPane, vBox);

        Scene newScene = new Scene(hBox, 700, 450);
        newStage.setScene(newScene);
        newStage.showAndWait();


    }

    @FXML
    void showPassenger(String passengerId, String fName, String lName, String birthday, String idNumber) {

        BookingDO.savePassengerID(passengerId);

        Stage newStage = new Stage();
        GridPane gridPane = new GridPane();

        Label firstNameLabel = new Label(fName);
        Label lastnameLabel = new Label(lName);
        Label idNumberLabel = new Label(idNumber);
        Label birthdayLabel = new Label(birthday);
        Label idLabel = new Label(passengerId);

        gridPane.add(new Label("PassengerId:"), 0, 0);
        gridPane.add(new Label("First Name:"), 0, 1);
        gridPane.add(new Label("Last Name:"), 0, 2);
        gridPane.add(new Label("Identification Number:"), 0, 3);
        gridPane.add(new Label("Birthdate:"), 0, 4);

        gridPane.add(idLabel, 1, 0);
        gridPane.add(firstNameLabel, 1, 1);
        gridPane.add(lastnameLabel, 1, 2);
        gridPane.add(birthdayLabel, 1, 3);
        gridPane.add(idNumberLabel, 1, 4);


        //later if no seat it say "no seat choosen" and gives button "choose Seat"
        //if seat was already choosen it shows seat
        //need to add seat to database for that but wanna ask first if thats okay
        // also do it on my branch first later when i pushed what i have so far
        gridPane.add(new Label("Seat:"),0,5);

        Button addSeatButton = new Button("Add Seat");
        addSeatButton.setOnAction(e -> {
            newStage.close();
            sceneManagerSupplier.get().changeScene("ChooseSeatController");
        });

        Button saveButton = new Button("Close");
        saveButton.setOnAction(e -> {
            // Save data and close the new stage
            newStage.close();

        });

        gridPane.add(addSeatButton,2,7);
        gridPane.add(saveButton,2,8);

        Scene newScene = new Scene(gridPane, 300, 450);
        newStage.setScene(newScene);
        newStage.showAndWait();
    }

    @FXML
    TableView<GetInfoForPassengers> passengerTable(String bookingId) {

        List<String> passengerId = bookingManager.getPassenger(bookingId).passengerIDs();
        //ObservableList<GetInfoForPassengers> passengersList = passengerManager.getPassengerById((ArrayList<String>) passengerId);

        //bookingObserver.savePassengerData(passengersList);

        TableView<GetInfoForPassengers> passengers = new TableView<>();
        passengers.setMaxHeight(200);
        TableColumn<GetInfoForPassengers, String> colPassengerFName = new TableColumn<GetInfoForPassengers, String>("First name");
        TableColumn<GetInfoForPassengers, String> colPassengerLName = new TableColumn<GetInfoForPassengers, String>("Last Name");
        TableColumn<GetInfoForPassengers, String> colPassengerIdNumber = new TableColumn<GetInfoForPassengers, String>("Id Number");
        TableColumn<GetInfoForPassengers, String> colPassengerBirthday = new TableColumn<GetInfoForPassengers, String>("Birthday");
        TableColumn<GetInfoForPassengers, String> colPId = new TableColumn<GetInfoForPassengers, String>("Id");
        passengers.getColumns().addAll(colPassengerFName, colPassengerLName, colPassengerIdNumber, colPassengerBirthday, colPId);

      //  passengers.setItems(passengersList);
        colPassengerFName.setCellValueFactory(new PropertyValueFactory<GetInfoForPassengers, String>("name"));
        colPassengerLName.setCellValueFactory(new PropertyValueFactory<GetInfoForPassengers, String>("lastName"));
        colPassengerIdNumber.setCellValueFactory(new PropertyValueFactory<GetInfoForPassengers, String>("personalNumber"));
        colPassengerBirthday.setCellValueFactory(new PropertyValueFactory<GetInfoForPassengers, String>("dateOfBirth"));
        colPId.setCellValueFactory(new PropertyValueFactory<GetInfoForPassengers, String>("id"));

        passengers.setOnMouseClicked(MouseEvent -> {
            int indexP = passengers.getSelectionModel().getSelectedIndex();
            if (indexP <= -1) {
                return;
            } else {
                GetInfoForPassengers selectedPassenger = passengers.getItems().get(indexP);
                showPassenger(selectedPassenger.getId(), selectedPassenger.getName(), selectedPassenger.getLastName(), selectedPassenger.getDateOfBirth(), selectedPassenger.getPersonalNumber());
            }
            
        });
        return passengers;
    }

}


