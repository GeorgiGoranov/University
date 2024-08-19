package datarecords;

import java.time.LocalDate;

public record TicketData(String seat, String options, String flightId, String bookingId, String passengerId ) {
}
