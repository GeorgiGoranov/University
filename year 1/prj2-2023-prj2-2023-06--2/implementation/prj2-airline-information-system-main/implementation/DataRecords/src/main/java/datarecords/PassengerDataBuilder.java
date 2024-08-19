package datarecords;

public class PassengerDataBuilder {
    private String firstName;
    private String lastName;
    private String personalNumber;
    private String dateOfBirth;

    public PassengerDataBuilder setname(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PassengerDataBuilder setlastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PassengerDataBuilder setpersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
        return this;
    }
    public PassengerDataBuilder setdateOfBirth(String dateOfBirth){
        this.dateOfBirth= dateOfBirth;
        return this;
    }

    public PassengerData build() {
        return new PassengerData(firstName, lastName, personalNumber, dateOfBirth);
    }
}
