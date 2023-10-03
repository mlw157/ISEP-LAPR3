package Domain.Model;


import java.util.TreeMap;

/**
 * Class to represent a client of the organization
 * @author Pedro Campos <1211511@isep.ipp.pt>
 */

public class Client extends Entity{

    Company pickupHub;

    public void setPickupHub(Company pickupHub) {
        this.pickupHub = pickupHub;
    }

    public Client(Location location, String name) {
        this.location = location;
        this.name = name;
    }

    private TreeMap<Integer, Basket> basketsMap = new TreeMap<>();

    public TreeMap<Integer, Basket> getBasketsMap() {
        return basketsMap;
    }

    public void setBasketsMap(TreeMap<Integer, Basket> availableProductsMap) {
        this.basketsMap = availableProductsMap;
    }



}
