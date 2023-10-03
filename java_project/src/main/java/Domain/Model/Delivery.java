package Domain.Model;

import utils.Pair;

import java.util.ArrayList;

/**
 * Class to represent an order of a basket
 * @author Pedro Campos <1211511@isep.ipp.pt>
 */

public class Delivery {

    private Entity client;
    private int dayOffset;
    private ArrayList<Pair<Product, Pair<Productor, Double>>> productProductorAndQuantityDeliveredList = new ArrayList<>();

    public void addProduct (Product product, Productor productor, Double quantity) {
        productProductorAndQuantityDeliveredList.add(new Pair<>(product, new Pair<>(productor, quantity)));
    }

    public Delivery(Entity client, int dayOffset) {
        this.client = client;
        this.dayOffset = dayOffset;
    }



    public Entity getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getDayOffset() {
        return dayOffset;
    }

    public void setDayOffset(int dayOffset) {
        this.dayOffset = dayOffset;
    }

    public ArrayList<Pair<Product, Pair<Productor, Double>>> getProductProductorAndQuantityDeliveredList() {
        return productProductorAndQuantityDeliveredList;
    }

    public void setProductProductorAndQuantityDeliveredList(ArrayList<Pair<Product, Pair<Productor, Double>>> productProductorAndQuantityDeliveredList) {
        this.productProductorAndQuantityDeliveredList = productProductorAndQuantityDeliveredList;
    }


}
