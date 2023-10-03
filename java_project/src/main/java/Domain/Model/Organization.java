package Domain.Model;

import Domain.Store.EntityStore;
import Domain.Store.ExpeditionStore;
import Domain.Store.IrrigationControllerStore;

/**
 * Class to represent the organization of the system
 * @author Pedro Campos <1211511@isep.ipp.pt>
 */
public class Organization {

    private final EntityStore entityStore;
    private final ExpeditionStore expeditionStore;

    private final IrrigationControllerStore irrigationControllerStore;
    public Organization() {
        this.entityStore = new EntityStore();
        this.irrigationControllerStore = new IrrigationControllerStore();
        this.expeditionStore = new ExpeditionStore();
    }



    public EntityStore getEntityStore() {
        return entityStore;
    }

    public ExpeditionStore getExpeditionStore() {
        return expeditionStore;
    }


    public IrrigationControllerStore getIrrigationControllerStore() {
        return irrigationControllerStore;
    }
}
