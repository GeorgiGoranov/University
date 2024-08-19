package persistence;

import datarecords.TicketData;
import java.util.List;

public interface TicketStorageService {
    TicketData add (TicketData TicketData);
    List<TicketData> getAll();


     void saveTicket(TicketData ticketData);

     String getChosenSeatsForPassenger(String bookingId, String flightId, String passengerId);

     TicketData getSpecifficTicket(String bookingId, String flightId, String passengerId);

     List<String> getChosenSeats(String bookingId, String flightId);

}
