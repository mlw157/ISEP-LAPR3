package Domain.Store;

import Domain.Model.Company;
import Domain.Model.Expedition;
import Domain.Model.Product;
import Domain.Model.Productor;
import utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;


/**
 * Class that stores all the expeditions
 * @author Pedro Campos <1211511@isep.ipp.pt>
 */
public class ExpeditionStore {

    private TreeMap<Integer, Expedition> noRestrictionsExpeditionMap = new TreeMap<>();
    private TreeMap<Integer, Expedition> expeditionMap = new TreeMap<>();

    public ExpeditionStore() {

    }

    public HashMap<Company, HashMap<Product, Double>>originalHubStockList = new HashMap<>();
    public HashMap<Company, HashMap<Product, Double>> getOriginalHubStockList() {
        return originalHubStockList;
    }

    public TreeMap<Integer, Expedition> getNoRestrictionsExpeditionMap() {
        return noRestrictionsExpeditionMap;
    }

    public TreeMap<Integer, Expedition> getExpeditionMap() {
        return expeditionMap;
    }

    public void setNoRestrictionsExpeditionMap(TreeMap<Integer, Expedition> noRestrictionsExpeditionMap) {
        this.noRestrictionsExpeditionMap = noRestrictionsExpeditionMap;
    }

    public void setExpeditionMap(TreeMap<Integer, Expedition> expeditionMap) {
        this.expeditionMap = expeditionMap;
    }

    public void addExpedition(Expedition expedition, boolean restrictions) {
        if (!restrictions) {
            noRestrictionsExpeditionMap.put(expedition.getDayOffset(), expedition);
        } else {
            expeditionMap.put(expedition.getDayOffset(), expedition);
        }
    }

}
