package Controller;

import Domain.Model.Company;
import Domain.Model.Entity;
import Domain.Model.Location;
import Domain.Store.EntityStore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.graph.map.MapGraph;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VerifyConnectivityControllerTest {

    EntityStore entityStore = App.getInstance().getOrganization().getEntityStore();
    VerifyConnectivityController controller = new VerifyConnectivityController();

    Company comp1 = new Company(new Location("id1", 99.1, 100.1), "comp1");
    Company comp2 = new Company(new Location("id2", 99.2, 100.2), "comp2");
    Company comp3 = new Company(new Location("id3", 99.3, 100.3), "comp3");
    Company comp4 = new Company(new Location("id4", 99.4, 100.4), "comp4");
    Company comp5 = new Company(new Location("id5", 99.5, 100.5), "comp5");

    @Test
    public void minimumNumberOfConnectionsGraphIsEmpty() {
        entityStore.getEntitiesGraph().removeVertex(entityStore.getEntitiesGraph().vertex(4));
        entityStore.getEntitiesGraph().removeVertex(entityStore.getEntitiesGraph().vertex(3));
        entityStore.getEntitiesGraph().removeVertex(entityStore.getEntitiesGraph().vertex(2));
        entityStore.getEntitiesGraph().removeVertex(entityStore.getEntitiesGraph().vertex(1));
        entityStore.getEntitiesGraph().removeVertex(entityStore.getEntitiesGraph().vertex(0));
        assertEquals(0,controller.minimumNumberOfConnections());
    }

    public void createConnectedGraph() {
        entityStore.getEntitiesGraph().addVertex(comp1);
        entityStore.getEntitiesGraph().addVertex(comp2);
        entityStore.getEntitiesGraph().addVertex(comp3);
        entityStore.getEntitiesGraph().addVertex(comp4);
        entityStore.getEntitiesGraph().addVertex(comp5);

        entityStore.getEntitiesGraph().addEdge(comp1, comp2, 1);
        entityStore.getEntitiesGraph().addEdge(comp2, comp3, 1);
        entityStore.getEntitiesGraph().addEdge(comp3, comp4, 1);
        entityStore.getEntitiesGraph().addEdge(comp4, comp5, 2);
        entityStore.getEntitiesGraph().addEdge(comp1, comp5, 2);
    }

    @Test
    public void minimumNumberOfConnections() {
        entityStore.getEntitiesGraph().removeVertex(entityStore.getEntitiesGraph().vertex(4));
        entityStore.getEntitiesGraph().removeVertex(entityStore.getEntitiesGraph().vertex(3));
        entityStore.getEntitiesGraph().removeVertex(entityStore.getEntitiesGraph().vertex(2));
        entityStore.getEntitiesGraph().removeVertex(entityStore.getEntitiesGraph().vertex(1));
        entityStore.getEntitiesGraph().removeVertex(entityStore.getEntitiesGraph().vertex(0));
        createConnectedGraph();
        assertEquals(2, controller.minimumNumberOfConnections());
    }

    @Test
    public void isConnected() {
        entityStore.getEntitiesGraph().removeVertex(entityStore.getEntitiesGraph().vertex(4));
        entityStore.getEntitiesGraph().removeVertex(entityStore.getEntitiesGraph().vertex(3));
        entityStore.getEntitiesGraph().removeVertex(entityStore.getEntitiesGraph().vertex(2));
        entityStore.getEntitiesGraph().removeVertex(entityStore.getEntitiesGraph().vertex(1));
        entityStore.getEntitiesGraph().removeVertex(entityStore.getEntitiesGraph().vertex(0));
        createConnectedGraph();
        boolean isConnected = controller.isConnected();
        Assertions.assertTrue(isConnected);
    }
    public void createUnConnectedGraph() {
        entityStore.getEntitiesGraph().addVertex(comp1);
        entityStore.getEntitiesGraph().addVertex(comp2);
        entityStore.getEntitiesGraph().addVertex(comp3);
        entityStore.getEntitiesGraph().addVertex(comp4);
        entityStore.getEntitiesGraph().addVertex(comp5);

        entityStore.getEntitiesGraph().addEdge(comp1, comp2, 2);
        entityStore.getEntitiesGraph().addEdge(comp1, comp3, 2);
        entityStore.getEntitiesGraph().addEdge(comp1, comp4, 2);
    }

    @Test
    public void notConnected(){
        createUnConnectedGraph();
        boolean notConnected = controller.isConnected();
        Assertions.assertFalse(notConnected);
    }
}