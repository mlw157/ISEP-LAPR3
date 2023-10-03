package Controller;

import Domain.Model.*;
import Domain.Store.EntityStore;
import Domain.Store.ExpeditionStore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculateExpeditionsControllerTest {

    private EntityStore entityStore;

    private ExpeditionStore expeditionStore;

    CalculateExpeditionsController calculateExpeditionsController = new CalculateExpeditionsController();

    public CalculateExpeditionsControllerTest(){
        this.entityStore=App.getInstance().getOrganization().getEntityStore();
        this.expeditionStore=App.getInstance().getOrganization().getExpeditionStore();
    }

    @Test
    public void testCalculateExpeditionsOneDay() {
        int day = 1;
        int numberOfProducers = 2;
        calculateExpeditionsController.calculateExpeditions(day, numberOfProducers);
        assertEquals(1, expeditionStore.getExpeditionMap().size());
    }

    @Test
    public void testCalculateExpeditionsFiveDays() {
        int day = 5;
        int numberOfProducers = 2;
        calculateExpeditionsController.calculateExpeditions(day, numberOfProducers);
        assertEquals(5, expeditionStore.getExpeditionMap().size());
    }


}