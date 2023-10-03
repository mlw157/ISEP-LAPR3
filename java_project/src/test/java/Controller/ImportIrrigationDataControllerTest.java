package Controller;

import Domain.Model.Plot;
import Domain.Store.IrrigationControllerStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Hour;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ImportIrrigationDataControllerTest {

    IrrigationControllerStore irrigationControllerStore = App.getInstance().getOrganization().getIrrigationControllerStore();

    @Test
    void importIrrigationControllerHoursSizeTest() {
        ImportIrrigationDataController controller = new ImportIrrigationDataController();
        String file = "src/test/resources/IrrigationData/irrigation_data_normal";
        try {
            controller.importIrrigationController(file);
        } catch (Exception e) {
            fail("Exception not expected");
        }
        assertEquals(2, irrigationControllerStore.getIrrigationController().getIrrigationHours().size());
    }

        @Test
        void importIrrigationControllerValuesSizeTest() {
            ImportIrrigationDataController controller = new ImportIrrigationDataController();
            String file = "src/test/resources/IrrigationData/irrigation_data_normal";
            try {
                controller.importIrrigationController(file);
            } catch (Exception e) {
                fail("Exception not expected");
            }
            assertEquals(5, irrigationControllerStore.getIrrigationController().getIrrigationValuesMap().size());
        }

    @Test
    void importIrrigationControllerValuesTest() {
        ImportIrrigationDataController controller = new ImportIrrigationDataController();
        String file = "src/test/resources/IrrigationData/irrigation_data_one_plot";
        try {
            controller.importIrrigationController(file);
        } catch (Exception e) {
            fail("Exception not expected");
        }

        Set<Plot> plots = irrigationControllerStore.getIrrigationController().getIrrigationValuesMap().keySet();
        for (Plot plot : plots) {
            assertEquals(10, irrigationControllerStore.getIrrigationController().getIrrigationValuesMap().get(plot).first());
            assertEquals("t", irrigationControllerStore.getIrrigationController().getIrrigationValuesMap().get(plot).second());
        }

    }

    @Test
    void importIrrigationControllerHoursTest() {
        ImportIrrigationDataController controller = new ImportIrrigationDataController();
        String file = "src/test/resources/IrrigationData/irrigation_data_normal";
        try {
            controller.importIrrigationController(file);
        } catch (Exception e) {
            fail("Exception not expected");
        }

        assertEquals(new Hour(8,30), irrigationControllerStore.getIrrigationController().getIrrigationHours().get(0));
        assertEquals(new Hour(17, 00), irrigationControllerStore.getIrrigationController().getIrrigationHours().get(1));
    }
}
