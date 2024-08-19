package persistence;

import javax.swing.table.TableColumn;

public class GetInfoForFlights {

    private  int pdiscount;
    String id, planeid,departure,arrival,depTime,arrTime,depDate,arrDate;

    float price;
    boolean sdiscount,ddiscount;

    public GetInfoForFlights(String id, String planeid, String departure, String arrival, String depTime, String arrTime, String depDate, String arrDate, boolean sdiscount, boolean ddiscount, float price) {

        this.id = id;
        this.planeid = planeid;
        this.departure = departure;
        this.arrival = arrival;
        this.depTime = depTime;
        this.arrTime = arrTime;
        this.depDate = depDate;
        this.arrDate = arrDate;
        this.ddiscount = ddiscount;
        this.sdiscount = sdiscount;
        this.price = price;
        this.pdiscount = pdiscount;
    }

    public String getId() {
        return id;
    }

    public String getPlaneid(){
        return planeid;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public String getDepTime() {
        return depTime;
    }

    public String getArrTime() {
        return arrTime;
    }

    public String getDepDate() {
        return depDate;
    }

    public String getArrDate() {
        return arrDate;
    }

    public boolean getSdiscount() {
        return sdiscount;
    }

    public boolean getDdiscount() {
        return ddiscount;
    }

    public float getPrice() {
        return price;
    }
    public float getDiscount() {
        return pdiscount;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setPlaneid(String planeid){
        this.planeid = planeid;
    }


    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public void setArrDate(String arrDate) {
        this.arrDate = arrDate;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setSdiscount(boolean sdiscount) {
        this.sdiscount = sdiscount;
    }

    public void setDdiscount(boolean ddiscount) {
        this.ddiscount = ddiscount;
    }

    @Override
    public String toString(){
        return id + " "+ planeid+ " " + departure+ " " + arrival+ " " +depTime+ " " + arrTime+ " " + depDate+ " " + arrDate+ " " + sdiscount+ " " + ddiscount+ " " + price;
    }
}


