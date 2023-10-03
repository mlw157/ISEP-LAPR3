package Domain.Model;


import java.util.TreeMap;

/**
 * Abstract class to represent an entity of the system
 * @author Pedro Campos <1211511@isep.ipp.pt>
 */
public abstract class Entity {

    Company pickupHub;

    public void setPickupHub(Company pickupHub) {
        this.pickupHub = pickupHub;
    }

    Location location;
    String name;

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    private TreeMap<Integer, Basket> basketsMap;

    public TreeMap<Integer, Basket> getBasketsMap() {
        return basketsMap;
    }

    public void setBasketsMap(TreeMap<Integer, Basket> availableProductsMap) {
        this.basketsMap = availableProductsMap;
    }


}
