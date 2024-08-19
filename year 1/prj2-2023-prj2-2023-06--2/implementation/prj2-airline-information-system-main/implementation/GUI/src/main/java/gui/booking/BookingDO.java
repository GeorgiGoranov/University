package gui.booking;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.GetInfoForCustomers;
import persistence.GetInfoForFlights;
import persistence.GetInfoForPassengers;

/**
 * The Booking Data Object (BookingDO) class handles storing and retrieving data related to booking management.
 */
public class BookingDO {
    private ObservableList<GetInfoForFlights> flightsData = FXCollections.observableArrayList();
    private ObservableList<GetInfoForCustomers> customersData = FXCollections.observableArrayList();
    private ObservableList<GetInfoForPassengers> passengersData = FXCollections.observableArrayList();
    private static String bookingID;
    private static String passengerID;


    public ObservableList<GetInfoForFlights> getFlightsData(){
        return flightsData;
    }

    public void saveFlightData (ObservableList<GetInfoForFlights> flightChanges) {
        flightsData = FXCollections.observableArrayList(flightChanges);
    }
    public ObservableList<GetInfoForCustomers> getCustomersData(){
        return customersData;
    }

    public void saveCustomerData (ObservableList<GetInfoForCustomers> customerChanges) {
        customersData = FXCollections.observableArrayList(customerChanges);
    }
    public ObservableList<GetInfoForPassengers> getPassengerData(){
        return passengersData;
    }

    public void savePassengerData (ObservableList<GetInfoForPassengers> passengerChanges) {
        passengersData = FXCollections.observableArrayList(passengerChanges);
    }

    public void clearAllData(){
        passengersData.clear();
        customersData.clear();
        flightsData.clear();
    }
    public static String getBookingID() {
        return bookingID;
    }

    public static void saveBookingID(String id) {
        bookingID = id;
    }

    public static String getPassengerID() {
        return passengerID;
    }

    public static void savePassengerID(String id) {
        passengerID = id;
    }




}
