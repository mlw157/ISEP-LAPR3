package Controller;

import Domain.Model.Client;
import Domain.Model.Company;
import Domain.Model.Entity;
import Domain.Model.Location;
import Domain.Store.EntityStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Pair;
import utils.graph.map.MapGraph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FindNearestHubControllerTest {

    EntityStore entityStore = App.getInstance().getOrganization().getEntityStore();
    FindNearestHubController controller = new FindNearestHubController();
    DefineHubsController defineHubsController = new DefineHubsController();
    MapGraph<Entity, Integer> emptyMapGraph = new MapGraph<>(false);
    MapGraph<Entity, Integer> testMapGraph = new MapGraph<>(false);

    Client client1 = new Client(new Location("id1", 99.1, 100.1), "client1");
    Client client2 = new Client(new Location("id2", 99.2, 100.2), "client2");
    Client client3 = new Client(new Location("id3", 99.3, 100.3), "client3");
    Company comp1 = new Company(new Location("id4", 99.4, 100.4), "comp1");
    Company comp2 = new Company(new Location("id5", 99.5, 100.5), "comp2");


    void resetGraph() {
        testMapGraph = emptyMapGraph.clone();
        entityStore.setEntitiesGraph(testMapGraph);
        MapGraph<Entity, Integer> testMapGraph = entityStore.getEntitiesGraph();
    }

    @Test
    void getEntityReachableDistributionHubs() {
        resetGraph();
        testMapGraph.addVertex(client1);
        testMapGraph.addVertex(client2);
        testMapGraph.addVertex(client3);
        testMapGraph.addVertex(comp1);
        testMapGraph.addVertex(comp2);
        testMapGraph.addEdge(client1, comp1, 10);
        testMapGraph.addEdge(client2, comp1, 10);
        testMapGraph.addEdge(client3, comp1, 10);
        testMapGraph.addEdge(client1, comp2, 20);
        testMapGraph.addEdge(client2, comp2, 20);
        testMapGraph.addEdge(client3, comp2, 20);
        testMapGraph.addEdge(comp1, comp2, 10);

        defineHubsController.defineHubs(2);

        List<Pair<Company, Integer>> hubList = controller.getEntityReachableDistributionHubs(client1);
        assertEquals(2, hubList.size());
        assertEquals(10, hubList.get(0).second());
        assertEquals(20, hubList.get(1).second());

    }

    @Test
    void getNearestDistributionHub() {
        resetGraph();
        testMapGraph.addVertex(client1);
        testMapGraph.addVertex(client2);
        testMapGraph.addVertex(client3);
        testMapGraph.addVertex(comp1);
        testMapGraph.addVertex(comp2);
        testMapGraph.addEdge(client1, comp1, 10);
        testMapGraph.addEdge(client2, comp1, 10);
        testMapGraph.addEdge(client3, comp1, 10);
        testMapGraph.addEdge(client1, comp2, 20);
        testMapGraph.addEdge(client2, comp2, 20);
        testMapGraph.addEdge(client3, comp2, 20);
        testMapGraph.addEdge(comp1, comp2, 10);

        defineHubsController.defineHubs(2);

        Pair<Company, Integer> nearestHub = controller.getNearestDistributionHub(client1);

        assertEquals(comp1, nearestHub.first());
        assertEquals(10, nearestHub.second());
    }

    @Test
    void getNearestDistributionHubForEachClient() {
        resetGraph();
        testMapGraph.addVertex(client1);
        testMapGraph.addVertex(client2);
        testMapGraph.addVertex(client3);
        testMapGraph.addVertex(comp1);
        testMapGraph.addVertex(comp2);
        testMapGraph.addEdge(client1, comp1, 10);
        testMapGraph.addEdge(client2, comp1, 10);
        testMapGraph.addEdge(client3, comp1, 10);
        testMapGraph.addEdge(client1, comp2, 20);
        testMapGraph.addEdge(client2, comp2, 20);
        testMapGraph.addEdge(client3, comp2, 5);
        testMapGraph.addEdge(comp1, comp2, 10);

        defineHubsController.defineHubs(2);

        List<Pair<Entity, Pair<Company, Integer>>> nearestHubForEachClient = controller.getNearestDistributionHubForEachClient();

        assertEquals(3, nearestHubForEachClient.size());

        assertEquals(client1, nearestHubForEachClient.get(0).first());
        assertEquals(comp1, nearestHubForEachClient.get(0).second().first());
        assertEquals(10, nearestHubForEachClient.get(0).second().second());

        assertEquals(client2, nearestHubForEachClient.get(1).first());
        assertEquals(comp1, nearestHubForEachClient.get(1).second().first());
        assertEquals(10, nearestHubForEachClient.get(1).second().second());

        assertEquals(client3, nearestHubForEachClient.get(2).first());
        assertEquals(comp2, nearestHubForEachClient.get(2).second().first());
        assertEquals(5, nearestHubForEachClient.get(2).second().second());


    }
}