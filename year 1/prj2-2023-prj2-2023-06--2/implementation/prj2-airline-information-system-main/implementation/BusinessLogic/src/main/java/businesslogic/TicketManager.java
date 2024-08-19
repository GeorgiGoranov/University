package businesslogic;

import datarecords.TicketData;
import persistence.TicketStorageService;

import java.util.List;
import java.util.Random;

public class TicketManager {
    private final persistence.TicketStorageService ticketStorageService;

    public TicketManager( TicketStorageService ticketStorageService ) {
        this.ticketStorageService = ticketStorageService;
    }

    public void saveTicket(String seat, String options, String flightId, String bookingId, String passengerId){
        TicketData ticketData = new TicketData(seat, options, flightId, bookingId, passengerId);
        ticketStorageService.saveTicket(ticketData);}

    public TicketData add(TicketData ticketData ){
        return ticketStorageService.add(ticketData);
    }

    public String getChosenSeatsForPassenger(String bookingId, String flightId, String passengerId){return ticketStorageService.getChosenSeatsForPassenger(bookingId, flightId, passengerId);}

    public TicketData getSpecifficTicket(String bookingId, String flightId, String passengerId){return ticketStorageService.getSpecifficTicket(bookingId, flightId, passengerId);}
    public List<String> getChosenSeats(String bookingId, String flightId){return ticketStorageService.getChosenSeats(bookingId, flightId);}

    public String randomSeat(){
        Random random = new Random();
        char randomLetter = (char) (random.nextInt('F' - 'A' + 1) + 'A');
        int randomNumber = random.nextInt(21 - 1 + 1) + 1;

        return String.valueOf(randomLetter) + randomNumber;
    }

}
