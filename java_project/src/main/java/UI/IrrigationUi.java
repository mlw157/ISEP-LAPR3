package UI;

import Controller.App;

import java.util.Scanner;

public class IrrigationUi implements Runnable {

    @Override
    public void run() {


        Scanner sc = new Scanner(System.in);
        int option;
        do {
            System.out.println("Irrigation Options");
            System.out.println();
            System.out.println("1. Import Irrigation Data");
            System.out.println("2. Show Irrigation Calendar");
            System.out.println("3. Check plots getting irrigated");
            System.out.println("0. Exit");

            do {
                option = sc.nextInt();
            } while (option < 0 || option > 3);
            switch (option) {
                case 1:
                    new UI.ImportIrrigationDataUi().run();
                    break;
                case 2:
                    if (App.getInstance().getOrganization().getIrrigationControllerStore().getIrrigationController() == null) {
                        System.out.println("No irrigation controller imported yet!");
                        System.out.println();
                    } else {
                        new IrrigationCalendarUi().run();
                    }
                    break;
                case 3:
                    if (App.getInstance().getOrganization().getIrrigationControllerStore().getIrrigationController() == null) {
                        System.out.println("No irrigation controller imported yet!");
                        System.out.println();
                    } else {
                        new UI.IrrigationCheckerUi().run();
                    }
                    break;
            }
        } while (option != 0);
        System.out.println();

    }
}

