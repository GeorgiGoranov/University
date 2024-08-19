package businesslogic;

/**
 * API of the BusinessLogic layer.
 * 
 * @author Informatics Fontys Venlo
 */
public interface BusinessLogicAPI {
    
    CustomerManager getCustomerManager();
    FlightManager getFlightManager();
    BookingManager getBookingManager();
    TicketManager getTicketManager();
    PassengerManager getPassengerManager();

    
}
