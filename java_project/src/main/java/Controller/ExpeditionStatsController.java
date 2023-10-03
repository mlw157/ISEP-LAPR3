package Controller;

import Domain.Model.*;
import Domain.Store.EntityStore;
import Domain.Store.ExpeditionStore;
import com.sun.source.tree.Tree;
import utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Controller class to generate expedition stats (US311)
 * @author Pedro Campos <1211511@isep.ipp.pt>
 **/
public class ExpeditionStatsController {

    private EntityStore entityStore;
    private ExpeditionStore expeditionStore;

    public ExpeditionStatsController() {
        this.entityStore = App.getInstance().getOrganization().getEntityStore();
        this.expeditionStore = App.getInstance().getOrganization().getExpeditionStore();
        entityStore.setProductorsList();
        entityStore.setCompanyList();
    }

    /**
     * prints stats for baskets for a given day
     * @return void
     * @param expedition
     * @param day
     */
    public void basketDayStats(Expedition expedition, int day) {

        List<Pair<Entity, List<Delivery>>> dayDeliveries = expedition.getDayDeliveries();
        System.out.println();
        for (Pair<Entity,List<Delivery>> clientDeliveries : dayDeliveries) {
            double totalProducts = 0;
            double totalProductsSupplied = 0;
            int satisfiedProducts = 0;
            int partiallySatisfiedProducts = 0;
            int unsatisfiedProducts = 0;
            double percentageSatisfiedProducts = 0;
            List<Productor> productors = new ArrayList<>();
            System.out.println("Client:" + clientDeliveries.first().getName());
            System.out.println();
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

                    if (askedProduct == productProductorAndQuantityDelivered.second().second()) {
                        satisfiedProducts++;
                    } else  {
                        partiallySatisfiedProducts++;
                    }

                    if (!productors.contains(productProductorAndQuantityDelivered.second().first())) {
                        productors.add(productProductorAndQuantityDelivered.second().first());
                    }
                    totalProductsSupplied = totalProductsSupplied + productProductorAndQuantityDelivered.second().second();
                    //System.out.println("Productor " + productProductorAndQuantityDelivered.second().first() + " delivered product " + productProductorAndQuantityDelivered.first().getName() + " in the quantity of " + productProductorAndQuantityDelivered.second().second() + " of the asked " + askedProduct);
                }




            }
            ArrayList<Pair<Product, Double>> list1 = clientDeliveries.first().getBasketsMap().get(day).getProducts();
            for (Pair<Product, Double> productDoublePair : list1) {
                totalProducts = totalProducts + productDoublePair.second();
                if (productDoublePair.second() == 0) {
                    satisfiedProducts++;
                }
            }
            unsatisfiedProducts = list1.size() - (satisfiedProducts + partiallySatisfiedProducts);
            percentageSatisfiedProducts = totalProductsSupplied / totalProducts * 100;
            percentageSatisfiedProducts = (double)Math.round(percentageSatisfiedProducts * 100d) / 100d; // round
            System.out.println("Amount of producers that contributed to this basket: " + productors.size());
            System.out.println("Amount of satisfied products: " + satisfiedProducts);
            System.out.println("Amount of partially satisfied products: " + partiallySatisfiedProducts);
            System.out.println("Amount of unsatisfied products: " + unsatisfiedProducts);
            System.out.println("Percentage of satisfied products: " + percentageSatisfiedProducts + "% (" + totalProductsSupplied + "/" + totalProducts + ")");
            System.out.println();
            System.out.println();

            productors.clear();
            System.out.println();

        }
    }

    /**
     * prints stats for every client for all days
     * @return void
     * @param expeditions Tree map of all the expeditions
     */
    public void clientStats(TreeMap<Integer, Expedition> expeditions) {
        HashMap<Entity, Pair<Pair<Integer, Integer>, List<Productor>>> clients = new HashMap<>(); // cliente, cabazes satisfeitos, cabazes parciais, lista de fornecedores
        for (Expedition expedition : expeditions.values()) {
            List<Pair<Entity, List<Delivery>>> dayDeliveries = expedition.getDayDeliveries();
            //System.out.println();
            for (Pair<Entity,List<Delivery>> clientDeliveries : dayDeliveries) {
                if (!clients.containsKey(clientDeliveries.first())) {
                    clients.put(clientDeliveries.first(), new Pair<>(new Pair<>(0,0), new ArrayList<>()));
                }
                double totalProducts = 0;
                double totalProductsSupplied = 0;
                int satisfiedProducts = 0;
                int partiallySatisfiedProducts = 0;
                int unsatisfiedProducts = 0;
                double percentageSatisfiedProducts = 0;
                List<Productor> productors = new ArrayList<>();
                //System.out.println("Client:" + clientDeliveries.first().getName());
                //System.out.println();
                for (Delivery delivery : clientDeliveries.second()) {
                    ArrayList<Pair<Product, Pair<Productor, Double>>> products = delivery.getProductProductorAndQuantityDeliveredList();
                    for (Pair<Product, Pair<Productor, Double>> productProductorAndQuantityDelivered : products) {
                        double askedProduct = 0;
                        ArrayList<Pair<Product, Double>> list = clientDeliveries.first().getBasketsMap().get(expedition.getDayOffset()).getProducts();
                        for (Pair<Product, Double> productDoublePair : list) {
                            if (productDoublePair.first().getName().equals(productProductorAndQuantityDelivered.first().getName())) {
                                askedProduct = productDoublePair.second();
                            }
                        }

                        if (askedProduct == productProductorAndQuantityDelivered.second().second()) {
                            satisfiedProducts++;
                        } else  {
                            partiallySatisfiedProducts++;
                        }

                        if (!clients.get(clientDeliveries.first()).second().contains(productProductorAndQuantityDelivered.second().first())) {
                            clients.get(clientDeliveries.first()).second().add(productProductorAndQuantityDelivered.second().first());
                        }
                        totalProductsSupplied = totalProductsSupplied + productProductorAndQuantityDelivered.second().second();
                        //System.out.println("Productor " + productProductorAndQuantityDelivered.second().first() + " delivered product " + productProductorAndQuantityDelivered.first().getName() + " in the quantity of " + productProductorAndQuantityDelivered.second().second() + " of the asked " + askedProduct);
                    }




                }
                ArrayList<Pair<Product, Double>> list1 = clientDeliveries.first().getBasketsMap().get(expedition.getDayOffset()).getProducts();
                for (Pair<Product, Double> productDoublePair : list1) {
                    totalProducts = totalProducts + productDoublePair.second();
                    if (productDoublePair.second() == 0) {
                        satisfiedProducts++;
                    }
                }
                unsatisfiedProducts = list1.size() - (satisfiedProducts + partiallySatisfiedProducts);
                percentageSatisfiedProducts = totalProductsSupplied / totalProducts * 100;

                if (percentageSatisfiedProducts == 100) {
                    clients.get(clientDeliveries.first()).first().setFirst(clients.get(clientDeliveries.first()).first().first() + 1);
                } else if (percentageSatisfiedProducts > 0) {
                    clients.get(clientDeliveries.first()).first().setSecond(clients.get(clientDeliveries.first()).first().second() + 1);
                }



                productors.clear();


            }
        }
        for (Entity client : clients.keySet()) {
            System.out.println("Client: " + client.getName());
            System.out.println("Amount of satisfied baskets: " + clients.get(client).first().first());
            System.out.println("Amount of partially satisfied baskets: " + clients.get(client).first().second());
            System.out.println("Amount of producers that contributed to this client: " + clients.get(client).second().size());
            System.out.println();
        }
    }

    /**
     * prints stats for every productor for all days
     * @return void
     * @param expeditions Tree map of all the expeditions
     */
    public void productorStats(TreeMap<Integer, Expedition> expeditions) {

        List<Productor> productorList = entityStore.getProductorsList();
        List<Company> companyList = entityStore.getCompanyList();
        List<Pair<Company, List<Productor>>> companyProductorList = new ArrayList<>();

        for (Company company : companyList) {
            if (company.isDistributionHub()) {
                List<Productor> distinctProductors = new ArrayList<>();
                TreeMap<Integer, List<Pair<Product, List<Pair<Double, Productor>>>>> stockMap = company.getStockMap();
                for (Integer day : stockMap.keySet()) {
                    List<Pair<Product, List<Pair<Double, Productor>>>> stocks = stockMap.get(day);
                    for (Pair<Product, List<Pair<Double, Productor>>> stock : stocks) {
                        for (Pair<Double, Productor> productorDoublePair : stock.second()) {
                            if (!distinctProductors.contains(productorDoublePair.second()) && productorDoublePair.first() != 0) {
                                distinctProductors.add(productorDoublePair.second());
                            }
                        }
                    }

                }
                companyProductorList.add(new Pair<>(company, distinctProductors));
            }
        }

        for (Productor productor : productorList){
            int suppliedHubs = 0;
            for (Pair<Company, List<Productor>> companyListPair : companyProductorList) {
                if (companyListPair.second().contains(productor)) {
                    suppliedHubs++;
                }
            }
            int totallyDelivered = 0;
            int partiallyDelivered = 0;
            int soldOutProducts = 0;
            TreeMap<Integer, Basket> originalBasketsMap = productor.getOriginalBasketsMap();
            TreeMap<Integer, Basket> basketsMap = productor.getBasketsMap();
            for (Integer day : basketsMap.keySet()) {
                boolean totallyDeliveredBool = true;
                boolean partiallyDeliveredBool = false;
                Basket basket = basketsMap.get(day);
                Basket originalBasket = originalBasketsMap.get(day);
                List<Pair<Product, Double>> products = basket.getProducts();
                List<Pair<Product, Double>> originalProducts = originalBasket.getProducts();
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).second() != 0){
                        products.get(i).first().setSoldOut(false);
                        totallyDeliveredBool = false;
                    }
                    if (products.get(i).second() != originalProducts.get(i).second()) {
                        partiallyDeliveredBool = true;
                    }
                }
                if (totallyDeliveredBool) {
                    totallyDelivered++;
                } else if (partiallyDeliveredBool) {
                    partiallyDelivered++;
                }
            }

            TreeMap<Integer, Basket> basketsMap1 = productor.getBasketsMap();
            Basket basket = basketsMap1.get(1);
            List<Pair<Product, Double>> products = basket.getProducts();
            for (Pair<Product, Double> productDoublePair : products) {
                if (productDoublePair.first().isSoldOut()) {
                    soldOutProducts++;
                }
                productDoublePair.first().setSoldOut(true);
            }
            System.out.println("Productor: " + productor.getName());
            System.out.println("Amount of totally delivered baskets: " + totallyDelivered);
            System.out.println("Amount of partially delivered baskets: " + partiallyDelivered);
            System.out.println("Amount of sold out products: " + soldOutProducts);
            System.out.println("Amount of hubs that received products from this productor: " + suppliedHubs);
            System.out.println();

        }

    }

    /**
     * prints stats for every company for all days
     * @return void
     * @param expeditions Tree map of all the expeditions
     */
    public void hubStats(TreeMap<Integer, Expedition> expeditions) {
        List<Company> companyList = entityStore.getCompanyList();

        for (Company company : companyList) {
            if (company.isDistributionHub()){
                List<Productor> distinctProductors = new ArrayList<>();
                TreeMap<Integer, List<Pair<Product, List<Pair<Double, Entity>>>>> orderMap = company.getOrderMap();
                TreeMap<Integer, List<Pair<Product, List<Pair<Double, Productor>>>>> stockMap = company.getStockMap();

                for (Integer day : orderMap.keySet()) {
                    List<Pair<Product, List<Pair<Double, Productor>>>> stocks = stockMap.get(day);
                    for (Pair<Product, List<Pair<Double, Productor>>> stock : stocks) {
                        for (Pair<Double, Productor> productorDoublePair : stock.second()) {
                            if (!distinctProductors.contains(productorDoublePair.second()) && productorDoublePair.first() != 0) {
                                distinctProductors.add(productorDoublePair.second());
                            }
                        }
                    }

                }

                System.out.println("Hub: " + company.getName());
                if (orderMap.size() == 0) {
                    System.out.println("Zero clients pickup orders at this hub");
                } else System.out.println("Amount of clients that pickup baskets at this hub: " + orderMap.get(1).get(0).second().size());

                if (distinctProductors.size() != 0){
                    System.out.println("Amount of producers that deliver baskets to this hub: " + distinctProductors.size());
                } else {
                    System.out.println("Zero producers deliver baskets to this hub");
                }

                System.out.println();
                System.out.println();
            }

        }
    }
}
