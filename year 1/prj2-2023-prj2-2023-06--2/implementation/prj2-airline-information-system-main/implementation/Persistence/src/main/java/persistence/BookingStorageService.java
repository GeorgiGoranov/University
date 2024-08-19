package persistence;

import datarecords.BookingData;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface BookingStorageService {
    BookingData add(BookingData bookingData);
    List<BookingData> getAll();
    String insertBookingData(BookingData bookingData);
    Float getBookingPrice(String bookingID);
    ObservableList<GetInfoForBookings> getDataForBookings();

    BookingData getPassenger(String bookingId);

}
