package Controller;

import Domain.Model.Plot;
import Domain.Store.IrrigationControllerStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Pair;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class IrrigationCheckerControllerTest {

    IrrigationControllerStore irrigationControllerStore = App.getInstance().getOrganization().getIrrigationControllerStore();

    @Test
    void isPlotGettingIrrigatedNotTest() {
    IrrigationCheckerController controller = new IrrigationCheckerController();
    ImportIrrigationDataController importController = new ImportIrrigationDataController();
        String file = "src/test/resources/IrrigationData/irrigation_data_false_hours";
        try {
            importController.importIrrigationController(file);
        } catch (Exception e) {
            fail("Exception not expected");
        }
        Set<Plot> plots = irrigationControllerStore.getIrrigationController().getIrrigationValuesMap().keySet();
        for (Plot plot : plots) {
            assertFalse(controller.isPlotGettingIrrigated(plot).first());

        }
    }

    @Test
    void isPlotGettingIrrigatedYesTest() {
        IrrigationCheckerController controller = new IrrigationCheckerController();
        ImportIrrigationDataController importController = new ImportIrrigationDataController();
        String file = "src/test/resources/IrrigationData/irrigation_data_always_irrigated";
        try {
            importController.importIrrigationController(file);
        } catch (Exception e) {
            fail("Exception not expected");
        }
        Set<Plot> plots = irrigationControllerStore.getIrrigationController().getIrrigationValuesMap().keySet();
        for (Plot plot : plots) {
            assertTrue(controller.isPlotGettingIrrigated(plot).first());
        }
    }

    @Test
    void getIrrigationTimeLeft() {
        IrrigationCheckerController controller = new IrrigationCheckerController();
        ImportIrrigationDataController importController = new ImportIrrigationDataController();
        String file = "src/test/resources/IrrigationData/irrigation_data_always_irrigated";
        try {
            importController.importIrrigationController(file);
        } catch (Exception e) {
            fail("Exception not expected");
        }
        Set<Plot> plots = irrigationControllerStore.getIrrigationController().getIrrigationValuesMap().keySet();
        for (Plot plot : plots) {
            boolean timeLeftTrue = controller.getIrrigationTimeLeft(plot) >= 0;
            assertTrue(timeLeftTrue);
        }
    }

    @Test
    void irrigatedPlots() {
        IrrigationCheckerController controller = new IrrigationCheckerController();
        ImportIrrigationDataController importController = new ImportIrrigationDataController();
        String file = "src/test/resources/IrrigationData/irrigation_data_always_irrigated";
        try {
            importController.importIrrigationController(file);
        } catch (Exception e) {
            fail("Exception not expected");
        }

        List<Pair<Plot, Integer>> irrigatedPlots = controller.irrigatedPlots();
        assert(irrigatedPlots.size() == 2);
    }

}