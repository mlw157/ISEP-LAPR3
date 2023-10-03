package Domain.Model;

import utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class Expedition {

    private int dayOffset;
    private List<Pair<Entity, List<Delivery>>> dayDeliveries = new ArrayList<>();

    public void addDelivery(Delivery delivery, Entity entity) {
        if (dayDeliveries.isEmpty()) {
            List<Delivery> deliveryList = new ArrayList<>();
            deliveryList.add(delivery);
            dayDeliveries.add(new Pair<>(entity, deliveryList));
        } else {
            for (Pair<Entity, List<Delivery>> pair : dayDeliveries) {
                if (pair.first().equals(entity)) {
                    pair.second().add(delivery);
                    return;
                }
            }
            List<Delivery> deliveryList = new ArrayList<>();
            deliveryList.add(delivery);
            dayDeliveries.add(new Pair<>(entity, deliveryList));
        }
    }
    public Expedition(int dayOffset) {
        this.dayOffset = dayOffset;
    }

    public int getDayOffset() {
        return dayOffset;
    }

    public List<Pair<Entity, List<Delivery>>> getDayDeliveries() {
        return dayDeliveries;
    }

    public void setDayOffset(int dayOffset) {
        this.dayOffset = dayOffset;
    }

}
