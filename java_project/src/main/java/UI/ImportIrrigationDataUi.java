package UI;


import Controller.App;
import Controller.ImportIrrigationDataController;
import Domain.Model.IrrigationCalendar;
import Domain.Model.Plot;
import utils.Hour;
import utils.Pair;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Ui class to import data to simulate a irrigation controller (US306)
 * @author Pedro Campos <1211511@isep.ipp.pt>
 */
public class ImportIrrigationDataUi implements Runnable {

    ImportIrrigationDataController controller;

    public ImportIrrigationDataUi() {
        controller = new ImportIrrigationDataController();


    }

    Scanner sc = new Scanner(System.in);


    @Override
    public void run() {
        System.out.println("Import Irrigation Controller Data");
        System.out.println("Insert file containing the irrigation controller data to import: "); // file containing irrigation controller data
        String file = sc.nextLine();
        try {
            controller.importIrrigationController(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        controller.addThirtyDays();
        controller.fillIrrigationCalendar();
        System.out.println("Irrigation Controller imported successfully!");
        System.out.println();



    }
}