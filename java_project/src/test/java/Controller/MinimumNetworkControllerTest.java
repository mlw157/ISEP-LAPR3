package Controller;

import Domain.Model.Client;
import Domain.Model.Company;
import Domain.Model.Entity;
import Domain.Model.Location;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.graph.map.MapGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MinimumNetworkControllerTest {
    Client client1 = new Client(new Location("id1", 99.1, 100.1), "client1");
    Client client2 = new Client(new Location("id2", 99.2, 100.2), "client2");
    Client client3 = new Client(new Location("id3", 99.3, 100.3), "client3");
    Company comp1 = new Company(new Location("id4", 99.4, 100.4), "comp1");
    Company comp2 = new Company(new Location("id5", 99.5, 100.5), "comp2");
    MinimumNetworkController controller=new MinimumNetworkController();

    @Test
    void getMinimumDistGraph() {
        App.getInstance().getOrganization().getEntityStore().setEntitiesGraph(new MapGraph<>(false));
        App.getInstance().getOrganization().getEntityStore().getEntitiesGraph().addEdge(client1,comp1,30);
        App.getInstance().getOrganization().getEntityStore().getEntitiesGraph().addEdge(client2,comp1,5);
        App.getInstance().getOrganization().getEntityStore().getEntitiesGraph().addEdge(client3,comp1,25);
        App.getInstance().getOrganization().getEntityStore().getEntitiesGraph().addEdge(client1,comp2,10);
        App.getInstance().getOrganization().getEntityStore().getEntitiesGraph().addEdge(client2,comp2,100);
        App.getInstance().getOrganization().getEntityStore().getEntitiesGraph().addEdge(client3,comp2,35);
        App.getInstance().getOrganization().getEntityStore().getEntitiesGraph().addEdge(comp2,comp1,10);
        MapGraph<Entity,Integer> testgraph=new MapGraph<>(false);
        testgraph.addVertex(client1);
        testgraph.addVertex(comp1);
        testgraph.addVertex(client2);
        testgraph.addVertex(client3);
        testgraph.addVertex(comp2);
        testgraph.addEdge(client1,comp2,10);
        testgraph.addEdge(comp1,client2,5);
        testgraph.addEdge(comp2,comp1,10);
        testgraph.addEdge(client3,comp1,25);
        assertEquals(testgraph,controller.getMinimumDistGraph());
    }
    @Test
    void getMinimumNetworkDistance(){
        App.getInstance().getOrganization().getEntityStore().getEntitiesGraph().addEdge(client1,comp1,30);
        App.getInstance().getOrganization().getEntityStore().getEntitiesGraph().addEdge(client2,comp1,5);
        App.getInstance().getOrganization().getEntityStore().getEntitiesGraph().addEdge(client3,comp1,25);
        App.getInstance().getOrganization().getEntityStore().getEntitiesGraph().addEdge(client1,comp2,10);
        App.getInstance().getOrganization().getEntityStore().getEntitiesGraph().addEdge(client2,comp2,100);
        App.getInstance().getOrganization().getEntityStore().getEntitiesGraph().addEdge(client3,comp2,35);
        App.getInstance().getOrganization().getEntityStore().getEntitiesGraph().addEdge(comp2,comp1,10);
        int result=100;
        assertEquals(result,controller.getMinimumNetworkDistance(controller.getMinimumDistGraph()));
    }
}