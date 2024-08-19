package businesslogic;

import businesslogic.exceptions.InvalidDataBusinessLogicException;
import datarecords.PassengerData;
import javafx.collections.ObservableList;
import persistence.GetInfoForPassengers;
import persistence.PassengerStorageService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class PassengerManager {

    private final PassengerStorageService passengerStorageService;

    public PassengerManager( PassengerStorageService passengerStorageService ) {
        this.passengerStorageService = passengerStorageService;
    }

    public boolean insertPassengerData(PassengerData passengerData) {
        isValid(passengerData);
        return passengerStorageService.insertPassengerData(passengerData);

    }
    public ObservableList<GetInfoForPassengers> getPassengerData() {
            return passengerStorageService.getDataForPassengers();
    }
    public ObservableList<GetInfoForPassengers> getPassengerById(ArrayList<String> passengerId){
        return passengerStorageService.getPassengerById(passengerId);
    }
    public void isValid(PassengerData passengerData){
        try {
            if (passengerData == null) {
                throw new InvalidDataBusinessLogicException("Passenger data null:");
            }
            if (passengerData.name().isBlank() || passengerData.lastName().isBlank() || passengerData.personalNumber().isBlank()) {
                throw new InvalidDataBusinessLogicException("All fields must be filled");
            }
            if (!passengerData.name().matches("[a-zA-Z]+") || !passengerData.lastName().matches("[a-zA-Z]+")) {
                throw new InvalidDataBusinessLogicException("Passenger name and last name should only contain characters");
            }
            if (!passengerData.personalNumber().matches("[0-9]+")) {
                throw new InvalidDataBusinessLogicException("Personal number should only contain digits");
            }
            if (!(isValidDate(passengerData.dateOfBirth()))) {
                throw new InvalidDataBusinessLogicException("Invalid birth date");
            }
        }catch (InvalidDataBusinessLogicException e){
            throw new InvalidDataBusinessLogicException(e.getMessage(), e.getCause());
        }

    }
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
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
