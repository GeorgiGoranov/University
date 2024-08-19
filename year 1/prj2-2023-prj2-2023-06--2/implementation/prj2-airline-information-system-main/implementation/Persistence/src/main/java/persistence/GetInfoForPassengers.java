package persistence;

public class GetInfoForPassengers {
    String name;
    String lastName;
    String id;
    String personalNumber;
    String dateOfBirth;
    public GetInfoForPassengers(String id, String name, String lastName, String personalNumber, String dateOfBirth){
        this.name =name;
        this.dateOfBirth = dateOfBirth;
        this.personalNumber = personalNumber;
        this.id = id;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }
    public String getId(){return id;}

    public String getLastName(){return lastName;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
