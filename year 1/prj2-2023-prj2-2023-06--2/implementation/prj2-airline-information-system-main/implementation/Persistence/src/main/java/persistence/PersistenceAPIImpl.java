package persistence;

import datarecords.BookingData;
import datarecords.FlightData;

import java.sql.SQLException;
import java.util.List;

/**
 * Actual creator of storage services.
 * @author Informatics Fontys Venlo
 */
class PersistenceAPIImpl implements PersistenceAPI{



    @Override
    public CustomerStorageService getCustomerStorageService() {
        try {
            return new CustomerStorageServiceImpl();
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public PassengerStorageService getPassengerStorageService(){
        try{
            return new PassengerStorageServiceImpl();
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    //using the in PersistenceAPI defined default
    @Override
    public FlightStorageService getFlightStorageService() {
        try{
            return new FlightStorageServiceImpl();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BookingStorageService getBookingStorageService(){
        try {
            return new BookingStorageServiceImpl();
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }
    public TicketStorageService getTicketStorageService() {
        try {
            return new TicketStorageServiceImpl();
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
