import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import rest.PersonResource;
import rest.SwaggerResource;
import rest.TicketResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {

        final Set<Class<?>> resources = new HashSet<>();

        resources.add(OpenApiResource.class);
        resources.add(TicketResource.class);
        resources.add(PersonResource.class);
        resources.add(SwaggerResource.class);

        return resources;
    }
}
