package persistence;

import java.time.LocalDate;

public class GetInfoForBookings {
    String id, customerID;
    LocalDate bookingDate;


    public GetInfoForBookings(String id, String customerID, LocalDate bookingDate ) {

        this.id = id;
        this.customerID = customerID;
        this.bookingDate = bookingDate;

    }

    public String getId() {
        return id;
    }
    public String getCustomerID() {
        return customerID;
    }
    public LocalDate getBookingDate(){return bookingDate;}


    public void setId(String id) {this.id = id;}
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    public void setBookingDate(LocalDate bookingDate){this.bookingDate = bookingDate;}

}

