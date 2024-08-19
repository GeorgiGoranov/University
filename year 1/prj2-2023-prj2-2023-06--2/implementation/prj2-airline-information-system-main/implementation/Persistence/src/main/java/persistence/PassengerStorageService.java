package persistence;

import datarecords.PassengerData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface PassengerStorageService {
      boolean insertPassengerData(PassengerData passengerData);
      ObservableList<GetInfoForPassengers> getDataForPassengers();
      ObservableList<GetInfoForPassengers> getPassengerById(ArrayList<String> id);
}