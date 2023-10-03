package UI;

import Controller.VerifyConnectivityController;

import java.util.Scanner;
/**
 * UI class to Verify Connectivity (US302)
 * @author Diogo Costa <1211514@isep.ipp.pt>
 */
public class VerifyConnectivityUi implements Runnable{

    VerifyConnectivityController verifyConnectivityController;

    public VerifyConnectivityUi(){
        verifyConnectivityController=new VerifyConnectivityController();
    }

    Scanner sc = new Scanner(System.in);

    /**
     * Method that interacts with the user
     */
    @Override
    public void run() {
        if(verifyConnectivityController.isConnected()){
            System.out.println("Graph is connected!");

        }else{
            System.out.println("Graph is not connected!");
        }
        System.out.printf("The minimum number of connections necessary to connect any client/producer to another in this network is %d \n", verifyConnectivityController.minimumNumberOfConnections());
        System.out.println();
    }
}
