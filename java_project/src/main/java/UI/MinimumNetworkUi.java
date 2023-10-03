package UI;

import Controller.MinimumNetworkController;
import Domain.Model.Entity;
import utils.graph.Edge;
import utils.graph.map.MapGraph;

import java.util.Scanner;

public class MinimumNetworkUi implements Runnable{

    MinimumNetworkController minimumNetworkController;

    public MinimumNetworkUi(){
        minimumNetworkController =new MinimumNetworkController();
    }

    Scanner sc = new Scanner(System.in);

    @Override
    public void run() {
        MapGraph<Entity, Integer> entityGraph = minimumNetworkController.getMinimumDistGraph();

        System.out.println("Shortest network between clients and producers: ");

        for (Edge edge : entityGraph.edges()) {
            System.out.println(edge);
            System.out.println("---------------------");
        }

        System.out.println("Number of edges: " + entityGraph.numEdges()/2);
        System.out.println("Total distance: " + minimumNetworkController.getMinimumNetworkDistance(entityGraph)/2);
        System.out.println();


    }
}
