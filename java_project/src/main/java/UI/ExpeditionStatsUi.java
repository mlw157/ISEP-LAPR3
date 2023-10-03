package UI;

import Controller.App;
import Controller.CalculateExpeditionsController;
import Controller.ExpeditionStatsController;
import Domain.Model.Delivery;
import Domain.Model.Entity;
import Domain.Model.Expedition;
import Domain.Store.EntityStore;
import Domain.Store.ExpeditionStore;
import utils.Pair;
import utils.graph.map.MapGraph;

import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Ui class to generate expedition stats (US311)
 * @author Pedro Campos <1211511@isep.ipp.pt>
 **/

public class ExpeditionStatsUi implements Runnable {

    Expedition expedition;
    ExpeditionStore expeditionStore;

    ExpeditionStatsController expeditionStatsController;
    CalculateExpeditionsController calculateExpeditionsController;

    EntityStore entityStore;

    Scanner sc = new Scanner(System.in);

    public ExpeditionStatsUi() {
        expeditionStatsController = new ExpeditionStatsController();
        calculateExpeditionsController = new CalculateExpeditionsController();
        this.expeditionStore = App.getInstance().getOrganization().getExpeditionStore();
        this.entityStore = App.getInstance().getOrganization().getEntityStore();

    }

    @Override
    public void run() {
        expeditionStore.getExpeditionMap().clear();

        MapGraph<Entity, Integer> graph = entityStore.getEntitiesGraph();
        int dayLimit = graph.vertices().get(0).getBasketsMap().size();
        System.out.println("Expedition Stats");
        System.out.println();
        System.out.println("Choose the amount of producers nearest to the hub to consider");
        int numberOfHubs = sc.nextInt();
        calculateExpeditionsController.calculateExpeditions(dayLimit + 1, numberOfHubs);


        TreeMap<Integer, Expedition> expeditionMap = expeditionStore.getExpeditionMap();

        int option;
        do {
            System.out.println("1. View basket stats");
            System.out.println("2. View client stats");
            System.out.println("3. View producer stats");
            System.out.println("4. View hub stats");
            System.out.println("0. Exit");
            do {
                option = sc.nextInt();
            } while (option < 0 || option > 4);
            switch (option) {
                case 1:
                    System.out.println("Choose a day to show the basket stats");
                    int day = sc.nextInt();
                    expedition = expeditionMap.get(day);
                    if (expedition != null) {
                        System.out.println("------------------------");
                        System.out.println("Day: " + day);
                        System.out.println("------------------------");
                        expeditionStatsController.basketDayStats(expedition, day);
                        System.out.println();
                    } else {
                        System.out.println("No expedition for this day");
                    }



                    break;
                case 2:
                    expeditionStatsController.clientStats(expeditionMap);

                    break;
                case 3:
                    expeditionStatsController.productorStats(expeditionMap);
                    break;
                case 4:
                    expeditionStatsController.hubStats(expeditionMap);

                    break;

            }
        } while (option != 0);
    }
}



