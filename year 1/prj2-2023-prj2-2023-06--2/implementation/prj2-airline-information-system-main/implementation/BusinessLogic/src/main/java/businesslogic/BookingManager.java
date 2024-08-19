package businesslogic;

import businesslogic.exceptions.InvalidDataBusinessLogicException;
import datarecords.BookingData;
import javafx.collections.ObservableList;
import persistence.BookingStorageService;
import persistence.GetInfoForBookings;

import java.time.LocalDate;


public class BookingManager {
    private final BookingStorageService bookingStorageService;

    public BookingManager(persistence.BookingStorageService bookingStorageService) {
        this.bookingStorageService = bookingStorageService;
    }
    public BookingData add(BookingData bookingData ){
        validData(bookingData);
        return bookingStorageService.add(bookingData);
    }
    public String insertBookingData(BookingData bookingData) {
        validData(bookingData);
        return bookingStorageService.insertBookingData(bookingData);
    }

    public  Float getBookingPrice(String bookingID){
            return bookingStorageService.getBookingPrice(bookingID);
    }

    public void validData(BookingData bookingData){
        try {
            if (bookingData == null) {
                throw new InvalidDataBusinessLogicException("Booking data is null");
            }
            if (bookingData.passengerIDs().isEmpty()) {
                throw new InvalidDataBusinessLogicException("Passenger ID list is empty");
            }
            if (bookingData.flightIDs().isEmpty()) {
                throw new InvalidDataBusinessLogicException("Flight ID list is empty");
            }

            LocalDate now = LocalDate.now();
            if(bookingData.bookingDate().isAfter(now)){
                throw new InvalidDataBusinessLogicException("Booking date is a future date");
            }
        }catch (InvalidDataBusinessLogicException e){
            throw new InvalidDataBusinessLogicException(e.getMessage(), e.getCause());
        }
    }

    public ObservableList<GetInfoForBookings> getDataForAllBookings() {
        return bookingStorageService.getDataForBookings();
    }

    public BookingData getPassenger(String bookingId){return bookingStorageService.getPassenger(bookingId);}
}
