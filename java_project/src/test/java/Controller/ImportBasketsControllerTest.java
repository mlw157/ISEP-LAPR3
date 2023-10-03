package Controller;

import Domain.Model.Basket;
import Domain.Model.Client;
import Domain.Model.Entity;
import Domain.Model.Product;
import Domain.Store.EntityStore;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.Test;
import utils.Pair;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ImportBasketsControllerTest {

    @Test
    void importBasketsWhenFileIsNotFoundThenThrowException() {
        ImportBasketsController importBasketsController = new ImportBasketsController();
        assertThrows(FileNotFoundException.class, () -> importBasketsController.importBaskets(""));
    }

    @Test
    void importBasketsFromFile() throws FileNotFoundException {
        ImportEntitiesController importEntitiesController=new ImportEntitiesController();
        importEntitiesController.importEntityInfo("src/test/resources/Entities/normal_entities_test.csv");
        ImportBasketsController importBasketsController = new ImportBasketsController();
        importBasketsController.importBaskets("src/test/resources/Cabazes/Teste.csv");
        EntityStore entityStore = App.getInstance().getOrganization().getEntityStore();
        Entity entity = entityStore.getEntityByName("C1");
        TreeMap<Integer, Basket> basketsMap = ((Client) entity).getBasketsMap();
        Basket basket = basketsMap.get(1);
        ArrayList<Pair<Product, Double>> products = basket.getProducts();
        assertEquals(products.size(), 3);
        assertEquals(products.get(0).first().getName(), "Prod1");
        assertEquals(products.get(0).second(), 0);
        assertEquals(products.get(1).first().getName(), "Prod2");
        assertEquals(products.get(1).second(), 0);
        assertEquals(products.get(2).first().getName(),"Prod3");
        assertEquals(products.get(2).second(),0);
    }

}