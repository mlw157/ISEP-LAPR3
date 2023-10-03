package Controller;

import Domain.Model.*;
import Domain.Store.EntityStore;
import Domain.Store.ExpeditionStore;
import Domain.Store.IrrigationControllerStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Pair;
import utils.graph.map.MapGraph;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpeditionStatsControllerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private ExpeditionStore expeditionStore;
    private ExpeditionStatsController expeditionStatsController;

    @BeforeEach
    void setUp() {
        expeditionStore = new ExpeditionStore();
        expeditionStatsController = new ExpeditionStatsController();

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    @DisplayName("Should print the correct amount of producers that deliver baskets to this hub")
    void hubStatsShouldPrintCorrectAmountOfProducersThatDeliverBasketsToThisHub() {
        Location location = new Location("1",0, 0);
        Product product = new Product("product");
        Product product1 = new Product("product1");
        Product product2 = new Product("product2");
        Product product3 = new Product("product3");
        Product product4 = new Product("product4");
        Product product5 = new Product("product5");
        Product product6 = new Product("product6");
        Product product7 = new Product("product7");
        Product product8 = new Product("product8");

        Company company = new Company(location, "company");
        Company company1 = new Company(location, "company1");
        Company company2 = new Company(location, "company2");

        company.setDistributionHub(true);
        company1.setDistributionHub(true);
        company2.setDistributionHub(true);

        Basket basket = new Basket(new ArrayList<>());
        Basket basket1 = new Basket(new ArrayList<>());
        Basket basket2 = new Basket(new ArrayList<>());

        basket.addQuantity(product, Double.parseDouble("10"));
        basket.addQuantity(product1, Double.parseDouble("10"));
        basket.addQuantity(product2, Double.parseDouble("10"));

        basket1.addQuantity(product3, Double.parseDouble("10"));
        basket1.addQuantity(product4, Double.parseDouble("10"));
        basket1.addQuantity(product5, Double.parseDouble("10"));

        basket2.addQuantity(product6, Double.parseDouble("10"));
        basket2.addQuantity(product7, Double.parseDouble("10"));
        basket2.addQuantity(product8, Double.parseDouble("10"));

        TreeMap<Integer, Basket> basketsMap = new TreeMap<>();

        basketsMap.put(0, basket);
        basketsMap.put(1, basket1);
        basketsMap.put(2, basket2);

        company.setBasketsMap(basketsMap);

        List<Pair<Double, Entity>> list = new ArrayList<>();

        list.add(new Pair<>(10d, company));

        List<Pair<Double, Entity>> list1 = new ArrayList<>();

        list1.add(new Pair<>(10d, company));

        List<Pair<Double, Entity>> list2 = new ArrayList<>();

        list2.add(new Pair<>(10d, company));

        List<Pair<Double, Entity>> list3 = new ArrayList<>();

        list3.add(new Pair<>(10d, company));

        List<Pair<Double, Entity>> list4 = new ArrayList<>();

        list4.add(new Pair<>(10d, company));

        List<Pair<Double, Entity>> list5 = new ArrayList<>();

        list5.add(new Pair<>(10d, company));

        List<Pair<Double, Entity>> list6 = new ArrayList<>();

        list6.add(new Pair<>(10d, company));

        List<Pair<Double, Entity>> list7 = new ArrayList<>();

        list7.add(new Pair<>(10d, company));

        List<Pair<Double, Entity>> list8 = new ArrayList<>();

        list8.add(new Pair<>(10d, company));

        List<Pair<Double, Entity>> list9 = new ArrayList<>();

        list9.add(new Pair<>(10d, company));
    }

    @Test
    @DisplayName("Should print the correct percentage of satisfied products")
    void basketDayStatsShouldPrintCorrectPercentageOfSatisfiedProducts() {
        ExpeditionStatsController expeditionStatsController = new ExpeditionStatsController();
        Product product1 = new Product("product1");
        Product product2 = new Product("product2");
        Product product3 = new Product("product3");
        Product product4 = new Product("product4");
        Product product5 = new Product("product5");
        Product product6 = new Product("product6");
        Product product7 = new Product("product7");
        Product product8 = new Product("product8");
        Product product9 = new Product("product9");
        Product product10 = new Product("product10");

        Location location1 = new Location("1",0, 0);
        Location location2 = new Location("1",0, 0);
        Location location3 = new Location("3",0, 0);

        Company company1 = new Company(location1, "company1");
        Company company2 = new Company(location2, "company2");
        Company company3 = new Company(location3, "company3");

        company1.setDistributionHub(true);
        company2.setDistributionHub(true);

        List<Product> products1 = new ArrayList<>();
        products1.add(product1);
        products1.add(product2);
        products1.add(product3);

        List<Product> products2 = new ArrayList<>();
        products2.add(product4);
        products2.add(product5);

        List<Product> products3 = new ArrayList<>();
        products3.add(product6);

        List<Product> products4 = new ArrayList<>();
        products4.add(product7);

        List<Product> products5 = new ArrayList<>();
        products5.add(product8);

        List<Product> products6 = new ArrayList<>();
        products6.add(product9);

        List<Product> products7 = new ArrayList<>();
        products7.add(product10);
    }
}