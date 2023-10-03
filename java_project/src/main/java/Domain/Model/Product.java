package Domain.Model;


/**
 * Class to represent a Product
 * @author Pedro Campos <1211511@isep.ipp.pt>
 */
public class Product {

    private String name;

    boolean isSoldOut = true;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSoldOut() {
        return isSoldOut;
    }

    public void setSoldOut(boolean soldOut) {
        isSoldOut = soldOut;
    }
}
