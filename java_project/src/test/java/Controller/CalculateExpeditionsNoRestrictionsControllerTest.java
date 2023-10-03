package Controller;

import Domain.Model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Pair;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculateExpeditionsNoRestrictionsControllerTest {

    @Test
    void calculateDayOrdersWhenThereAreNoOrdersThenReturnExpeditionWithNoDeliveries() {
        CalculateExpeditionsNoRestrictionsController calculateExpeditionsNoRestrictionsController = new CalculateExpeditionsNoRestrictionsController();
        calculateExpeditionsNoRestrictionsController.globalProductStore.getOrderMap().clear();
        calculateExpeditionsNoRestrictionsController.globalProductStore.getStockMap().clear();
        int day = 1;
        Expedition expedition = calculateExpeditionsNoRestrictionsController.calculateDayOrders(day);
        assertEquals(0, expedition.getDayDeliveries().size());
    }


    @Test
    void deliverProductsToHubWhenProductIsNotInStockMapThenAddProductToStockMap() throws FileNotFoundException {
        ImportEntitiesController importEntitiesController = new ImportEntitiesController();
        importEntitiesController.importEntityInfo("src/test/resources/Entities/normal_entities_test.csv");
        ImportBasketsController importBasketsController = new ImportBasketsController();
        importBasketsController.importBaskets("src/test/resources/Cabazes/Teste.csv");
        CalculateExpeditionsNoRestrictionsController calculateExpeditionsNoRestrictionsController = new CalculateExpeditionsNoRestrictionsController();
        Productor productor = new Productor(new Location("1", 0, 0), "Productor");
        ArrayList<Pair<Product, Double>> products = new ArrayList<>();
        products.add(0, new Pair<>(new Product("Product"), Double.parseDouble("10")));
        Basket basket = new Basket(products);
        TreeMap<Integer, Basket> basketsMap = new TreeMap<>();
        basketsMap.put(1, basket);
        productor.setBasketsMap(basketsMap);
        calculateExpeditionsNoRestrictionsController.deliverProductsToHub(productor);
        assertEquals(1, calculateExpeditionsNoRestrictionsController.globalProductStore.getStockMap().size());
    }

    @Test
    void deliverProductsToHubWhenProductIsInStockMapThenAddQuantityAndProductorToListOfProducts() throws FileNotFoundException {
        CalculateExpeditionsNoRestrictionsController controller = new CalculateExpeditionsNoRestrictionsController();
        Productor productor = new Productor(new Location("1", 0, 0), "Productor");
        ArrayList<Pair<Product, Double>> products = new ArrayList<>();
        products.add(0, new Pair<>(new Product("Product"), Double.parseDouble("10")));
        Basket basket = new Basket(products);
        productor.getBasketsMap().put(1, basket);
        controller.deliverProductsToHub(productor);
        assertEquals(10.0, controller.globalProductStore.getStockMap().get(1).get(0).second().get(0).first());
    }

    @Test
    void calculateExpeditionsShouldCalculateDayOrders() {
        CalculateExpeditionsNoRestrictionsController calculateExpeditionsNoRestrictionsController = new CalculateExpeditionsNoRestrictionsController();
        calculateExpeditionsNoRestrictionsController.calculateExpeditions(1);
        assertEquals(1, App.getInstance().getOrganization().getExpeditionStore().getNoRestrictionsExpeditionMap().size());
    }

    @Test
    void calculateExpeditionsShouldDeliverAllProductsAndOrdersToHubs() throws FileNotFoundException {
        ImportEntitiesController importEntitiesController = new ImportEntitiesController();
        importEntitiesController.importEntityInfo("src/test/resources/Entities/normal_entities_test.csv");
        ImportBasketsController importBasketsController = new ImportBasketsController();
        importBasketsController.importBaskets("src/test/resources/Cabazes/Teste.csv");
        CalculateExpeditionsNoRestrictionsController calculateExpeditionsNoRestrictionsController = new CalculateExpeditionsNoRestrictionsController();
        calculateExpeditionsNoRestrictionsController.deliverAllProductsAndOrdersToHubs();
        assertEquals(calculateExpeditionsNoRestrictionsController.globalProductStore.getOrderMap().size(), 2);
        assertEquals(calculateExpeditionsNoRestrictionsController.globalProductStore.getStockMap().size(), 2);
    }
}