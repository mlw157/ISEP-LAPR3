package Domain.Model;

import utils.Pair;

import java.util.ArrayList;


/**
 * Class to represent a Basket of products
 * @author Pedro Campos <1211511@isep.ipp.pt>
 */
public class Basket {

    private ArrayList<Pair<Product, Double>> products;
    public Basket(ArrayList<Pair<Product, Double>> products) {
        this.products = products;
    }

    public ArrayList<Pair<Product, Double>> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Pair<Product, Double>> products) {
        this.products = products;
    }

    public void addNewProduct(Product product, Double quantity) {
        products.add(new Pair<Product, Double>(product, quantity));
    }

    public void removeProduct(Product product) {
        for (Pair<Product, Double> pair : products) {
            if (pair.first().equals(product)) {
                products.remove(pair);
                break;
            }
        }
    }

    public void addQuantity(Product product, Double quantity) {
        for (Pair<Product, Double> pair : products) {
            if (pair.first().equals(product)) {
                pair.setSecond(pair.second() + quantity);
                break;
            }
        }
    }

    public void removeQuantity(Product product, Double quantity) {
        for (Pair<Product, Double> pair : products) {
            if (pair.first().equals(product)) {
                pair.setSecond(pair.second() - quantity);
                break;
            }
            if (pair.second() < 0) {
                pair.setSecond(0.0);
            }
        }
    }

    public void clearBasket() {
        products.clear();
    }


}
