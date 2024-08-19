package datarecords;

public class CustomerDataBuilder {
    private String firstName;
    private String lastName;
    private String personalNumber;

    public CustomerDataBuilder setname(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CustomerDataBuilder setlastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CustomerDataBuilder setpersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
        return this;
    }

    public CustomerData build() {
        return new CustomerData(firstName, lastName, personalNumber);
    }
}
