package Domain.Model;

import com.sun.source.tree.Tree;
import utils.Pair;

import java.util.List;
import java.util.TreeMap;

/**
 * Class to represent a company client of the organization
 * @author Pedro Campos <1211511@isep.ipp.pt>
 */
public class Company extends Entity {

    private boolean isDistributionHub;

    public TreeMap<Integer, List<Pair<Product, List<Pair<Double, Productor>>>>> stockMap = new TreeMap<>(); // day, quantity, productor

    public TreeMap<Integer, List<Pair<Product, List<Pair<Double, Entity>>>>> orderMap = new TreeMap<>(); // day, quantity, productor

    public TreeMap<Integer, List<Pair<Product, Double>>> wantedProductsMap = new TreeMap<>(); // day, product, total quantity wanted


    public Company(Location location, String name) {
        this.location = location;
        this.name = name;
        this.isDistributionHub = false;
    }

    public boolean isDistributionHub() {
        return isDistributionHub;
    }

    public void setDistributionHub(boolean distributionHub) {
        isDistributionHub = distributionHub;
    }

    private TreeMap<Integer, Basket> basketsMap = new TreeMap<>();

    public TreeMap<Integer, Basket> getBasketsMap() {
        return basketsMap;
    }

    public void setBasketsMap(TreeMap<Integer, Basket> availableProductsMap) {
        this.basketsMap = availableProductsMap;
    }

    public TreeMap<Integer, List<Pair<Product, List<Pair<Double, Productor>>>>> getStockMap() {
        return stockMap;
    }



    public void setStockMap(TreeMap<Integer, List<Pair<Product, List<Pair<Double, Productor>>>>> stockMap) {
        this.stockMap = stockMap;
    }

    public TreeMap<Integer, List<Pair<Product, List<Pair<Double, Entity>>>>> getOrderMap() {
        return orderMap;
    }

    public TreeMap<Integer, List<Pair<Product, Double>>> getWantedProductsMap() {
        return wantedProductsMap;
    }


}
