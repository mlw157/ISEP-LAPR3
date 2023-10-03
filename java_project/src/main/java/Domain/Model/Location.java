package Domain.Model;

/**
 * Class to represent the location of an entity
 * @author Pedro Campos <1211511@isep.ipp.pt>
 */
public class Location {
    String locationId;
    double latitude;
    double longitude;

    public Location(String locationId, double latitude, double longitude) {
        this.locationId = locationId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getLocationId() {
        return locationId;
    }

}
