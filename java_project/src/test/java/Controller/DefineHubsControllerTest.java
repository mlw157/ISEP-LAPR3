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

class DefineHubsControllerTest {

    EntityStore entityStore = App.getInstance().getOrganization().getEntityStore();
    DefineHubsController controller = new DefineHubsController();
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
    void getAverageProximityDistanceOfCompanyTest() {
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
        testMapGraph.addEdge(comp1, comp2, 15);

        int distance = controller.getAverageProximityDistanceOfCompany(comp1);
        int distance2 = controller.getAverageProximityDistanceOfCompany(comp2);

        assertEquals(11, distance);
        assertEquals(18, distance2);



    }

    @Test
    void getCompanyAverageDistanceListTest() {
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
        testMapGraph.addEdge(comp1, comp2, 15);

        List<Pair<Company, Integer>> companyAverageDistanceList = controller.getCompanyAverageDistanceList();

        assertEquals(2, companyAverageDistanceList.size());
        assertEquals(11, companyAverageDistanceList.get(0).second());
        assertEquals(18, companyAverageDistanceList.get(1).second());


    }

    @Test
    void defineHubsTest() {
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
        testMapGraph.addEdge(comp1, comp2, 15);

        controller.defineHubs(1);
        assertTrue(comp1.isDistributionHub());
        assertFalse(comp2.isDistributionHub());
    }
}