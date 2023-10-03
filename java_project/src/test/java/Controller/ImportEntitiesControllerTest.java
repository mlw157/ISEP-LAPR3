package Controller;

import Domain.Model.Entity;
import Domain.Store.EntityStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.graph.map.MapGraph;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImportEntitiesControllerTest {

    MapGraph<Entity, Integer> testMapGraph;
    EntityStore entityStore = App.getInstance().getOrganization().getEntityStore();
    ImportEntitiesController controller = new ImportEntitiesController();

    MapGraph<Entity, Integer> emptyMapGraph = new MapGraph<>(false);

    void resetGraph() {
        testMapGraph = emptyMapGraph.clone();
        entityStore.setEntitiesGraph(testMapGraph);
        MapGraph<Entity, Integer> testMapGraph = entityStore.getEntitiesGraph();
    }

    @Test
    void noEntitiesTest() throws FileNotFoundException {
        resetGraph();
        controller.importEntityInfo("src/test/resources/Entities/no_entities_test.csv");
        assertEquals(0, testMapGraph.numVertices());
    }

    @Test
    void noConnectionsTest() throws FileNotFoundException {
        resetGraph();
        controller.importEntityConnections("src/test/resources/Entities/no_connections_test.csv");
        assertEquals(0, testMapGraph.numEdges());
    }

    @Test
    void oneConnectionTest() throws FileNotFoundException {
        resetGraph();
        controller.importEntityInfo("src/test/resources/Entities/two_entities_test.csv");
        controller.importEntityConnections("src/test/resources/Entities/one_connection_test.csv");
        assertEquals(2, testMapGraph.numEdges());
    }

    @Test
    void twoEntitiesTest() throws FileNotFoundException {
        resetGraph();
        controller.importEntityInfo("src/test/resources/Entities/two_entities_test.csv");
        assertEquals(2, testMapGraph.numVertices());
    }


    @Test
    void findEntityByLocationIdTest() throws FileNotFoundException {
        resetGraph();
        controller.importEntityInfo("src/test/resources/Entities/two_entities_test.csv");
        controller.importEntityConnections("src/test/resources/Entities/one_connection_test.csv");
        List<Entity> entityList = testMapGraph.vertices(); // get all the entities from the graph
        Entity firstEntity = controller.findEntityByLocationId(entityList, "CT1");
        Entity secondEntity = controller.findEntityByLocationId(entityList, "CT2");
        assertEquals(firstEntity, entityList.get(0));
        assertEquals(secondEntity, entityList.get(1));
    }

}