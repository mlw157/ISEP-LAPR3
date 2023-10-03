package Controller;

import Domain.Model.*;
import Domain.Store.EntityStore;
import Domain.Store.ExpeditionStore;
import utils.Pair;

import java.util.*;

/**
 * Controller class to generate a list of expedition for a certain day (US309)
 * @author: Pedro Lopes <1211504@isep.ipp.pt>
 */
public class CalculateExpeditionsNoRestrictionsController {

    private EntityStore entityStore;
    private ExpeditionStore expeditionStore;

    private FindNearestHubController findNearestHubController = new FindNearestHubController();

    public GlobalProductStore globalProductStore = new GlobalProductStore();

    public CalculateExpeditionsNoRestrictionsController() {
        this.entityStore = App.getInstance().getOrganization().getEntityStore();
        this.expeditionStore = App.getInstance().getOrganization().getExpeditionStore();
        entityStore.setCompanyList();
        entityStore.setProductorsList();


    }

    /**
     * Method that calculates the expeditions for a given dayS
     * @param day day to calculate the expeditions
     */
    public void calculateExpeditions(int day) {
        expeditionStore.getNoRestrictionsExpeditionMap().clear();
        deliverAllProductsAndOrdersToHubs();
        for (int i = 1; i <= day; i++) {
            Expedition expedition = calculateDayOrders(i);
            expeditionStore.addExpedition(expedition, false);
        }


    }

    /**
     * Method that delivers all the products of a productor
     * @param productor productor to deliver the products
     * @return void
     */
    public void deliverProductsToHub(Productor productor) {

        TreeMap<Integer, List<Pair<Product, List<Pair<Double, Productor>>>>> stockMap = globalProductStore.getStockMap();

        TreeMap<Integer, Basket> basketsMap = productor.getBasketsMap();
        for (Integer integer : basketsMap.keySet()) {
            Basket basket = basketsMap.get(integer);
            for (Pair<Product, Double> pair : basket.getProducts()) {
                Product product = pair.first();
                Double quantity = pair.second();
                if (!stockMap.containsKey(integer)) {
                    stockMap.put(integer, new ArrayList<>());
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
            }
        }
    }

    /**
     * Method that generates the order of a client on the nearest hub
     * @return void (updates ordermap of the hub)
     */
    public void generateHubOrder(Entity entity){

        TreeMap<Integer, List<Pair<Product, List<Pair<Double, Entity>>>>> orderMap = globalProductStore.getOrderMap();

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
            }
        }
    }

    /**
     * Method that delivers all the products and orders for all days
     * @return void (updates hubs stockmap and ordermap)
     */
    public void deliverAllProductsAndOrdersToHubs(){
        List<Productor> productors = entityStore.getProductorsList();

        for (Productor productor : productors) {
            deliverProductsToHub(productor);
        }

        for (Entity entity : entityStore.getEntitiesGraph().vertices()){
            if (entity instanceof Company || entity instanceof Client){
                generateHubOrder(entity);
            }
        }

    }

    /**
     * Method that calculates the expeditions for a given day
     * @return Expedition
     */
    public Expedition calculateDayOrders(int day){
        Expedition expedition = new Expedition(day);

        TreeMap<Integer, List<Pair<Product, List<Pair<Double, Entity>>>>> orderMap = globalProductStore.getOrderMap();
        TreeMap<Integer, List<Pair<Product, List<Pair<Double, Productor>>>>> stockMap = globalProductStore.getStockMap();
        if (orderMap.containsKey(day)) {
            List<Pair<Product, List<Pair<Double, Entity>>>> dayOrders = orderMap.get(day);
            List<Pair<Product, List<Pair<Double, Productor>>>> dayStocks = stockMap.get(day);
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

                } else {
            System.out.println("Invalid Day");
        }
        return expedition;
    }



}
