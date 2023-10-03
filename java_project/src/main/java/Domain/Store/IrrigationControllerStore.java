package Domain.Store;

import Domain.Model.IrrigationCalendar;
import Domain.Model.IrrigationController;

public class IrrigationControllerStore {

    private IrrigationController irrigationController;
    private IrrigationCalendar irrigationCalendar;

    public IrrigationControllerStore() {
        this.irrigationController = new IrrigationController();
        this.irrigationCalendar = new IrrigationCalendar();
    }

    public IrrigationController getIrrigationController() {
        return irrigationController;
    }

    public void setIrrigationController(IrrigationController irrigationController) {
        this.irrigationController = irrigationController;
    }

    public IrrigationCalendar getIrrigationCalendar() {
        return irrigationCalendar;
    }

    public void setIrrigationCalendar(IrrigationCalendar irrigationCalendar) {
        this.irrigationCalendar = irrigationCalendar;
    }

}
