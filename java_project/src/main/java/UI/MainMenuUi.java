package UI;

import java.util.Scanner;

public class MainMenuUi {
    public static void main(String[] args)  {
        Scanner sc = new Scanner(System.in);
        int option;
        do {
            System.out.println("Main Menu");
            System.out.println("1. Import Entity Data");
            System.out.println("2. Define Distribution Hubs");
            System.out.println("3. Find Nearest Distribution Hub for each client");
            System.out.println("4. Verify if graph is connected");
            System.out.println("5. Shortest network between clients and producers");
            System.out.println("6. Open Irrigation options");
            System.out.println("7. Import Baskets");
            System.out.println("8. Generate Expedition List for a given day without restrictions");
            System.out.println("9. Generate Expedition List for a given day with restrictions");
            System.out.println("10. See expedition stats");
            System.out.println("0. Exit");
            do {
                option = sc.nextInt();
            } while (option < 0 || option > 10);
            switch (option) {
                case 1:
                    new ImportEntitiesUi().run();
                    break;
                case 2:
                    new DefineHubsUi().run();
                    break;
                case 3:
                    new FindNearestHubUi().run();
                    break;
                case 4:
                    new VerifyConnectivityUi().run();
                    break;
                case 5:
                    new MinimumNetworkUi().run();
                    break;
                case 6:
                    new IrrigationUi().run();
                    break;
                case 7:
                    new ImportBasketsUi().run();
                    break;
                case 8:
                    new GenerateExpeditionsWithoutRestrictionsUi().run();
                    break;
                case 9:
                    new GenerateExpeditionsWithRestrictionsUi().run();
                    break;
                case 10:
                    new ExpeditionStatsUi().run();
                    break;
            }
        } while (option != 0);
}

}
