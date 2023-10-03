package UI;

import Controller.App;
import Controller.CalculateExpeditionsController;
import Domain.Model.*;
import Domain.Store.EntityStore;
import Domain.Store.ExpeditionStore;
import utils.Pair;

import java.util.*;

/**
 * Ui class to generate an expedition list for a given day
 * @author Diogo Gonçalves <1211509@isep.ipp.pt>
 */

public class GenerateExpeditionsWithRestrictionsUi implements Runnable {

    CalculateExpeditionsController calculateExpeditionsController;

    Scanner sc = new Scanner(System.in);

    private EntityStore entityStore;
    private ExpeditionStore expeditionStore;


    public GenerateExpeditionsWithRestrictionsUi() {
        calculateExpeditionsController = new CalculateExpeditionsController();
        entityStore = App.getInstance().getOrganization().getEntityStore();
        expeditionStore = App.getInstance().getOrganization().getExpeditionStore();

    }

    @Override
    public void run() {
        System.out.println();

        System.out.println("Choose a day to show the expedition list");
        int day = sc.nextInt();
        System.out.println("Choose the amount of producers nearest to the hub to consider");
        int numberOfProducers = sc.nextInt();

        calculateExpeditionsController.calculateExpeditions(day, numberOfProducers);

        Expedition expedition = expeditionStore.getExpeditionMap().get(day);

        if (expedition != null) {
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
            int option;
            do {
                System.out.println("Main Menu");
                System.out.println("1. See pickup route");
                System.out.println("0. Exit");
                do {
                    option = sc.nextInt();
                } while (option < 0 || option > 1);
                switch (option) {
                    case 1:
                        new MinimumDistanceRouteUi(expedition).run();
                        break;
                }
            } while (option != 0);

        } else {
            System.out.println("No expeditions for this day");
        }
    }
}
