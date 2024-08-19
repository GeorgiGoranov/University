package datarecords;

import java.time.LocalDate;

public record CanceledFlightsRecord(Integer ID,String planeID , String depAirport, String arrAirport, String depTime, String arrTime, LocalDate DoD, LocalDate DoA, Boolean sdiscount, Boolean ddiscount, float price, String reasonForCancellation) {
}
