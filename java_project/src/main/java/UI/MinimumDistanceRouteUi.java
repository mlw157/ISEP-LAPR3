package UI;

import Controller.App;
import Controller.MinimumDistanceRouteController;
import Domain.Model.Company;
import Domain.Model.Entity;
import Domain.Model.Expedition;
import Domain.Model.Productor;
import Domain.Store.EntityStore;
import Domain.Store.ExpeditionStore;

import java.util.List;
import java.util.Scanner;
/**
 * UI class to obtain minimum distance route for a daily expedition list (US310)
 * @author Diogo Costa <1211514@isep.ipp.pt>
 */
public class MinimumDistanceRouteUi implements Runnable {

    MinimumDistanceRouteController minimumDistanceRouteController;
    Expedition expedition;



    ExpeditionStore expeditionStore;

    Scanner sc = new Scanner(System.in);

    public MinimumDistanceRouteUi(Expedition expedition) {
        minimumDistanceRouteController = new MinimumDistanceRouteController();
        this.expeditionStore = App.getInstance().getOrganization().getExpeditionStore();
        this.expedition = expedition;

    }
    /**
     * Method that interacts with the user
     */
    @Override
    public void run() {
        System.out.println("Minimum Distance Route");
        System.out.println();

        minimumDistanceRouteController.calculateMinimumDistanceRoute(expedition);
    }
}

