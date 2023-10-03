package Controller;

import Domain.Model.Entity;
import Domain.Store.EntityStore;
import utils.graph.Algorithms;
import utils.graph.Edge;
import utils.graph.map.MapGraph;

import java.util.*;
/**
 * Controller class to Verify Connectivity (US302)
 * @author Diogo Costa <1211514@isep.ipp.pt>
 */
public class VerifyConnectivityController {

    private EntityStore entityStore;

    private MapGraph <Entity,Integer> mapGraph;

    public VerifyConnectivityController(){
        this.entityStore=App.getInstance().getOrganization().getEntityStore();
        this.mapGraph = entityStore.getEntitiesGraph();
    }
    /**
     * Method that verifies if the graph is connected
     * @return if the graph is connected or not
     */
    public boolean isConnected(){
        LinkedList<Entity> queue = Algorithms.DepthFirstSearch(mapGraph,mapGraph.vertex(0));
        if(queue == null){
            return false;
        }
        if (mapGraph.numVertices() == queue.size()){
            return true;
        }else {
            return false;
        }
    }
    /**
     * Method that creates a copy of the graph without weights
     * @return the graph without weights
     */
    public MapGraph<Entity, Integer> IgnoreWeights(){
        MapGraph<Entity, Integer> noWeightsMapGraph = new MapGraph<>(false);
        noWeightsMapGraph = mapGraph.clone();
        for (Edge<Entity,Integer> edge : noWeightsMapGraph.edges()){
                edge.setWeight(1);
        }
        return noWeightsMapGraph;
    }
    /**
     * Method that obtains the diameter of the graph
     * @return the diameter of the graph
     */
    public int minimumNumberOfConnections(){
        LinkedList<Entity> path = new LinkedList<>();
        int maxNumberOfConnections = 0;
        MapGraph<Entity, Integer> noWeightsMapGraph = IgnoreWeights();
        for(Entity entity : noWeightsMapGraph.vertices()) {
            LinkedList<Entity> queue = new LinkedList<>();
            queue = Algorithms.BreadthFirstSearch(noWeightsMapGraph, entity);
            Algorithms.shortestPath(noWeightsMapGraph,entity,queue.getLast(),Integer::compareTo,Integer::sum,0,path);
            if(maxNumberOfConnections < path.size()-1)
                maxNumberOfConnections = path.size()-1;
        }
        return maxNumberOfConnections;
    }
}
