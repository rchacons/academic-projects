package fr.istic.csr.gare.v2.resources;

import fr.istic.csr.gare.v2.database.InMemoryDataBase;
import fr.istic.csr.gare.v2.internals.Train;
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

public class TrainsResource extends ServerResource {

    private InMemoryDataBase dataBase;


    /**
     * Constructor.
     * Call for every single train request.
     */

    public TrainsResource() {
        super();
        this.dataBase = (InMemoryDataBase) getApplication().getContext().
                getAttributes().get("database");
    }

    @Get("json")
    public Representation getTrain() throws JSException {

        Collection<Train> listTrain = dataBase.getTrain();
        Collection<JSONObject> jsonTrains = new ArrayList<>();

        for (Train train : listTrain) {
            JSONObject current = new JSONObject();
            current.put("id", train.getId_());
            current.put("capacite", train.getCapacite());
            current.put("placeLibre", train.getPlaceLibre());
            current.put("NbrDeVoyageur", train.getNbrVoyageur());
            current.put("vitesse", train.getVitesse());
            jsonTrains.add(current);
        }

        JSONArray jsonArray = new JSONArray(jsonTrains);

        return new JsonRepresentation(jsonArray);

    }

    @Post("json")
    public Representation createTrain(JsonRepresentation representation)
            throws Exception {
        JSONObject object = representation.getJsonObject();
        int capacite = object.getInt("capacite");
        int vitesse = object.getInt("vitesse");

        //Save the train
        Train train = dataBase.createTrain(capacite, vitesse);

        // On lance le train
        train.start();

        //generate result
        JSONObject resultObject = new JSONObject();
        resultObject.put("id",train.getId_());
        resultObject.put("capacite", train.getCapacite());
        resultObject.put("vitesse", train.getVitesse());
        resultObject.put("placeDispo", train.getPlaceLibre());
        JsonRepresentation result = new JsonRepresentation(resultObject);
        return result;

    }

}
