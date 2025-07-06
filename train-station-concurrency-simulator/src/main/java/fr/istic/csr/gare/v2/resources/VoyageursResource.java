package fr.istic.csr.gare.v2.resources;

import fr.istic.csr.gare.v2.database.InMemoryDataBase;
import fr.istic.csr.gare.v2.internals.Train;
import fr.istic.csr.gare.v2.internals.Voyageur;
import netscape.javascript.JSException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.Collection;

public class VoyageursResource extends ServerResource {


    InMemoryDataBase dataBase;

    public VoyageursResource() {
        this.dataBase = (InMemoryDataBase) getApplication().getContext().
                getAttributes().get("database");
    }


    @Get("json")
    public Representation getVoyageur() throws JSException {

        Collection<Voyageur> listVoyageur = dataBase.getVoyageurs();
        Collection<JSONObject> jsonTrains = new ArrayList<>();

        for (Voyageur voyageur : listVoyageur) {
            JSONObject current = new JSONObject();
            current.put("name: ", voyageur.getNameVoyageur());
            jsonTrains.add(current);
        }
        JSONArray jsonArray = new JSONArray(jsonTrains);

        return new JsonRepresentation(jsonArray);

    }


    @Post("json")
    public Representation createVoyageurs(JsonRepresentation representation)
            throws Exception {
        JSONObject object = representation.getJsonObject();
        String name = object.getString("name");

        // Save the traveller
        Voyageur voyageur = dataBase.createVoyageur(name);
        voyageur.start();

        // generate result
        JSONObject resultObject = new JSONObject();
        resultObject.put("name", voyageur.getNameVoyageur());
        JsonRepresentation result = new JsonRepresentation(resultObject);
        return result;
    }
}
