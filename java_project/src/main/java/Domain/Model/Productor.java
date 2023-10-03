package Domain.Model;

import java.util.TreeMap;

/**
 * Class to represent a productor of the organization
 * @author Pedro Campos <1211511@isep.ipp.pt>
 */

public class Productor extends Entity {

    public Productor(Location location, String name) {
        this.location = location;
        this.name = name;
    }

    public Basket getDayStock(int dayOffset) {
        return basketsMap.get(dayOffset);

    }

    private TreeMap<Integer, Basket> basketsMap = new TreeMap<>();
    private TreeMap<Integer, Basket> originalBasketsMap = new TreeMap<>();

    public TreeMap<Integer, Basket> getBasketsMap() {
        return basketsMap;
    }

    public void setBasketsMap(TreeMap<Integer, Basket> availableProductsMap) {
        this.basketsMap = availableProductsMap;
    }

    public void setOriginalBasketsMap() {
        originalBasketsMap = (TreeMap<Integer, Basket>) basketsMap.clone();
    }
    public TreeMap<Integer, Basket> getOriginalBasketsMap() {
        return originalBasketsMap;
    }


}
