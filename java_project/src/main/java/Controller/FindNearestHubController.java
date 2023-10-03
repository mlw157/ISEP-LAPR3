package Controller;

import Domain.Model.Client;
import Domain.Model.Company;
import Domain.Model.Entity;
import Domain.Model.Productor;
import Domain.Store.EntityStore;
import utils.Pair;
import utils.graph.Algorithms;
import utils.graph.map.MapGraph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Controller class for finding the nearest distribution Hub for each client (US304)
 * @author: Pedro Campos <1211511@isep.ipp.pt>
 */

public class FindNearestHubController {

    private EntityStore entityStore;

    public FindNearestHubController() {
        this.entityStore = App.getInstance().getOrganization().getEntityStore();
    }

    /**
     * Method to get a list of reachable Distribution Hubs from a given entity with the respective path distance
     * @param entity the entity to find the Distribution Hubs from
     * @return a list of pairs with the reachable Distribution Hubs and the distance to each one
     */
    public List<Pair<Company, Integer>> getEntityReachableDistributionHubs(Entity entity) {
        List<Pair<Company, Integer>> reachableDistributionHubs = new ArrayList<>();
        MapGraph<Entity, Integer> entityGraph = entityStore.getEntitiesGraph();
        ArrayList<LinkedList<Entity>> paths = new ArrayList<>();
        ArrayList<Integer> pathsDistances = new ArrayList<>();

        Algorithms.shortestPaths(entityGraph, entity, Integer::compareTo, Integer::sum,0, paths, pathsDistances);
        for (int i = 0; i < paths.size(); i++) {
            if (paths.get(i).getLast() instanceof Company && ((Company) paths.get(i).getLast()).isDistributionHub()) {
                reachableDistributionHubs.add(new Pair<>((Company) paths.get(i).getLast(), pathsDistances.get(i)));
            }
        }

        return reachableDistributionHubs;
    }

    /**
     * Method to get the nearest Distribution Hub to a given entity
     * @param entity the entity to find the nearest Distribution Hub from
     * @return a pair with the nearest company and the distance to it
     */
    public Pair<Company, Integer> getNearestDistributionHub(Entity entity) {
        List<Pair<Company, Integer>> reachableDistributionHubs = getEntityReachableDistributionHubs(entity);
        Pair<Company, Integer> nearestDistributionHub = null;
        for (Pair<Company, Integer> pair : reachableDistributionHubs) {
            if (nearestDistributionHub == null || pair.second() < nearestDistributionHub.second()) {
                nearestDistributionHub = pair;
            }
        }
        return nearestDistributionHub;
    }

    /**
     * Method to get the nearest Distribution Hub for each client
     * @return a list of pairs with the nearest Distribution Hub for each client and the distance to it
     */

    public List<Pair<Entity, Pair<Company, Integer>>> getNearestDistributionHubForEachClient() {
        List<Pair<Entity, Pair<Company, Integer>>> nearestDistributionHubForAllClients = new ArrayList<>();
        for (Entity entity : entityStore.getEntitiesGraph().vertices()) {
            if (!(entity instanceof Productor)) {
                if(entity instanceof Company){
                    if(((Company) entity).isDistributionHub()){
                        continue;
                    }
                }
                Pair<Company, Integer> nearestDistributionHub = getNearestDistributionHub(entity);
                nearestDistributionHubForAllClients.add(new Pair<>(entity, nearestDistributionHub));

                entity.setPickupHub(nearestDistributionHub.first());
            }
        }
        return nearestDistributionHubForAllClients;
    }




}
