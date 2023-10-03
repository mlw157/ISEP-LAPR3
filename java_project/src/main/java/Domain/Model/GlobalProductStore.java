package Domain.Model;

import utils.Pair;

import java.util.List;
import java.util.TreeMap;

public class GlobalProductStore {

    public TreeMap<Integer, List<Pair<Product, List<Pair<Double, Productor>>>>> stockMap = new TreeMap<>(); // day, quantity, productor

    public TreeMap<Integer, List<Pair<Product, List<Pair<Double, Entity>>>>> orderMap = new TreeMap<>(); // day, quantity, productor

    public TreeMap<Integer, List<Pair<Product, List<Pair<Double, Productor>>>>> getStockMap() {
        return stockMap;
    }

    public TreeMap<Integer, List<Pair<Product, List<Pair<Double, Entity>>>>> getOrderMap() {
        return orderMap;
    }
}
