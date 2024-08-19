package persistence;

import datarecords.FlightData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.*;
import java.util.List;

public interface FlightStorageService {


    FlightData add(FlightData FlightData);

     ObservableList<String> getTimeOptionsForHour();

     ObservableList<String> getTimeOptionsForMinutes();

     ObservableList<String> getAirports();

     ObservableList<GetInfoForFlights> getDataForFlights();

     float getPrice(String flightID);


     ObservableList<String> getPlaneIDs();


    void deleteFlight(String id);

    void insertIntoRecurringFlights(GetInfoForRecurringFlights getInfoForRecurringFlights);

   void insertIntoDeletedTable(String deletedFlightID, String reason);
    ObservableList<GetInfoForCanceledFlights> getDataForDeletedFlights();

     void insertFlightIntoFlightData(FlightData flightData);

     void updateFlightData(FlightData flightData, String selectedFlightID) throws SQLException;

     double priceFetcher(String id);

     double discountFetcher(String id);

     void eliminateStaticDiscount(String currentFlightID, double undiscountedprice, double discount);

     void changeStaticDiscount(String currentFlightID, double discountedprice, double discount);

     void changeDynamicDiscount(String idLabel, boolean boolValue, double trueprice, double dpercent);


     ObservableList<GetInfoForFlights> getFlightsbyId(List<String> flightID);
}
