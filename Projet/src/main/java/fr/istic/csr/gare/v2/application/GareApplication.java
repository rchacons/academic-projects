package fr.istic.csr.gare.v2.application;

import fr.istic.csr.gare.v2.resources.TrainsResource;
import fr.istic.csr.gare.v2.resources.VoyageursResource;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class GareApplication extends Application {

    public GareApplication(Context context) {
        super(context);
    }

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());

        //GET: List the train, POST: Add the new train to list.
        router.attach("/trains" , TrainsResource.class);

        //GET: List the traveller, POST: Add new traveller
        router.attach("/voyageurs", VoyageursResource.class);

        return router;
    }
}


