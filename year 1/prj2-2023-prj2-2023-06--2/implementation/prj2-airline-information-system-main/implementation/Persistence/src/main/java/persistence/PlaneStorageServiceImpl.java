package persistence;

import datarecords.PlaneData;

import java.util.List;

public class PlaneStorageServiceImpl implements PlaneStorageService {
    @Override
    public PlaneData add(PlaneData planeData){
        return new PlaneData(planeData.id(), planeData.plainType(), planeData.seats());
    }
    @Override
    public List<PlaneData> getAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
