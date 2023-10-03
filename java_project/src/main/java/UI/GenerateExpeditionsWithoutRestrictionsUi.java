package UI;

import Controller.App;
import Controller.CalculateExpeditionsController;
import Controller.CalculateExpeditionsNoRestrictionsController;
import Domain.Model.*;
import Domain.Store.EntityStore;
import Domain.Store.ExpeditionStore;
import utils.Pair;

import java.util.*;

/**
 * Ui class to generate a expedition list for a given day
 * @author Pedro Campos <1211511@isep.ipp.pt>
 * @author Pedro Lopes <1211504@isep.ipp.pt>
 */

public class GenerateExpeditionsWithoutRestrictionsUi implements Runnable {

    CalculateExpeditionsController calculateExpeditionsController;

    CalculateExpeditionsNoRestrictionsController calculateExpeditionsNoRestrictionsController;
    Scanner sc = new Scanner(System.in);

    private EntityStore entityStore;
    private ExpeditionStore expeditionStore;


    public GenerateExpeditionsWithoutRestrictionsUi() {
        calculateExpeditionsController = new CalculateExpeditionsController();
        calculateExpeditionsNoRestrictionsController = new CalculateExpeditionsNoRestrictionsController();
        entityStore = App.getInstance().getOrganization().getEntityStore();
        expeditionStore = App.getInstance().getOrganization().getExpeditionStore();

    }

    @Override
    public void run() {
        System.out.println();


        System.out.println("Choose a day to show the expedition list");
        int day = sc.nextInt();
        calculateExpeditionsNoRestrictionsController.calculateExpeditions(day);
        Expedition expedition = expeditionStore.getNoRestrictionsExpeditionMap().get(day);

        if (expedition != null){
            System.out.println("------------------------");
            System.out.println("Day: " + day);
            System.out.println("------------------------");
            List<Pair<Entity, List<Delivery>>> dayDeliveries = expedition.getDayDeliveries();
            System.out.println();
            for (Pair<Entity,List<Delivery>> clientDeliveries : dayDeliveries) {
                System.out.println("Client:" + clientDeliveries.first().getName());
                System.out.println();
                System.out.println("Deliveries:");
                for (Delivery delivery : clientDeliveries.second()) {
                    ArrayList<Pair<Product, Pair<Productor, Double>>> products = delivery.getProductProductorAndQuantityDeliveredList();
                    for (Pair<Product, Pair<Productor, Double>> productProductorAndQuantityDelivered : products) {
                        double askedProduct = 0;
                        ArrayList<Pair<Product, Double>> list = clientDeliveries.first().getBasketsMap().get(day).getProducts();
                        for (Pair<Product, Double> productDoublePair : list) {
                            if (productDoublePair.first().getName().equals(productProductorAndQuantityDelivered.first().getName())) {
                                askedProduct = productDoublePair.second();
                            }
                        }
                        System.out.println("Productor " + productProductorAndQuantityDelivered.second().first() + " delivered product " + productProductorAndQuantityDelivered.first().getName() + " in the quantity of " + productProductorAndQuantityDelivered.second().second() + " of the asked " + askedProduct);
                    }
                }
                System.out.println();
            }

        } else {
            System.out.println("No expeditions for this day");
        }
        }
}
