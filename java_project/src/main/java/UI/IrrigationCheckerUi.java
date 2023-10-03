package UI;

import Controller.App;
import Controller.IrrigationCheckerController;
import Domain.Model.Plot;
import utils.Hour;
import utils.Pair;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class IrrigationCheckerUi implements Runnable {

    IrrigationCheckerController irrigationCheckerController;
    public IrrigationCheckerUi(){
        irrigationCheckerController = new IrrigationCheckerController();
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        int option;
        Date date = new Date();
        Hour hour = new Hour();
        System.out.println("Irrigation Checker");
        System.out.println();

        System.out.println("1. Check current date and time");
        System.out.println("2. Specify date and time");
        System.out.println("0. Exit");

            do {
                option = sc.nextInt();
            } while (option < 0 || option > 2);
            switch (option) {
                case 1:
                    date = Date.from(java.time.Instant.now());
                    hour = Hour.currentTime();
                    break;
                case 2:
                    System.out.println("Insert day: ");
                    int day = sc.nextInt();
                    System.out.println("Insert month: ");
                    int month = sc.nextInt();
                    System.out.println("Insert year: ");
                    int year = sc.nextInt();
                    System.out.println("Insert hour: ");
                    int hourValue = sc.nextInt();
                    System.out.println("Insert minutes: ");
                    int minutes = sc.nextInt();
                    date = new Date(year-1900, month-1, day, hourValue, minutes);
                    hour = new Hour(hourValue, minutes);
                    break;

            }
        System.out.println();



        try {
            Pair<Boolean, Pair<Plot, Integer>> irrigatedPlot = irrigationCheckerController.isAnyPlotGettingIrrigated(date, hour);

            if (!irrigatedPlot.first()){
                System.out.println("No plots are being irrigated");
            } else {

                System.out.println("Plot " + irrigatedPlot.second().first().designation + " is currently being irrigated" + " with " + irrigatedPlot.second().second() + " minutes left.");
            }
            System.out.println();

        } finally {
            System.out.println("Irrigation Checker finished");
        }
    }
}

