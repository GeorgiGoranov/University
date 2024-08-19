package persistence;

public class GetInfoForCustomers {

    String id, name, lastName, personalNumber;
    public GetInfoForCustomers(String id, String name, String lastName, String personalNumber){
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.personalNumber = personalNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public String getId() {
        return id;
    }
    public String getName(){
        return name;
    }
}
