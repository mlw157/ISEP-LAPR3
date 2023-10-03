package UI;


import Controller.DefineHubsController;

import java.util.Scanner;

/**
 * Ui class to define distribution Hubs (US303)
 * @author Pedro Campos <1211511@isep.ipp.pt>
 * @author Pedro Lopes <1211504@isep.ipp.pt>
 */
public class DefineHubsUi implements Runnable {

    DefineHubsController controller;

    public DefineHubsUi() {
        controller = new DefineHubsController();

    }

    Scanner sc = new Scanner(System.in);


    @Override
    public void run() {

        System.out.println("Define Hubs");
        System.out.println("Please enter the number of hubs you want to define: ");
        int numberOfHubs = sc.nextInt();
        controller.defineHubs(numberOfHubs);
        System.out.println("Hubs defined successfully!");
        System.out.println();
    }

}
