package gui.booking;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Helper class for creating a popup window to create new objects.
 *
 * @param <V> The class instance used for creating new objects.
 * @param <M> The manager class for handling objects.
 * @param <B> The builder class for creating object instances.
 */
public class ObjectCreator<V, M, B> {
    private final Stage newStage;
    private final String popupTitle;
    private GridPane gridPane;
    private final V classInstance;
    private final M manager;
    private final B builder;



    /**
     * Constructs an ObjectCreator object.
     *
     * @param clazz       The class instance used for retrieving the class and it's methods.
     * @param manager     The manager class for handling objects.
     * @param builder     The builder class for creating data record object instances.
     * @param popupTitle  The name of the entity.
     */
    public ObjectCreator(V clazz, M manager, B builder, String popupTitle) {
        newStage = new Stage();
        this.classInstance = clazz;
        this.builder = builder;
        this.manager = manager;
        this.popupTitle = popupTitle;
    }


    /**
     * Creates the UI elements for the popup window.
     *
     * @param nameOfObject The name of the object to be created.
     */
    public void createScene(String nameOfObject) {
        // Create the necessary UI elements
        gridPane = new GridPane();
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

        Label labelOfScene = new Label("Create a new " + nameOfObject.toLowerCase());

        // Get the declared methods of the object type
        Method[] methods = classInstance.getClass().getDeclaredMethods();

        int rowIndex = 2;
        for (Method method : methods) {
            // get every getter from the data record (Getters in data records don't have get prefix
            // for example 'getName()' would be 'name()'
            if (!(method.getName().equals("hashCode") || method.getName().equals("toString") || method.getName().equals("equals"))){
                // Extract the property name from the getter method name
                String propertyName = method.getName();
                String[] splitName = propertyName.split("(?=[A-Z])");
                StringBuilder nameBuilt = new StringBuilder();
                for (String word:splitName) {
                    nameBuilt.append(word).append(" ");
                }

                switch (propertyName) {
                    case "lastName" -> {
                        Label label = new Label("Enter " + nameBuilt + "of the "+ nameOfObject +":");
                        TextField textField = new TextField();
                        textField.setPromptText("Last name");
                        gridPane.add(label, 0, rowIndex, 3, 1);
                        gridPane.add(textField, 3, rowIndex, 3, 1);
                        rowIndex++;
                    }
                    case "name" -> {
                        Label label = new Label("Enter " + nameBuilt + "of the " + nameOfObject + ":");
                        TextField textField = new TextField();
                        textField.setPromptText("Name");
                        gridPane.add(label, 0, rowIndex, 3, 1);
                        gridPane.add(textField, 3, rowIndex, 3, 1);
                        rowIndex++;
                    }
                    case "dateOfBirth" -> {
                        Label label = new Label("Enter " + nameBuilt + "of the " + nameOfObject + ":");
                        TextField textField = new TextField();
                        textField.setPromptText("dd-mm-yyyy");
                        gridPane.add(label, 0, rowIndex, 3, 1);
                        gridPane.add(textField, 3, rowIndex, 3, 1);
                        rowIndex++;
                    }
                    case "personalNumber" -> {
                        Label label = new Label("Enter " + nameBuilt + "of the " + nameOfObject + ":");
                        TextField textField = new TextField();
                        textField.setPromptText("Personal number");
                        gridPane.add(label, 0, rowIndex, 3, 1);
                        gridPane.add(textField, 3, rowIndex, 3, 1);
                        rowIndex++;
                    }
                    default -> {
                        // Create labels and text fields for each property
                        Label label = new Label("Enter " + nameBuilt + "of the " + nameOfObject + ":");
                        TextField textField = new TextField();

                        // Add them to the grid pane
                        gridPane.add(label, 0, rowIndex, 3, 1);
                        gridPane.add(textField, 3, rowIndex, 3, 1);

                        rowIndex++;
                    }
                }
            }
        }


        Button saveButton = new Button("Save");
        AtomicInteger finalRowIndex = new AtomicInteger(2);
        saveButton.setOnAction(e -> {
            try {
                // Set the property values using reflection
                for (Method method : methods) {
                    if (!(method.getName().equals("hashCode") || method.getName().equals("toString") || method.getName().equals("equals"))) {
                        TextField textField = (TextField) getNodeByRowColumnIndex(gridPane, finalRowIndex.get());
                        assert textField != null;

                        switch (textField.getPromptText()){
                            case "dd-mm-yyyy" -> {
                                if(isValidDate(textField.getText())){
                                    System.out.println("valid");
                                    finalRowIndex.set(2);
                                } else {
                                    alertSystem("Invalid date");
                                    finalRowIndex.set(2);
                                    return;
                                }
                            }
                            case "Personal number" -> {
                                String pattern = "[0-9]+";
                                if(!(textField.getText().matches(pattern))){
                                    alertSystem("Personal number should only contain numbers");
                                    finalRowIndex.set(2);
                                    return;
                                }
                            }
                            case "Name" -> {
                                if (isInValidString(textField.getText())){
                                    alertSystem("Name should only contain characters");
                                    finalRowIndex.set(2);
                                    return;
                                }
                            }
                            case "Last name" -> {
                                if (isInValidString(textField.getText())){
                                    alertSystem("Last name should only contain characters");
                                    finalRowIndex.set(2);
                                    return;
                                }
                            }
                        }

                        String value = textField.getText();

                        // Invoke the corresponding setter method on the builder
                        String propertyName = method.getName();
                        Method setterMethod = builder.getClass().getMethod("set"+ propertyName, value.getClass());
                        setterMethod.invoke(builder, value);

                        finalRowIndex.getAndIncrement();
                    }
                }

                // Build the object using the builder
                B data = (B) builder.getClass().getMethod("build").invoke(builder);
                // Invoke the insert method on the manager object, passing the data instance
                Method insert = manager.getClass().getMethod("insert" + nameOfObject + "Data", data.getClass());
                boolean isSuccess =  (boolean)insert.invoke(manager, data);
                if (isSuccess){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, popupTitle+" created", ButtonType.CLOSE);
                    alert.setContentText("Success");
                    alert.showAndWait();
                    newStage.close();
                }
                 else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid data.", ButtonType.CLOSE);
                    alert.showAndWait();
                }

            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
                throw new RuntimeException("Failed to create and insert data.");
            }

        });


        gridPane.add(labelOfScene, 2, 0, 4, 1);
        gridPane.add(saveButton, 4, rowIndex, 1, 1);

        Scene scene = new Scene(gridPane, 630, 660);
        newStage.setScene(scene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.showAndWait();
    }


    /**
     * Retrieves the JavaFX node from the given row and column index in the gridPane.
     *
     * @param gridPane The gridPane containing the nodes.
     * @param row      The row index.
     * @return The JavaFX node at the specified row and column index, or null if not found.
     */
    private static javafx.scene.Node getNodeByRowColumnIndex(GridPane gridPane, final int row) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == 3) {
                return node;
            }
        }
        return null;
    }

    /**
     * Checks if a date string is in the valid format 'dd-mm-yyyy'.
     *
     * @param date The date string to be validated.
     * @return true if the date is valid, false otherwise.
     */
    public static boolean isValidDate(String date) {
        // Check if the date string has the format 'dd-mm-yyyy'
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate parsedDate = LocalDate.parse(date, dateFormatter);

            // Check if the date is not a future date
            LocalDate currentDate = LocalDate.now();
            if (parsedDate.isAfter(currentDate)) {
                return false;
            }

            // Check if the person is not more than 122 years old (oldest person's age)
            LocalDate oldestAllowedDate = currentDate.minusYears(122);
            return !parsedDate.isBefore(oldestAllowedDate);

            // All constraints are satisfied
        } catch (Exception e) {
            return false; // Invalid date format
        }
    }

    /**
     * Checks if a string contains only alphabetic characters.
     *
     * @param string The string to be validated.
     * @return true if the string contains only alphabetic characters, false otherwise.
     */
    public boolean isInValidString(String string){
        return !string.matches("[a-zA-Z]+");
    }
    /**
     * Displays an alert dialog with the given message.
     *
     * @param message The message to be displayed in the alert dialog.
     */
    private void alertSystem(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.CLOSE);
        alert.showAndWait();

    }


}
