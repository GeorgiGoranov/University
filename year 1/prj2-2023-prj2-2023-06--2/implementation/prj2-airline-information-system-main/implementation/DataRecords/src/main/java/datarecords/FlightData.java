package datarecords;

import java.time.LocalDate;

public record FlightData(String flightId,String planeID , String depAirport, String arrAirport, String depTime, String arrTime, LocalDate DoD, LocalDate DoA, Boolean sdiscount, Boolean ddiscount, float price) {


}
