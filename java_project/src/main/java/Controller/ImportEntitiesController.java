package Controller;

import Domain.Model.*;
import Domain.Store.EntityStore;
import utils.graph.map.MapGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Controller class for importing data from files (US301)
 * @author: Pedro Campos <1211511@isep.ipp.pt>
 */
public class ImportEntitiesController {

    private EntityStore entityStore;
    public ImportEntitiesController() {
        this.entityStore = App.getInstance().getOrganization().getEntityStore();
    }

    /**
     * Imports the entity information from a file to entityStore
     * @param file the file to import from
     * @throws FileNotFoundException if the file is not found
     */
    public void importEntityInfo(String file) throws FileNotFoundException {
        List<Entity> entityList = new ArrayList<>(); // list containing all the entities
        Scanner sc = new Scanner(new File(file));
        int lineNumber = 1;
        sc.nextLine(); // skip header
        while (sc.hasNextLine()) {
            try {
                String line = sc.nextLine();
                String[] elements = line.split(",");
                Location entityLocation = new Location(elements[0], Double.parseDouble(elements[1]), Double.parseDouble(elements[2]));
                char entityType = elements[3].charAt(0); // get the first character of the string to distinguish between the different types of entities
                switch (entityType){
                    case 'C': // if the entity is a client
                        entityList.add(new Client(entityLocation, elements[3]));
                        break;
                    case 'E': // if the entity is a company
                        entityList.add(new Company(entityLocation, elements[3]));
                        break;
                    case 'P': // if the entity is a productor
                        entityList.add(new Productor(entityLocation, elements[3]));
                        break;
                }
            } catch (Exception e) {
                System.out.printf("Error in line: %d %s ", lineNumber, e.getCause());
            }
            lineNumber++;
        }

        MapGraph<Entity, Integer> entitiesGraph = entityStore.getEntitiesGraph();
        for (Entity e : entityList) {
            entitiesGraph.addVertex(e);
        }

    }

    /**
     * Finds the correct entity object, given its location id
     * @param entityList list of entities
     * @param locationId location id of the entity
     * @return the entity object
     */
    public Entity findEntityByLocationId(List<Entity> entityList, String locationId){
        for (Entity e : entityList) {
            if (e.getLocation().getLocationId().equals(locationId)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Imports the entity connections from a file and adds them to the entityStore
     * @param file the file to import from
     * @throws FileNotFoundException if the file is not found
     */
    public void importEntityConnections(String file) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(file));
        int lineNumber = 1;
        sc.nextLine(); // skip header
        while (sc.hasNextLine()) {
            try {
                String line = sc.nextLine();
                String[] elements = line.split(",");
                String firstLocationId = elements[0]; // location id of the first entity
                String secondLocationId = elements[1]; // location id of the second entity
                int distance = Integer.parseInt(elements[2]); // distance between the two locations
                MapGraph<Entity, Integer> entitiesGraph = entityStore.getEntitiesGraph();
                List<Entity> entityList = entitiesGraph.vertices(); // get all the entities from the graph
                Entity firstEntity = findEntityByLocationId(entityList, firstLocationId); // find the first entity by location id
                Entity secondEntity = findEntityByLocationId(entityList, secondLocationId); // find the second entity by location id
                entitiesGraph.addEdge(firstEntity, secondEntity, distance); // add the edge to the graph
            } catch (Exception e) {
                System.out.printf("Error in line: %d %s ", lineNumber, e.getCause());
            }
            lineNumber++;
        }
    }

}
