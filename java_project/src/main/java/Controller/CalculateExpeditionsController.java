package Controller;

import Domain.Model.*;
import Domain.Store.EntityStore;
import Domain.Store.ExpeditionStore;
import utils.Pair;
import utils.graph.Algorithms;
import utils.graph.map.MapGraph;

import java.util.*;
/**
 * Controller class to generate a list of expedition for a certain day (US309)
 * @author: Diogo Gonçalves <1211509@isep.ipp.pt>
 * @author Pedro Campos <1211511@isep.ipp.pt>
 */
public class CalculateExpeditionsController {

    private EntityStore entityStore;
    private ExpeditionStore expeditionStore;

    private FindNearestHubController findNearestHubController = new FindNearestHubController();

    public CalculateExpeditionsController() {
        this.entityStore = App.getInstance().getOrganization().getEntityStore();
        this.expeditionStore = App.getInstance().getOrganization().getExpeditionStore();
        entityStore.setCompanyList();
        entityStore.setProductorsList();
        for (Productor productor : entityStore.getProductorsList()) {
            productor.setOriginalBasketsMap();

        }

    }

    /**
     * Method to calculate the expeditions for a given day
     * @param day day to calculate the expeditions
     * @param numberOfProducers number of producers closests to the hub to consider
     */


    public void calculateExpeditions(int day, int numberOfProducers) {
        expeditionStore.getExpeditionMap().clear();
        deliverAllProductsAndOrdersToHubs(numberOfProducers);
        for (int i = 1; i <= day; i++) {
            Expedition expedition = calculateDayOrders(i);
            expeditionStore.addExpedition(expedition, true);
        }

    }

    /**
     * Method to deliver all the products and generate all the orders for all days
     * @param numberOfProducers number of producers closests to the hub to consider
     */
    public void deliverAllProductsAndOrdersToHubs(int numberOfProducers){
        for (Entity entity : entityStore.getEntitiesGraph().vertices()){
            if (entity instanceof Company || entity instanceof Client){
                generateHubOrder(entity);
            }
        }
        generateHubStocks(numberOfProducers);

    }

    /**
     * Method to deliver the products of a certain productor to a certain hub
     * @param productor  productor to deliver the products
     * @param hub hub to deliver the products
     */
    public void deliverProductsToHub(Productor productor, Company hub) {


        TreeMap<Integer, List<Pair<Product, List<Pair<Double, Productor>>>>> stockMap = hub.getStockMap();
        TreeMap<Integer, List<Pair<Product, Double>>> wantedProductsMap = hub.getWantedProductsMap();
        TreeMap<Integer, Basket> basketsMap = productor.getBasketsMap();
        for (Integer integer : basketsMap.keySet()) {
            Basket basket = basketsMap.get(integer);
            for (Pair<Product, Double> pair : basket.getProducts()) {
                Product product = pair.first();
                Double quantity = pair.second();

                    if (!stockMap.containsKey(integer)) {
                        stockMap.put(integer, new ArrayList<>());
                    }
                    List<Pair<Product, Double>> wantedProducts = wantedProductsMap.get(integer);
                    if (wantedProducts != null) {
                        for (Pair<Product, Double> wantedProduct : wantedProducts) {
                            if (wantedProduct.first().equals(product)) {
                                if (wantedProduct.second() >= quantity) {
                                    wantedProduct.setSecond(wantedProduct.second() - quantity);
                                } else if (wantedProduct.second() == 0) {
                                    quantity = 0.0;
                                } else {
                                    quantity = quantity - wantedProduct.second();
                                    wantedProduct.setSecond(0.0);
                                }
                            }
                        }


                    }

                    List<Pair<Product, List<Pair<Double, Productor>>>> list = stockMap.get(integer);
                    boolean found = false;
                    for (Pair<Product, List<Pair<Double, Productor>>> pair1 : list) {
                        if (pair1.first().equals(product)) {
                            List<Pair<Double, Productor>> list1 = pair1.second();
                            list1.add(new Pair<>(quantity, productor));
                            found = true;
                        }
                    }
                    if (!found) {
                        List<Pair<Double, Productor>> list1 = new ArrayList<>();
                        list1.add(new Pair<>(quantity, productor));
                        list.add(new Pair<>(product, list1));
                    }
                    ArrayList<Pair<Product, Double>> basketList = productor.getBasketsMap().get(integer).getProducts();
                    for (Pair<Product, Double> pair1 : basketList) {
                        if (pair1.first().equals(product)) {
                            pair1.setSecond(pair.second() - quantity);
                        }
                    }
                    HashMap<Company, HashMap<Product, Double>> originalHubStockList = expeditionStore.getOriginalHubStockList();

                    if (!originalHubStockList.containsKey(hub)) {
                        originalHubStockList.put(hub, new HashMap<>());
                    }
                    HashMap<Product, Double> productDoubleHashMap = originalHubStockList.get(hub);
                    if (!productDoubleHashMap.containsKey(product)) {
                        if (quantity != 0) {
                            productDoubleHashMap.put(product, quantity);
                        }
                    } else {
                        if (quantity != 0) {
                            productDoubleHashMap.put(product, productDoubleHashMap.get(product) + quantity);
                        }
                    }


            }
        }
    }

    /**
     * Method to generate the orders for a certain client
     * @param entity client to generate the orders
     */

        public void generateHubOrder(Entity entity){
            Pair<Company, Integer> nearestHub = findNearestHubController.getNearestDistributionHub(entity);

            Company hub = nearestHub.first();
            TreeMap<Integer, List<Pair<Product, List<Pair<Double, Entity>>>>> orderMap = hub.getOrderMap();
            TreeMap<Integer, List<Pair<Product, Double>>> wantedProductsMap = hub.getWantedProductsMap();


            TreeMap<Integer, Basket> basketsMap = entity.getBasketsMap();
            for (Integer integer : basketsMap.keySet()) {
                Basket basket = basketsMap.get(integer);
                for (Pair<Product, Double> pair : basket.getProducts()) {
                    Product product = pair.first();
                    Double quantity = pair.second();
                    if (!orderMap.containsKey(integer)) {
                        orderMap.put(integer, new ArrayList<>());
                    }
                    List<Pair<Product, List<Pair<Double, Entity>>>> list = orderMap.get(integer);
                    boolean found = false;
                    for (Pair<Product, List<Pair<Double, Entity>>> pair1 : list) {
                        if (pair1.first().equals(product)) {
                            List<Pair<Double, Entity>> list1 = pair1.second();
                            list1.add(new Pair<>(quantity, entity));
                            found = true;
                        }
                    }
                    if (!found) {
                        List<Pair<Double, Entity>> list1 = new ArrayList<>();
                        list1.add(new Pair<>(quantity, entity));
                        list.add(new Pair<>(product, list1));

                    }
                    if (!wantedProductsMap.containsKey(integer)) {
                        wantedProductsMap.put(integer, new ArrayList<>());
                    }
                    List<Pair<Product, Double>> list1 = wantedProductsMap.get(integer);
                    boolean found1 = false;
                    for (Pair<Product, Double> pair1 : list1) {
                        if (pair1.first().equals(product)) {
                            pair1.setSecond(pair1.second() + quantity);
                            found1 = true;
                        }
                    }
                    if (!found1) {
                        list1.add(new Pair<>(product, quantity));
                    }


                }
            }
        }

    /**
     * Method to calculate the expedition for a certain day
     * @param day to calculate the expedition
     * @return the expedition for a certain day
     */
        public Expedition calculateDayOrders(int day){
            Expedition expedition = new Expedition(day);
            List<Company> companies = entityStore.getCompanyList();

            for (Company company1 : companies){
                if (company1.isDistributionHub()){
                    TreeMap<Integer, List<Pair<Product, List<Pair<Double, Entity>>>>> orderMap = company1.getOrderMap();
                    TreeMap<Integer, List<Pair<Product, List<Pair<Double, Productor>>>>> stockMap = company1.getStockMap();
                    //System.out.println("Hub: " + company1.getName());
                }
            }

            for (Company company : companies) {
                if (company.isDistributionHub()){
                    TreeMap<Integer, List<Pair<Product, List<Pair<Double, Entity>>>>> orderMap = company.getOrderMap();
                    TreeMap<Integer, List<Pair<Product, List<Pair<Double, Productor>>>>> stockMap = company.getStockMap();
                    if (company.getStockMap().size() != 0){
                        if (orderMap.containsKey(day)) {
                            List<Pair<Product, List<Pair<Double, Entity>>>> dayOrders = orderMap.get(day);
                            List<Pair<Product, List<Pair<Double, Productor>>>> dayStocks = stockMap.get(day);
                            // add stock of previous 2 days
                            if (day > 2){
                                List<Pair<Product, List<Pair<Double, Productor>>>> dayStocksDayBeforeYesterday = stockMap.get(day - 2);
                                List<Pair<Product, List<Pair<Double, Productor>>>> dayStocksYesterday = stockMap.get(day - 1);
                                for (int y = 0; y < dayStocks.size(); y++) {
                                    List<Pair<Double, Productor>> additionProducts1 = dayStocksYesterday.get(y).second();
                                    List<Pair<Double, Productor>> additionProducts2 = dayStocksDayBeforeYesterday.get(y).second();
                                    List<Pair<Double, Productor>> addedProducts = dayStocks.get(y).second();
                                    for (int z = 0; z < addedProducts.size(); z++) {
                                        Double sum = additionProducts1.get(z).first() + additionProducts2.get(z).first() + addedProducts.get(z).first();
                                        addedProducts.get(z).setFirst(sum);
                                    }
                                }
                            }

                            for (Pair<Product, List<Pair<Double, Entity>>> orders: dayOrders){
                                List<Pair<Double, Productor>> products = new ArrayList<>();
                                Product product = orders.first();
                                for (int j = 0; j < dayStocks.size(); j++) {
                                    if (dayStocks.get(j).first().equals(product)) {
                                        products = dayStocks.get(j).second();
                                    }
                                }
                                Collections.sort(products, Comparator.comparing(p -> -p.first()));
                                List<Pair<Double, Entity>> productOrders = orders.second();
                                for (int i = 0; i < productOrders.size(); i++) {
                                    Pair<Double, Entity> order = productOrders.get(i);
                                    double quantity = order.first();
                                    Entity entity = order.second();
                                    Delivery delivery = new Delivery(entity, day);
                                    double quantitySupplied = products.get(0).first();

                                    Productor productor = products.get(0).second();
                                    if (quantitySupplied > quantity) {
                                        products.get(0).setFirst(quantitySupplied - quantity);
                                        quantitySupplied = quantity;
                                    }
                                    else if (quantitySupplied <= quantity) {
                                        products.get(0).setFirst(0.0);
                                        quantity = quantitySupplied;
                                    }
                                    if (quantitySupplied != 0) {
                                        delivery.addProduct(product, productor, quantity);
                                        expedition.addDelivery(delivery, entity);
                                    }
                                    Collections.sort(products, Comparator.comparing(p -> -p.first()));

                                }
                            }
                        }
                    }

                }
            }
            return expedition;
        }


    /**
     * Method to generate all the hub stocks
     * @param numberOfProducers the number of producers close to the hub to consider
     */
    public void generateHubStocks(int numberOfProducers){
        List<Company> companies = entityStore.getCompanyList();
        for (Company company : companies) {
            if (company.isDistributionHub()){
                List<Pair<Productor, Integer>> producers = getProducers(company, numberOfProducers);
                for (Pair<Productor,Integer> productor : producers) {
                        deliverProductsToHub(productor.first(), company);
                    }

                }


            }
        }


    /**
     * Method to get the reachable producers to a hub
     * @param entity the hub
     * @return the reachable producers to a hub with their respective distances
     */
    public List<Pair<Productor, Integer>> getHubReachableProducers(Entity entity) {
        List<Pair<Productor, Integer>> reachableProducers = new ArrayList<>();
        MapGraph<Entity, Integer> entityGraph = entityStore.getEntitiesGraph();
        ArrayList<LinkedList<Entity>> paths = new ArrayList<>();
        ArrayList<Integer> pathsDistances = new ArrayList<>();

        Algorithms.shortestPaths(entityGraph, entity, Integer::compareTo, Integer::sum,0, paths, pathsDistances);
        for (int i = 0; i < paths.size(); i++) {
            if (paths.get(i).getLast() instanceof Productor) {
                reachableProducers.add(new Pair<>((Productor) paths.get(i).getLast(), pathsDistances.get(i)));
            }
        }

        return reachableProducers;
    }

    /**
     * Method to get the closest producers to a hub
     * @param entity the hub
     * @param numberOfProducers the number of producers to consider
     * @return the closest producers to a hub with their respective distances
     */
    public List<Pair<Productor, Integer>> getProducers(Entity entity, int numberOfProducers) {
        List<Pair<Productor, Integer>> reachableProducers = getHubReachableProducers(entity);
        Collections.sort(reachableProducers, Comparator.comparing(p -> p.second()));

        List<Pair<Productor, Integer>> producers = new ArrayList<>();
        for (int i = 0; i < numberOfProducers; i++) {
            producers.add(new Pair<>(reachableProducers.get(i).first(), reachableProducers.get(i).second()));
        }

        return producers;
    }


}
