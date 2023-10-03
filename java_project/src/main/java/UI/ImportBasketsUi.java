package UI;

import Controller.App;
import Controller.ImportBasketsController;
import Domain.Model.Basket;
import Domain.Model.Entity;
import Domain.Model.Product;
import Domain.Model.Productor;
import Domain.Store.EntityStore;
import utils.Pair;
import utils.graph.map.MapGraph;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Controller class for importing baskets from files (US307)
 * @author: Joao Costa <1211546@isep.ipp.pt>
 */
public class ImportBasketsUi implements Runnable {

    ImportBasketsController controller;
    Scanner sc = new Scanner(System.in);
    public ImportBasketsUi() {
        this.controller = new ImportBasketsController();
    }
    @Override
    public void run() {

        System.out.println("Insert file containing the baskets to import: "); // file containing baskets
        String file = sc.nextLine();
        //String testFile = "src/main/resources/grafos/Small/cabazes_small.csv";
        //String bigFile = "src/main/resources/grafos/Big/cabazes_big.csv";
        try {
            controller.importBaskets(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public void print(){

        MapGraph<Entity, Integer> entityGraph = App.getInstance().getOrganization().getEntityStore().getEntitiesGraph();

        for (Entity entity : entityGraph.vertices()) {
            System.out.println(entity.getName());
            TreeMap<Integer, Basket> basketsMap = entity.getBasketsMap();
            for (Integer dayOffset : basketsMap.keySet()) {
                Basket basket = basketsMap.get(dayOffset);
                ArrayList<Pair<Product, Double>> products = basket.getProducts();
                System.out.println("Day " + dayOffset + " basket: ");
                for (Pair<Product, Double> pair : products) {
                    System.out.println(pair.first().getName() + " " + pair.second());
                }
            }
        }
    }
}


