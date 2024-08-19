package gui.booking;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**

 Helper class for creating a popup window to select or create entities.

 @param <T> The type of entities in the table.

 @param <V> The class instance used for creating new entities.

 @param <M> The manager class for handling entities.

 @param <B> The builder class for creating entity instances.
 */
public class EntityPopupHelper<T, V, M, B> {

    private final ObservableList<T> data;
    private T returnData;
    private final TableView<T> table;
    private final String popupTitle;
    private final V classInstance;
    private final M manager;
    private final B builder;


    private Integer index;
    Stage newStage = new Stage();

    /**

     Constructs an EntityPopupHelper object.
     @param data The observable list of entities.
     @param table The table view to display the entities.
     @param popupTitle The title of the popup window.
     @param classInstance The class instance used for creating new entities.
     @param manager The manager class for handling entities.
     @param builder The builder class for creating entity data instances.
     */
    public EntityPopupHelper(ObservableList<T> data, TableView<T> table, String popupTitle, V classInstance, M manager, B builder) {
        this.data = data;
        this.table = table;
        this.popupTitle = popupTitle;
        this.classInstance = classInstance;
        this.manager = manager;
        this.builder = builder;
        returnData = null;
    }

    /**

     Creates and displays the entity popup window.

     @return The selected entity or null if no entity was selected.
     */
    public T createPopup() {
        // Create the necessary UI elements
        index = null;
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.setTitle(popupTitle);

        GridPane gridPane = new GridPane();
        // Configure the gridPane...
        gridPane.setGridLinesVisible(false);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        final int numCols = 8;
        final int numRows = 8;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            gridPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            gridPane.getRowConstraints().add(rowConst);
        }
        TextField filterField = new TextField();


        table.setOnMouseClicked(e -> index = table.getSelectionModel().getSelectedIndex());
        Button addNew = new Button("Create new " + popupTitle.toLowerCase());
        addNew.setVisible(false);
        if (popupTitle.equalsIgnoreCase("passenger") || popupTitle.equalsIgnoreCase("customer")) {

            addNew.setVisible(true);
            addNew.setOnAction(e -> {
                ObjectCreator<V, M, B> objectCreator = new ObjectCreator<>(classInstance, manager, builder, popupTitle);
                objectCreator.createScene(popupTitle);
            });
        }


        Button okButton = new Button("Ok");
        okButton.setOnAction(e -> {
            // Save data and close the new stage
            newStage.close();
            if (index != null && index >= 0) {
                returnData = table.getItems().get(index);
            }
        });

        FilteredList<T> filteredListData = new FilteredList<>(data, b ->true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> filteredListData.setPredicate(person -> {
            if(newValue == null || newValue.isEmpty()){
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();

            Method[] methods = person.getClass().getMethods();

            for (Method method : methods) {
                // Check if the method is a getter (starts with "get" and has no parameters)
                if (method.getName().startsWith("get") && method.getParameterCount() == 0) {
                    try {
                        // Invoke the getter method to get the value
                        Object value = method.invoke(person);

                        if (value != null && value.toString().toLowerCase().contains(lowerCaseFilter)) {
                            // Value matches the filter, return true
                            return true;
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        // Handle any exceptions that occur during method invocation
                        e.printStackTrace();
                    }
                }
            }

            // No getter values match the filter, return false
            return false;
        }));


        // Set the filteredList as the initial items of the table
        table.setItems(filteredListData);

        gridPane.add(okButton, 4, 7, 1, 1);
        gridPane.add(addNew, 6, 6, 3, 1);
        gridPane.add(table, 0, 1, 8, 5);
        gridPane.add(filterField, 0, 0, 8, 1);

        // Add a black border around the GridPane
        Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        gridPane.setBorder(border);

        Scene newScene = new Scene(gridPane, 630, 660);

        newStage.setScene(newScene);
        newStage.showAndWait();

        return returnData;
    }
}