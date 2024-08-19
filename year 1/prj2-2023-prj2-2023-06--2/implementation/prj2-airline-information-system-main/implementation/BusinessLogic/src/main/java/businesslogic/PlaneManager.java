package businesslogic;

import datarecords.PlaneData;
import persistence.PlaneStorageServiceImpl;

public class PlaneManager {
    private final PlaneStorageServiceImpl plainStorageService;

    public PlaneManager(PlaneStorageServiceImpl plainStorageService ) {
        this.plainStorageService = plainStorageService;
    }

    public PlaneData add(PlaneData planeData){
        return plainStorageService.add(planeData);
    }
}
