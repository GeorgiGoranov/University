package persistence;

public class GetInfoForCanceledFlights {

    int id;
    String fid,reasonForCancellation;

    public GetInfoForCanceledFlights(int id, String fid, String reasonForCancellation) {
        this.id = id;
        this.fid = fid;
        this.reasonForCancellation = reasonForCancellation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getReasonForCancellation() {
        return reasonForCancellation;
    }

    public void setReasonForCancellation(String reasonForCancellation) {
        this.reasonForCancellation = reasonForCancellation;
    }
}
