package businesslogic;

import persistence.PersistenceAPI;

/**
 * Actual business logic implementation.
 * 
 * @author Informatics Fontys Venlo
 */
class BusinessLogicAPIImpl implements BusinessLogicAPI {

    final PersistenceAPI persistenceAPI;

    BusinessLogicAPIImpl(PersistenceAPI persistenceAPI) {
        this.persistenceAPI = persistenceAPI;
    }
    
    @Override
    public CustomerManager getCustomerManager() {
        return new CustomerManager(persistenceAPI.getCustomerStorageService());
    }

    @Override
    public FlightManager getFlightManager() {
        return new FlightManager(persistenceAPI.getFlightStorageService());
    }

    @Override
    public BookingManager getBookingManager() {
        return new BookingManager(persistenceAPI.getBookingStorageService());
    }
    @Override
    public TicketManager getTicketManager() {
        return new TicketManager(persistenceAPI.getTicketStorageService());
    }

    @Override
    public PassengerManager getPassengerManager() {return  new PassengerManager(persistenceAPI.getPassengerStorageService());}

}
