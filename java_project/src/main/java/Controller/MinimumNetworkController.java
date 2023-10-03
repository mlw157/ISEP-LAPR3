package Controller;

import Domain.Model.Entity;
import Domain.Store.EntityStore;
import utils.graph.Algorithms;
import utils.graph.Edge;
import utils.graph.map.MapGraph;

/**
 * Controller class for get the minimum path (US305)
 * @author: Pedro Campos <1211511@isep.ipp.pt>
 * @author: João Costa <1211546@isep.ipp.pt>
 */
public class MinimumNetworkController {
    private EntityStore entityStore;

    private MapGraph<Entity,Integer> mapGraph;

    public MinimumNetworkController(){
        this.entityStore=App.getInstance().getOrganization().getEntityStore();
        this.mapGraph = entityStore.getEntitiesGraph();
    }

    public int getMinimumNetworkDistance(MapGraph<Entity,Integer> mapGraph){
        int totalDistance = 0;
        for (Edge<Entity, Integer> edge : mapGraph.edges()) {
            totalDistance = totalDistance + edge.getWeight();
        }
        return totalDistance;
    }



    /**
     * Method to get the minimum network to connect all entities
     * @return the minimum network graph to connect all entities
     */
    public MapGraph<Entity, Integer> getMinimumDistGraph() {
        MapGraph<Entity, Integer> minimumDistGraph = App.getInstance().getOrganization().getEntityStore().getEntitiesGraph().clone();
        minimumDistGraph = Algorithms.kruskallAlgorithm(minimumDistGraph);
        return minimumDistGraph;
    }


}
