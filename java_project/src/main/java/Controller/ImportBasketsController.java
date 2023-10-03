package Controller;

import Domain.Model.*;
import Domain.Store.EntityStore;
import utils.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controller class for importing baskets from files (US307)
 * @author: Joao Costa <1211546@isep.ipp.pt>
 */

public class ImportBasketsController {

    private EntityStore entityStore;
    public ImportBasketsController() {
        this.entityStore = App.getInstance().getOrganization().getEntityStore();
    }

    /**
     * Imports the baskets from a file to entityStore
     * @param file the file to import from
     * @throws FileNotFoundException if the file is not found
     */
    public void importBaskets(String file) throws FileNotFoundException {
        ArrayList<Product> productList = new ArrayList<>(); // list containing all the products
        Scanner sc = new Scanner(new File(file));
        String firstLine = sc.nextLine();
        String[] elements = firstLine.split(",");
        for (int i = 2; i < elements.length; i++) {
            String productName = elements[i];
            productName = productName.replace("\"", "");
            productList.add(new Product(productName));
        }
        int lineNumber = 1;
        while (sc.hasNextLine()) {
            try {
                String line = sc.nextLine();
                elements = line.split(",");
                String entityName = elements[0].replace("\"", "");
                Entity entity = entityStore.getEntityByName(entityName);
                if (entity == null) {
                    continue;
                }
                ArrayList<Pair<Product, Double>> products = new ArrayList<>();
                for (int i = 2; i < elements.length; i++) {
                    products.add(new Pair<>(productList.get(i-2), Double.parseDouble(elements[i])));
                }
                Basket basket = new Basket(products);
                char entityType = entityName.charAt(0); // get the first character of the string to distinguish between the different types of entities
                switch (entityType){
                    case 'C': // if the entity is a client
                        ((Client) entity).getBasketsMap().put(Integer.parseInt(elements[1]), basket);
                        break;
                    case 'E': // if the entity is a company
                        ((Company) entity).getBasketsMap().put(Integer.parseInt(elements[1]), basket);
                        break;
                    case 'P': // if the entity is a productor
                        ((Productor) entity).getBasketsMap().put(Integer.parseInt(elements[1]), basket);
                        break;

                }
            } catch (Exception e) {
                System.out.printf("Error in line: %d %s ", lineNumber, e.getCause());
            }
            lineNumber++;
        }

    }
}



