package Controller;

import Domain.Model.Company;
import Domain.Model.Entity;
import Domain.Store.EntityStore;
import utils.Pair;
import utils.graph.Algorithms;
import utils.graph.map.MapGraph;

import java.util.*;

/**
 * Controller class for defining Distribution Hubs (US303)
 * @author: Pedro Campos <1211511@isep.ipp.pt>
 * @author: Pedro Lopes <1211504@isep.ipp.pt>
 */
public class DefineHubsController {

    private EntityStore entityStore;

    public DefineHubsController() {
        this.entityStore = App.getInstance().getOrganization().getEntityStore();
    }

    /**
     * Method to get the average proximity distance to all other entities for a respective company
     * @param company the company to get the average proximity distance to all other entities
     * @return the average proximity distance to all other entities
     */
    public int getAverageProximityDistanceOfCompany(Company company) {
        MapGraph<Entity, Integer> entityGraph = entityStore.getEntitiesGraph();
        int totalDistance = 0;

        ArrayList<LinkedList<Entity>> paths = new ArrayList<>();
        ArrayList<Integer> pathsDistances = new ArrayList<>();

        Algorithms.shortestPaths(entityGraph, company, Integer::compareTo, Integer::sum,0, paths, pathsDistances);
        for (int i = 0; i < paths.size(); i++) {
            if (!(paths.get(i).getLast().equals(company))) {
                totalDistance += pathsDistances.get(i);
            }

        }
        int averageDistance = totalDistance / (paths.size() - 1);

        return averageDistance;
    }

    /**
     * Method to get a list of all companies with their respective average proximity distance to all other entities
     * @return a list of pairs with the companies and their respective average proximity distance to all other entities
     */
    public List<Pair<Company, Integer>> getCompanyAverageDistanceList() {
        List<Pair<Company, Integer>> companyAverageDistanceList = new ArrayList<>();

        for (Entity entity : entityStore.getEntitiesGraph().vertices()) {
            if (entity instanceof Company) {
                int averageDistance = getAverageProximityDistanceOfCompany((Company) entity);
                companyAverageDistanceList.add(new Pair<>((Company) entity, averageDistance));
            }

        }
        return companyAverageDistanceList;
    }

    /**
     * Method to define the n companies with the lowest average proximity distance to all other entities as Distribution Hubs
     */
    public void defineHubs(int numberOfHubs) {

        List<Pair<Company, Integer>> companyAverageDistanceList = getCompanyAverageDistanceList();
        Collections.sort(companyAverageDistanceList, Comparator.comparing(p -> p.second()));
        if (numberOfHubs > companyAverageDistanceList.size()) {
            numberOfHubs = companyAverageDistanceList.size();
            System.out.println("Number of hubs is greater than the number of companies, setting number of hubs to " + numberOfHubs);
        }

        for (int i = 0; i < numberOfHubs; i++) {
            companyAverageDistanceList.get(i).first().setDistributionHub(true);
            System.out.println("Company " + companyAverageDistanceList.get(i).first().getName() + " is now a distribution hub with a average distribution distance of " + companyAverageDistanceList.get(i).second() + " meters");
        }
    }

}
