package persistence;

public class GetPlaneInfo {
int seats;
String planeID;
String planeType;

public GetPlaneInfo(String planeID, String planeType, int seats){
    this.planeID = planeID;
    this.planeType = planeType;
    this.seats = seats;
}

    public String getPlaneID(){
        return planeID;
    }

    public String getPlaneType(){
        return planeType;
    }

    public int getSeats(){
        return seats;
    }

    public void setPlaneType(){
        this.planeType = planeType;
    }

    public void setSeats(){
        this.seats = seats;
    }
}
