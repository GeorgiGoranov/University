package persistence;

import java.time.LocalDate;

public class GetInfoForRecurringFlights {

    String duplicatedFlightId;
    LocalDate dateOfDeparture, dateOfArrival,dayLimit;
    int dayInterval;

    public GetInfoForRecurringFlights(String duplicatedFlightId,int dayInterval ,LocalDate dateOfDeparture, LocalDate dateOfArrival, LocalDate dayLimit) {
        this.duplicatedFlightId = duplicatedFlightId;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.dayLimit = dayLimit;
        this.dayInterval = dayInterval;
    }

    public String getDuplicatedFlightId() {
        return duplicatedFlightId;
    }

    public void setDuplicatedFlightId(String duplicatedFlightId) {
        this.duplicatedFlightId = duplicatedFlightId;
    }

    public LocalDate getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(LocalDate dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public LocalDate getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(LocalDate dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public LocalDate getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(LocalDate dayLimit) {
        this.dayLimit = dayLimit;
    }

    public int getDayInterval() {
        return dayInterval;
    }

    public void setDayInterval(int dayInterval) {
        this.dayInterval = dayInterval;
    }


}
