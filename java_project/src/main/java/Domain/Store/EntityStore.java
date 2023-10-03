package Domain.Store;

import Domain.Model.Company;
import Domain.Model.Entity;
import Domain.Model.Productor;
import utils.graph.map.MapGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to store and create all the entities of the system
 * @author Pedro Campos <1211511@isep.ipp.pt>
 */
public class EntityStore {

    private MapGraph<Entity, Integer> entitiesGraph;

    private List<Productor> productorsList;

    public List<Productor> getProductorsList() {
        return productorsList;
    }


    private List<Company> companyList;

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setProductorsList() {
        List<Productor> productorsList = new ArrayList<>();
        for (Entity entity : entitiesGraph.vertices()) {
            if (entity instanceof Productor) {
                productorsList.add((Productor) entity);
            }
        }
        this.productorsList = productorsList;
    }

    public void setCompanyList() {
        List<Company> companyList = new ArrayList<>();
        for (Entity entity : entitiesGraph.vertices()) {
            if (entity instanceof Company) {
                companyList.add((Company) entity);
            }
        }
        this.companyList = companyList;
    }

    public EntityStore() {
        this.entitiesGraph = new MapGraph<>(false);
    }

    public MapGraph<Entity, Integer> getEntitiesGraph() {
        return entitiesGraph;
    }

    public void setEntitiesGraph(MapGraph<Entity, Integer> entitiesGraph) {
        this.entitiesGraph = entitiesGraph;
    }

    public Entity getEntityByName(String name) {
        for (Entity entity : entitiesGraph.vertices()) {
            if (entity.getName().equals(name)) {
                return entity;
            }
        }
        return null;
    }
}
