package gui;

import businesslogic.FlightManager;
import businesslogic.WeatherAPI;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import persistence.GetInfoForFlights;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class SearchFlight implements Initializable {


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
    public TextField filterField;



    @FXML
    private ImageView burgerM;

    @FXML
    private AnchorPane pane1, pane2,pane3;
    @FXML
    public TextField txtTA;

    @FXML
    public TextField txtAA;

    @FXML
    public TextField txtDA;

    @FXML
    public TextField txtDD;

    @FXML
    public TextField txtID;

    @FXML
    public TextField txtTD;

    @FXML
    private Button manegmentBoardBtn;

    private SceneManager sceneManager;

    private final Supplier<SceneManager> sceneManagerSupplier;

    public ObservableList<GetInfoForFlights> list;
    private double selectedStaticDiscount = 0.0;

    ObservableList<GetInfoForFlights> dataList;
    private final FlightManager flightManager;

    Stage popup1;
    Integer index;


    public SearchFlight(Supplier<SceneManager> sceneManagerSupplier, FlightManager flightManager) {
        this.sceneManagerSupplier = sceneManagerSupplier;
        this.flightManager = flightManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transition();
        displayInfoOfFlights();
        search_flight();
    }

    @FXML
    void exitBtn(MouseEvent event) {
        goBackToPreviousPage();
    }

    @FXML
    void goToManagementBoard(MouseEvent event) {
        pathToManagementBoard();
    }

    @FXML
    void goToCreateBooking(MouseEvent event) {
        pathToCreateBooking();
    }

    @FXML
    void goToSearchFlight(MouseEvent event) {
        pathToSearchFlight();
    }


    private void pathToCreateFlight(){
        sceneManagerSupplier.get().changeScene("CreateFlightController");
    }

    private void pathToCreateBooking(){
        sceneManagerSupplier.get().changeScene("bookFlightView");
    }

    @FXML
    private void goBackToPreviousPage(){
        sceneManagerSupplier.get().changeScene("ViewFlightController");
    }


    private void pathToSearchFlight(){
        sceneManagerSupplier.get().changeScene("SearchFlight");
    }

    Scene scene;

    @FXML
    void goToCreateFlight(MouseEvent event) {
        pathToCreateFlight();
    }

    private void pathToManagementBoard(){
        sceneManagerSupplier.get().changeScene("ManagementBoardController");
    }

    @FXML
    void getItemsPopUp(MouseEvent event) {

        index = table_DB.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }


         popup1 = new Stage();



        popup1.initModality(Modality.APPLICATION_MODAL);
        popup1.initStyle(StageStyle.UNDECORATED);
        popup1.setTitle("");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        //make it blurry for bettwe visuals
        GaussianBlur blurEffect = new GaussianBlur(5);
        scene = table_DB.getScene();
        scene.getRoot().setEffect(blurEffect);

        Label IDLabel = new Label();
        Label DALabel = new Label();
        Label AALabel = new Label();
        Label TDLabel = new Label();
        Label TALabel = new Label();
        Label DDLabel = new Label();
        Label DaLabel = new Label();
        Label sdLabel = new Label();
        Label priceLabel = new Label();
        Label discountLabel = new Label();

        //
        TextArea textArea = new TextArea();
        textArea.setPrefSize(10, 20);
        //
        Label ddisLabel = new Label();

        Label IDLabelh = new Label("Flight ID: ");
        IDLabelh.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label DALabelh = new Label("Departure Airport: ");
        DALabelh.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label AALabelh = new Label("Arrival Airport: ");
        AALabelh.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label TDLabelh = new Label("ETD: ");
        TDLabelh.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label TALabelh = new Label("ETA: ");
        TALabelh.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label DDLabelh = new Label("Date of Departure: ");
        DDLabelh.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label DaLabelh = new Label("Date of Arrival: ");
        DaLabelh.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label sdisLabelh = new Label("Static discount: ");
        sdisLabelh.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label ddisLabelh = new Label("Dynamic discount: ");
        ddisLabelh.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label priceLabelh = new Label("Price of the Flight: ");
        priceLabelh.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label discountLabelh = new Label("Discount %: ");
        discountLabelh.setFont(Font.font("Arial", FontWeight.BOLD, 12));





        IDLabel.setText(col_id.getCellData(index).toString());
        DALabel.setText(col_d.getCellData(index).toString());
        AALabel.setText(col_a.getCellData(index).toString());
        TDLabel.setText(col_t_d.getCellData(index).toString());
        TALabel.setText(col_t_a.getCellData(index).toString());
        DDLabel.setText(col_d_d.getCellData(index).toString());
        DaLabel.setText(col_d_a.getCellData(index).toString());
        sdLabel.setText(static_discount.getCellData(index).toString());
        priceLabel.setText(priceId.getCellData(index).toString());

        //
//        //Static
//        ComboBox<String> sdisComboBox = new ComboBox<>();
//        sdisComboBox.getItems().addAll("true", "false");
//        sdisComboBox.setValue(static_discount.getCellData(index).toString());
//        // Save the selected option to a variable or database
//        sdisComboBox.setOnAction(e -> {
//            String selectedOption = sdisComboBox.getValue();
//            boolean boolValue = Boolean.valueOf(selectedOption);
//            try {
//                dao.changeStaticDiscount(IDLabel.getText(),boolValue);
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        });


        //Checkdate for static discount

        //Dynamic
        ComboBox<String> ddisComboBox = new ComboBox<>();
        ddisComboBox.getItems().addAll("true", "false");
        ddisComboBox.setValue(dynamic_discount.getCellData(index).toString());
        // Save the selected option to a variable or database
//        ddisComboBox.setOnAction(e -> {
//            String selectedOption = ddisComboBox.getValue();
//            boolean boolValue = Boolean.valueOf(selectedOption);
//            try {
//                dao.changeDynamicDiscount(IDLabel.getText(),boolValue);
//            } catch (SQLException ex) {
//                throw new RuntimeException(ex);
//            }
//        });


        sdLabel.setText(static_discount.getCellData(index).toString() );
        ddisLabel.setText(dynamic_discount.getCellData(index).toString());
        priceLabel.setText(flightManager.getPrice(col_id.getCellData(index).toString())+ " €");
        discountLabel.setText(flightManager.getPercentage(col_id.getCellData(index).toString()) + "%");

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            // Save data and close the new stage
            displayInfoOfFlights();
            popup1.close();
            scene.getRoot().setEffect(null);

        });


        Button addDD = new Button("+");
        Button deleteSD = new Button("--");







        gridPane.add(addDD, 2, 8);
        gridPane.add(deleteSD, 2, 7);


        gridPane.add(IDLabel, 1, 0);
        gridPane.add(DALabel, 1, 1);
        gridPane.add(AALabel, 1, 2);
        gridPane.add(TDLabel, 1, 3);
        gridPane.add(TALabel, 1, 4);
        gridPane.add(DDLabel, 1, 5);
        gridPane.add(DaLabel, 1, 6);
        gridPane.add(sdLabel, 1, 7);
        gridPane.add(ddisLabel, 1, 8);
        gridPane.add(priceLabel, 1, 9);
        gridPane.add(discountLabel, 1, 10);



        //names of the labels
        gridPane.add(IDLabelh, 0, 0);
        gridPane.add(DALabelh, 0, 1);
        gridPane.add(AALabelh, 0, 2);
        gridPane.add(TDLabelh, 0, 3);
        gridPane.add(TALabelh, 0, 4);
        gridPane.add(DDLabelh, 0, 5);
        gridPane.add(DaLabelh, 0, 6);
        gridPane.add(sdisLabelh, 0, 7);
        gridPane.add(ddisLabelh, 0, 8);
        gridPane.add(priceLabelh, 0 , 9);
        gridPane.add(discountLabelh, 0 , 10);
        //gridPane.add(currentValueLabel, 0, 10);

        //save button that closes
        gridPane.add(saveButton, 0, 13, 2, 2); // Add the Save button to the bottom row

        gridPane.setBorder(new Border(new BorderStroke(Paint.valueOf("#D2B48C"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,new BorderWidths(3))));
        gridPane.setStyle("-fx-background-color: #F5DEB3;");

        saveButton.setStyle("-fx-background-color: #4CAF50;");
        saveButton.setOnMouseEntered(e -> saveButton.setStyle("-fx-background-color: #66BB6A;")); // Change background color on hover
        saveButton.setOnMouseExited(e -> saveButton.setStyle("-fx-background-color: #4CAF50;")); // Restore background color on exit
        saveButton.setOnMousePressed(e -> saveButton.setScaleX(0.8)); // Scale down on button press
        saveButton.setOnMouseReleased(e -> saveButton.setScaleX(1.0)); // Restore scale on button release
        saveButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));


        addDD.setStyle("-fx-background-color: #4CAF50;");
        addDD.setOnMouseEntered(e -> addDD.setStyle("-fx-background-color: #66BB6A;")); // Change background color on hover
        addDD.setOnMouseExited(e -> addDD.setStyle("-fx-background-color: #4CAF50;")); // Restore background color on exit
        addDD.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        deleteSD.setStyle("-fx-background-color: #4CAF50;");
        deleteSD.setOnMouseEntered(e -> deleteSD.setStyle("-fx-background-color: #66BB6A;")); // Change background color on hover
        deleteSD.setOnMouseExited(e -> deleteSD.setStyle("-fx-background-color: #4CAF50;")); // Restore background color on exit
        deleteSD.setFont(Font.font("Arial", FontWeight.BOLD, 12));




        Scene newScene = new Scene(gridPane, 300, 450);

        addDD.setOnAction(e -> {


            if (static_discount.getCellData(index).toString().contains("true")){
                bigErrorAlert(" You are not allowed to place a Dynamic Discount whilst a Static Discount is active");
            } else{
                newScene.getRoot().setEffect(blurEffect);
            displayDynamicDiscount(newScene);
            }


        });
        deleteSD.setOnAction(e -> {


            newScene.getRoot().setEffect(blurEffect);
            displayEliminateStaticDiscount(newScene);

        });


        popup1.setScene(newScene);
        popup1.showAndWait();

    }




    private void displayEliminateStaticDiscount(Scene prevScene) {

        index = table_DB.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }


        Stage newStage = new Stage();

        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.setTitle("");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(165);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(5, 5, 5, 5));



        Label Title = new Label("Are you sure you want to delete\nall static discounts for this month?");

        Title.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        Button noButton = new Button("No");
        noButton.setOnAction(no -> {

            displayInfoOfFlights();
            newStage.close();
            prevScene.getRoot().setEffect(null);
            scene.getRoot().setEffect(null);
            popup1.close();



        });
        Button yesButton = new Button("Yes");


        yesButton.setOnAction(e -> {

//            displayInfoOfFlights();
            if (flightManager.eliminateDiscountonFlights(col_d_d.getCellData(index).toString(),selectedStaticDiscount)){

                successAlert("All static discounts have been deleted");
                //everythingcloses


            } else
                successAlert("There is no discount to be deleted");

            displayInfoOfFlights();
            newStage.close();
            prevScene.getRoot().setEffect(null);
            scene.getRoot().setEffect(null);
            popup1.close();

        });


        gridPane.add(Title, 0, 0, 2, 1);
        gridPane.add(yesButton, 0, 1);
        gridPane.add(noButton, 1, 1);

        gridPane.setBorder(new Border(new BorderStroke(Paint.valueOf("#D2B48C"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,new BorderWidths(3))));
        gridPane.setStyle("-fx-background-color: #F5DEB3;");

        yesButton.setStyle("-fx-background-color: #4CAF50;");
        yesButton.setOnMouseEntered(e -> yesButton.setStyle("-fx-background-color: #66BB6A;")); // Change background color on hover
        yesButton.setOnMouseExited(e -> yesButton.setStyle("-fx-background-color: #4CAF50;")); // Restore background color on exit
        yesButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        noButton.setStyle("-fx-background-color: #4CAF50;");
        noButton.setOnMouseEntered(e -> noButton.setStyle("-fx-background-color: #66BB6A;")); // Change background color on hover
        noButton.setOnMouseExited(e -> noButton.setStyle("-fx-background-color: #4CAF50;")); // Restore background color on exit
        noButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Scene newScene = new Scene(gridPane, 300, 150);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        newStage.setX(screenBounds.getMaxX() - newScene.getWidth() - 300);
        newStage.setY((screenBounds.getMaxY() - newScene.getHeight()) / 2);

        newStage.setScene(newScene);
        newStage.showAndWait();

    }



    private void displayDynamicDiscount(Scene prevScene) {

        WeatherAPI api = new WeatherAPI();

        index = table_DB.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }

        Stage newStage = new Stage();

        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.setTitle("");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER_LEFT);
        gridPane.setHgap(10);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(5, 5, 5, 5));

        //make it blurry for bettwe visuals


        Button saveButton = new Button("Save");
        saveButton.setOnAction(save -> {
            // Save data and close the new stage
//            displayInfoOfFlights();
            displayInfoOfFlights();
            newStage.close();
            prevScene.getRoot().setEffect(null);
            scene.getRoot().setEffect(null);
            popup1.close();

        });


        /*
        current price
        current discount
        discounted price

        set discount

        save button
         */
        Label initialValueLabel = new Label("Arrival Airport: ");
        initialValueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label currentdiscountLabel = new Label("Current discount: ");
        currentdiscountLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label arrivalTimeLabel = new Label("Arrival time: ");
        arrivalTimeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label currentValueLabel = new Label("Sunset time: ");
        currentValueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label discountLabel = new Label("Contains dynamic\ndiscount?: ");
        discountLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        //
        Label discountParameterLabel = new Label("Additional discount: ");
        discountParameterLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label sunsetDiscountLabel = new Label("Sunset discount: ");
        sunsetDiscountLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        TextField discountParameter = new TextField();
        discountParameter.setPrefWidth(50);
        //

        Label arrAir = new Label();
        Label arrTime = new Label();
        Label sunTime = new Label();
        Label currentdiscount = new Label();
        Label sunsetdiscount = new Label();


        StringProperty labelText = new SimpleStringProperty();
        currentdiscount.textProperty().bind(labelText);
        labelText.set(flightManager.getPercentage(col_id.getCellData(index).toString()) + "%");

       // currentdiscount.setText(flightManager.getPercentage(col_id.getCellData(index).toString()) + "%");


        ComboBox<String> ddisComboBox = new ComboBox<>();
        ddisComboBox.getItems().addAll("true", "false");
        ddisComboBox.setValue((dynamic_discount.getCellData(index).toString()));
        //TODO send to business logic
        String getSunsetTime = api.API(col_a.getCellData(index).toString());
        //
        arrAir.setText((col_a.getCellData(index).toString()));
        arrTime.setText(col_t_a.getCellData(index).toString());
        sunTime.setText(getSunsetTime );

        // Save the selected option to a variable or database
        LocalTime arrivalTime = LocalTime.parse(arrTime.getText());
        LocalTime sunsetTime = LocalTime.parse(getSunsetTime);
        //
        sunsetdiscount.setText(String.valueOf(flightManager.calculateSunsetPercentage(arrivalTime,sunsetTime))+"%");


        ddisComboBox.setOnAction(changeInComboBox -> {

            flightManager.checkifnotNull(discountParameter);
            double discountParam = flightManager.convertToDouble(discountParameter.getText());



                String selectedOption = ddisComboBox.getValue();
                boolean boolValue = Boolean.valueOf(selectedOption);


                if (boolValue == true) {
                    if (checkValidDiscount(discountParam, 30)) {
                    flightManager.changeDD(col_id.getCellData(index).toString(), boolValue, true, arrivalTime, sunsetTime, discountParam);
                    displayInfoOfFlights();
                    successAlert("All operations successful, a discount of " + flightManager.getPercentage(col_id.getCellData(index).toString()) + "% has been added");
                    }else {
                        ddisComboBox.cancelEdit();
                        saveButton.fire();
                    }
                } else if (boolValue == false) {
                    double removeddiscount = flightManager.getPercentage(col_id.getCellData(index).toString());
                    flightManager.changeDD(col_id.getCellData(index).toString(), boolValue, false, arrivalTime, sunsetTime, discountParam);
                    successAlert("All operations successful, a discount of " + removeddiscount  + "% has been removed");
                    displayInfoOfFlights();

                }

                displayInfoOfFlights();
                labelText.set(flightManager.getPercentage(col_id.getCellData(index).toString()) + "%");


        });




        gridPane.add(initialValueLabel, 0, 0);
        gridPane.add(arrivalTimeLabel, 0, 1);
        gridPane.add(currentValueLabel, 0, 2);
        gridPane.add(discountParameterLabel, 0, 3);
        gridPane.add(currentdiscountLabel, 0, 4);
        gridPane.add(sunsetDiscountLabel, 0, 5);

        gridPane.add(discountLabel, 0, 6);
        gridPane.add(saveButton, 0, 7);

        gridPane.add(arrAir, 1, 0);
        gridPane.add(arrTime, 1, 1);
        gridPane.add(sunTime, 1, 2);
        gridPane.add(discountParameter, 1, 3);
        gridPane.add(currentdiscount, 1, 4);
        gridPane.add(sunsetdiscount, 1, 5);
        gridPane.add(ddisComboBox, 1, 6);





        gridPane.setBorder(new Border(new BorderStroke(Paint.valueOf("#D2B48C"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,new BorderWidths(3))));
        gridPane.setStyle("-fx-background-color: #F5DEB3;");
        saveButton.setStyle("-fx-background-color: #4CAF50;");

        saveButton.setOnMouseEntered(saveButtonChangeColor1 -> saveButton.setStyle("-fx-background-color: #66BB6A;")); // Change background color on hover
        saveButton.setOnMouseExited(saveButtonChangeColor2 -> saveButton.setStyle("-fx-background-color: #4CAF50;")); // Restore background color on exit
        saveButton.setOnMousePressed(saveButtonAnimation1 -> saveButton.setScaleX(0.8)); // Scale down on button press
        saveButton.setOnMouseReleased(saveButtonAnimation2 -> saveButton.setScaleX(1.0)); // Restore scale on button release
        saveButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Scene newScene = new Scene(gridPane, 250, 350);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        newStage.setX(screenBounds.getMaxX() - newScene.getWidth() - 340);
        newStage.setY((screenBounds.getMaxY() - newScene.getHeight()) / 2);

        newStage.setScene(newScene);
        newStage.showAndWait();


    }

    private void updateGridpane() {


    }

    @FXML
    public void displayStaticDiscount1(){
        if (!(flightManager.checkIfStaticDiscountExists()))
        displayStaticDiscount();
        else
            bigErrorAlert(" You are not allowed to place a Static Discount whilst a Dynamic Discount is active");
    }


    public void displayStaticDiscount(){
        /*
        Clicks on button, pop up shows up
        for which month would you like to apply the static discount
        howmuch will the discount be
        Congradulations discount was applied!
         */
        Stage newStage = new Stage();

        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.setTitle("");

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(25);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        //make it blurry for bettwe visuals
        GaussianBlur blurEffect = new GaussianBlur(5);
        Scene scene = table_DB.getScene();
        scene.getRoot().setEffect(blurEffect);

        ComboBox<Integer> selectMonthComboBox = new ComboBox<>();
        selectMonthComboBox.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12);
        TextField discount = new TextField();
        discount.setPrefWidth(50);





        Label monthLabel = new Label("Select Month: ");
        monthLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        Label Title = new Label("Apply a new Static discount");
        Title.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        Label selectDiscountLabel = new Label("Percentage of Discount: ");
        selectDiscountLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));


        Button saveButton = new Button("Save");
        saveButton.setOnAction(saveB -> {


            // check if values are null
            if (flightManager.checkifnotNull(selectMonthComboBox.getValue()) & flightManager.checkifnotNull(discount.getText())) {
                //convert value to integer
                int selectedMonth = selectMonthComboBox.getValue();
                double selectedDiscount = flightManager.convertToDouble(discount.getText());
                selectedStaticDiscount = selectedDiscount;
                if (checkValidDiscount(selectedDiscount, 100)) {

                    if (flightManager.applyStaticDiscount(selectedMonth, selectedDiscount)) {
                        successAlert("All operations successful. Your static discount has been saved.");

                    } else {
                        bigErrorAlert("There are no flights saved with that month or discount already exists for that month");
                    }


                }

            } else {
                errorAlert("Values cannot bet Null");
            }




        });
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            // Save data and close the new stage
            displayInfoOfFlights();
            newStage.close();
            scene.getRoot().setEffect(null);

        });

        gridPane.add(Title, 0, 0);

        gridPane.add(monthLabel, 0, 1);
        gridPane.add(selectMonthComboBox, 1, 1);
        gridPane.add(selectDiscountLabel, 0, 2);
        gridPane.add(discount, 1, 2);

        gridPane.add(saveButton, 0, 3, 2, 2);
        gridPane.add(closeButton, 1, 3, 2, 2);

        gridPane.setBorder(new Border(new BorderStroke(Paint.valueOf("#D2B48C"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,new BorderWidths(3))));
        gridPane.setStyle("-fx-background-color: #F5DEB3;");

        saveButton.setStyle("-fx-background-color: #4CAF50;");
        saveButton.setOnMouseEntered(e -> saveButton.setStyle("-fx-background-color: #66BB6A;")); // Change background color on hover
        saveButton.setOnMouseExited(e -> saveButton.setStyle("-fx-background-color: #4CAF50;")); // Restore background color on exit
        saveButton.setOnMousePressed(e -> saveButton.setScaleX(0.8)); // Scale down on button press
        saveButton.setOnMouseReleased(e -> saveButton.setScaleX(1.0)); // Restore scale on button release
        saveButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        closeButton.setStyle("-fx-background-color: #4CAF50;");
        closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: #66BB6A;")); // Change background color on hover
        closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: #4CAF50;")); // Restore background color on exit
        closeButton.setOnMousePressed(e -> closeButton.setScaleX(0.8)); // Scale down on button press
        closeButton.setOnMouseReleased(e -> closeButton.setScaleX(1.0)); // Restore scale on button release
        closeButton.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Scene newScene = new Scene(gridPane, 350, 250);

        if (flightManager.staticdiscountAlreadyExists())
        {
            warningAlert("A static discount already exists: refrain from adding too many discounts ");
        }
        newStage.setScene(newScene);
        newStage.showAndWait();
    }


    @FXML
    void displayInfoOfFlights(){

        col_id.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("id"));
        col_d.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("departure"));
        col_a.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("arrival"));
        col_t_d.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("depTime"));
        col_t_a.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("arrTime"));
        col_d_d.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("depDate"));
        col_d_a.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("arrDate"));
        static_discount.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,Boolean>("sdiscount"));
        dynamic_discount.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,Boolean>("ddiscount"));
        priceId.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,Float>("price"));




        list = flightManager.getDataForAllFlights();
        table_DB.setItems(list);

    }


    @FXML
    void search_flight(){
        col_id.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("id"));
        col_d.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("departure"));
        col_a.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("arrival"));
        col_t_d.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("depTime"));
        col_t_a.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("arrTime"));
        col_d_d.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("depDate"));
        col_d_a.setCellValueFactory(new PropertyValueFactory<GetInfoForFlights,String>("arrDate"));

        dataList = flightManager.getDataForAllFlights();
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

    private void errorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void bigErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);

// Get the alert dialog stage
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

// Set a custom size for the alert dialog
        stage.setWidth(500);  // Set the desired width
        stage.setHeight(300); // Set the desired height
        alert.showAndWait();
    }
    private void successAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setPrefWidth(300); // Set the preferred width of the alert dialog pane

        // Set the alert style to a small dialog window
        alert.getDialogPane().getStyleClass().add("small-alert");

        // Show the alert and wait for the user to close it
        alert.showAndWait();
    }
    private void warningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setPrefWidth(350); // Set the preferred width of the alert dialog pane

        // Set the alert style to a small dialog window
        alert.getDialogPane().getStyleClass().add("small-alert");

        // Show the alert and wait for the user to close it
        alert.showAndWait();
    }
    private boolean checkValidDiscount(double selectedDiscount, int limit){

        if (selectedDiscount > limit){
            errorAlert("Additional discount cannot be more then "+ limit +"%");
            return false;
        }

        if (selectedDiscount == -3) {
            errorAlert("Only discounts of type Double are accepted");
            return false;
        }
        else if (selectedDiscount == -1) {
            errorAlert("Discounts cannot be negative");
            return false;
        }
        else if (selectedDiscount == -2){
            errorAlert("Discounts cannot be more than 100 percent");
        return false;
        }
        return true;
    }
    private boolean checkValidMonth(int selectedMonth) {
      if (flightManager.checkValidMonth(selectedMonth)){
            return true;
        }
        errorAlert("This month already contains a static discount");
        return false;
    }



}

