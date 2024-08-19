package datarecords;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record BookingData(LocalDate bookingDate, String customerID, List<String> flightIDs, List<String> passengerIDs) {
}
