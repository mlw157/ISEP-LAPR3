package Controller;

import Domain.Model.*;
import Domain.Store.EntityStore;
import Domain.Store.ExpeditionStore;
import utils.Pair;
import utils.graph.Algorithms;

import java.util.*;
/**
 * Controller class to obtain minimum distance route for a given expedition daily list (US310)
 * @author Diogo Costa <1211514@isep.ipp.pt>
 */
public class MinimumDistanceRouteController {

    private EntityStore entityStore;
    private ExpeditionStore expeditionStore;


    private CalculateExpeditionsController calculateExpeditionsController;

    public MinimumDistanceRouteController() {
        this.entityStore = App.getInstance().getOrganization().getEntityStore();
        this.expeditionStore = App.getInstance().getOrganization().getExpeditionStore();
        this.calculateExpeditionsController = new CalculateExpeditionsController();


    }


    /**
     * Calculates the minimum distance route for a given expedition
     * @param expedition expedition to calculate the minimum distance route
     * @return prints the minimum distance route
     */
    public void calculateMinimumDistanceRoute(Expedition expedition) {
        HashMap<Company, HashMap<Product, Double>> originalHubStockList = expeditionStore.getOriginalHubStockList();
        Pair<List<Integer>, Pair<List<LinkedList<Entity>>, Integer>> productorsRoute = calculateProductorsMinimumRoute();
        List<Integer> productorsRouteDistances = productorsRoute.first();
        List<LinkedList<Entity>> productorsRouteList = productorsRoute.second().first();
        int productorsRouteDistance = productorsRoute.second().second();
        Productor lastProductor = (Productor) productorsRouteList.get(productorsRouteList.size() - 1).getLast();
        Pair<List<Integer>, Pair<List<LinkedList<Entity>>, Integer>> hubRoute = calculateHubsMinimumRoute(lastProductor, expedition);
        List<Integer> hubRouteDistances = hubRoute.first();
        List<LinkedList<Entity>> hubRouteList = hubRoute.second().first();
        int hubRouteDistance = hubRoute.second().second();
        int totalDistance = productorsRouteDistance + hubRouteDistance;

        System.out.println("Total distance: " + totalDistance + " meters");
        System.out.println();
        System.out.println("Products Pickup: ");
        System.out.println();
        int amountOfProducers = productorsRouteDistances.size() + 1;

        for (int i = 0; i < productorsRouteList.size(); i++) {
            System.out.println(i+1 + " - " + productorsRouteList.get(i).getFirst() + "--->" + productorsRouteList.get(i).getLast() + " - distance: " + productorsRouteDistances.get(i) + " meters");
        }
        System.out.println();
        System.out.println("Hub Drop off: ");
        for (int i = 0; i < hubRouteList.size(); i++) {
            Company hub = (Company) hubRouteList.get(i).getLast();
            System.out.println(i+amountOfProducers + " - " + hubRouteList.get(i).getFirst() + "--->" + hubRouteList.get(i).getLast() + " - distance: " + hubRouteDistances.get(i) + " meters");
            System.out.println();
            System.out.println("Delivered Products: ");
            System.out.println();
            for (Product product : originalHubStockList.get(hub).keySet()) {
                System.out.println(product.getName() + " - " + originalHubStockList.get(hub).get(product) + " units");
            }

            System.out.println();


        }




    }

    /**
     * Calculates the minimum distance route for the hubs
     * @param startPoint last productor in the productor route
     * @param expedition expedition to calculate the minimum distance route
     * @return returns the minimum distance route for the hubs
     */
    public Pair<List<Integer>, Pair<List<LinkedList<Entity>>, Integer>> calculateHubsMinimumRoute(Productor startPoint, Expedition expedition) {
        HashMap<Company, HashMap<Product, Double>> originalHubStockList = expeditionStore.getOriginalHubStockList();
        List<Company> companyList = entityStore.getCompanyList();
        List<Company> hubList = new ArrayList<>();
        for (Company company : companyList) {
            if (company.isDistributionHub()) {
                boolean hasStock = false;
                for (Product product : originalHubStockList.get(company).keySet()) {
                    if (originalHubStockList.get(company).get(product) > 0) {
                        hasStock = true;
                    }
                }
                if (hasStock) {
                    hubList.add(company);
                }
            }
        }
        int minDistance = Integer.MAX_VALUE;
        LinkedList<Entity> minPath = new LinkedList<>();
        for (int i = 0; i < hubList.size(); i++) {
            LinkedList<Entity> path = new LinkedList<>();
            int distance = Algorithms.shortestPath(entityStore.getEntitiesGraph(), startPoint, hubList.get(i), Integer::compareTo, Integer::sum, 0, path);
            if (distance < minDistance){
                minDistance = distance;
                minPath = path;
            }
        }
        Company firstHub = (Company) minPath.getLast();
        List<LinkedList<Entity>> hubRoutes = new ArrayList<>();
        List<Integer> distances = new ArrayList<>();
        distances.add(minDistance);
        hubRoutes.add(minPath);
        int totalDistance = 0;
        List<Company> hubListCopy = new ArrayList<>(hubList);
        hubListCopy.remove(firstHub);
        Pair<Company, Pair<LinkedList<Entity>, Integer>> hubRoute = calculateNextHub(firstHub, hubListCopy);
        hubRoutes.add(hubRoute.second().first());
        totalDistance += hubRoute.second().second();
        distances.add(hubRoute.second().second());
        Company nextHub = hubRoute.first();
        hubListCopy.remove(nextHub);
        do {
            hubRoute = calculateNextHub(nextHub, hubListCopy);
            distances.add(hubRoute.second().second());
            hubRoutes.add(hubRoute.second().first());
            totalDistance += hubRoute.second().second();
            nextHub = hubRoute.first();
            hubListCopy.remove(nextHub);


        } while (hubListCopy.size() > 0);

        return new Pair<>(distances, new Pair<>(hubRoutes, totalDistance));


    }

    /**
     * Calculates the next hub in the minimum distance route
     * @param hub hub to calculate the next hub
     * @param hubs list of hubs to calculate the next hub
     * @return returns the next hub in the minimum distance route
     */
    public Pair<Company, Pair<LinkedList<Entity>, Integer>> calculateNextHub(Company hub, List<Company> hubs){
        int minDistance = Integer.MAX_VALUE;
        LinkedList<Entity> minPath = new LinkedList<>();
        for (int i = 0; i < hubs.size(); i++) {
            LinkedList<Entity> path = new LinkedList<>();
            int distance = Algorithms.shortestPath(entityStore.getEntitiesGraph(), hub, hubs.get(i), Integer::compareTo, Integer::sum, 0, path);
            if (distance < minDistance){
                minDistance = distance;
                minPath = path;
            }
        }
        Company returnHub = (Company) minPath.getLast();
        return new Pair<>(returnHub, new Pair<>(minPath, minDistance));

    }

    /**
     * Calculates the minimum distance route for the productors
     * @return returns the minimum distance route for the productors
     */
    public Pair<List<Integer>, Pair<List<LinkedList<Entity>>, Integer>> calculateProductorsMinimumRoute(){
        List<LinkedList<Entity>> productorsRoutes = new ArrayList<>();
        List<Integer> distances = new ArrayList<>();
        int totalDistance = 0;
        List<Productor> productors = entityStore.getProductorsList();
        List<Productor> productorsCopy = new ArrayList<>(productors);
        Productor firstProductor = productors.get(0);
        productorsCopy.remove(firstProductor);
        Pair<Productor, Pair<LinkedList<Entity>, Integer>> productorRoute = calculateNextProductor(firstProductor, productorsCopy);
        productorsRoutes.add(productorRoute.second().first());
        totalDistance += productorRoute.second().second();
        distances.add(productorRoute.second().second());
        Productor nextProductor = productorRoute.first();
        productorsCopy.remove(nextProductor);
        do {
           productorRoute = calculateNextProductor(nextProductor, productorsCopy);
           distances.add(productorRoute.second().second());
           productorsRoutes.add(productorRoute.second().first());
           totalDistance += productorRoute.second().second();
           nextProductor = productorRoute.first();
           productorsCopy.remove(nextProductor);


        } while (productorsCopy.size() > 0);

        return new Pair<>(distances, new Pair<>(productorsRoutes, totalDistance));
    }

    /**
     * Calculates the next productor in the minimum distance route
     * @param productor productor to calculate the next productor
     * @param productors list of productors to calculate the next productor
     * @return returns the next productor in the minimum distance route
     */
    public Pair<Productor, Pair<LinkedList<Entity>, Integer>> calculateNextProductor(Productor productor, List<Productor> productors){
        int minDistance = Integer.MAX_VALUE;
        LinkedList<Entity> minPath = new LinkedList<>();
        for (int i = 0; i < productors.size(); i++) {
            LinkedList<Entity> path = new LinkedList<>();
            int distance = Algorithms.shortestPath(entityStore.getEntitiesGraph(), productor, productors.get(i), Integer::compareTo, Integer::sum, 0, path);
            if (distance < minDistance){
                minDistance = distance;
                minPath = path;
            }
        }
        Productor returnProductor = (Productor) minPath.getLast();
        return new Pair<>(returnProductor, new Pair<>(minPath, minDistance));

    }
}
