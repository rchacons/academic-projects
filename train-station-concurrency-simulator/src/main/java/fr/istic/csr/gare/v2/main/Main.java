
package fr.istic.csr.gare.v2.main;

import fr.istic.csr.gare.v2.application.GareApplication;
import fr.istic.csr.gare.v2.database.InMemoryDataBase;
import fr.istic.csr.gare.v2.internals.Gare;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;

public class Main {

    /**
     *Main method
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // Create a component
        Component component = new Component();
        Context context = component.getContext().createChildContext();
        component.getServers().add(Protocol.HTTP, 8124);

        // Create an application
        Application application = new GareApplication(context);

        // Add the db into component's context
        InMemoryDataBase db = new InMemoryDataBase ();
        db.createTrain(99, 200);
        context.getAttributes().put("database", db);
        component.getDefaultHost().attach(application);

        // Start the component
        component.start();

        Gare gare = new Gare(db);
   }

}