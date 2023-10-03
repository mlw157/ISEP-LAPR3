package Controller;

import Domain.Model.Organization;

public class App {

    /**
     * @author Pedro Campos <1211511@isep.ipp.pt>
     */
    private Organization organization;

    private App() {
        this.organization = new Organization();
        bootstrap();

    }

    public Organization getOrganization() {
        return organization;
    }

    private void bootstrap() {

    }

    // Extracted from https://www.javaworld.com/article/2073352/core-java/core-java-simply-singleton.html?page=2
    private static App singleton = null;
    public static App getInstance()
    {
        if(singleton == null)
        {
            synchronized(App.class)
            {
                singleton = new App();
            }
        }
        return singleton;
    }

}
