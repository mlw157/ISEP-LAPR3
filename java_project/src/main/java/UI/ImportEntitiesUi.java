package UI;

import Domain.Model.Entity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Ui class to import data from a file (US301)
 * @author Pedro Campos <1211511@isep.ipp.pt>
 */
public class ImportEntitiesUi implements Runnable {

    Controller.ImportEntitiesController controller;
    public ImportEntitiesUi(){
        controller = new Controller.ImportEntitiesController();
    }

    Scanner sc = new Scanner(System.in);

    @Override
    public void run(){
        System.out.println("Import Data");
        System.out.println("Insert file containing the entities to import: "); // file containing entity locations and coordinates
        String file = sc.nextLine();
        //String testFile = "src/main/resources/grafos/Small/clientes-produtores_small.csv";
        //String bigFile = "src/main/resources/grafos/Big/clientes-produtores_big.csv";
        try {
            controller.importEntityInfo(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }


        System.out.println("Insert file containing the connections to import: "); // file containing the distances between locations
        //testFile = "src/main/resources/grafos/Small/distancias_small.csv";
        //bigFile = "src/main/resources/grafos/Big/distancias_big.csv";
        file = sc.nextLine();
        try {
            controller.importEntityConnections(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        System.out.println("Data imported successfully!");
        System.out.println();

    }

}
