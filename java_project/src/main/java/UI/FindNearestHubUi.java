package UI;

import Controller.FindNearestHubController;
import Domain.Model.Company;
import Domain.Model.Entity;
import utils.Pair;

import java.util.List;
import java.util.Scanner;

/**
 * Ui class to find the nearest distribution Hub for each client (US304)
 * @author Pedro Campos <1211511@isep.ipp.pt>
 */

public class FindNearestHubUi implements Runnable {

    FindNearestHubController controller;

    public FindNearestHubUi(){
        controller = new FindNearestHubController();
    }

    Scanner sc = new Scanner(System.in);

    @Override
    public void run() {

        List<Pair<Entity, Pair<Company, Integer>>> clientNearestList = controller.getNearestDistributionHubForEachClient();

        System.out.println("Nearest Distribution Hub for each client: ");
        for (Pair<Entity, Pair<Company, Integer>> pair : clientNearestList) {
            System.out.println("Nearest Distribution Hub to " + pair.first().getName() + " is " + pair.second().first().getName() + " with a distance of " + pair.second().second() + " meters");
        }

        System.out.println();

    }
}



