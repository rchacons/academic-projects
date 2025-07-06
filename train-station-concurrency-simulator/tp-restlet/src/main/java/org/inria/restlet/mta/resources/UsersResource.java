package org.inria.restlet.mta.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.Iterator;


import org.inria.restlet.mta.database.InMemoryDatabase;
import org.inria.restlet.mta.internals.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 * Resource exposing the users
 *
 * @author ctedeschi
 * @author msimonin
 *
 */
public class UsersResource extends ServerResource {

    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    /** Database. */

    private InMemoryDatabase db_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public UsersResource() {
        super();
        db_ = (InMemoryDatabase) getApplication().getContext().getAttributes()
                .get("database");
    }

    /**
     * Returns the list of all the users
     *
     * @return JSON representation of the users
     * @throws JSONException
     */

    @Get("json")
    public Representation getUsers() throws JSONException {
        Collection<User> users = db_.getUsers();
        Collection<JSONObject> jsonUsers = new ArrayList<JSONObject>();

        for (User user : users) {
            JSONObject current = new JSONObject();
            current.put("id", user.getId());
            current.put("name", user.getName());
            current.put("url", getReference() + "/" + user.getId());
            jsonUsers.add(current);

        }
        JSONArray jsonArray = new JSONArray(jsonUsers);
        return new JsonRepresentation(jsonArray);
    }

//    @Get("json")
//    public Representation getUsersById(int id) throws JSONException {
//        Collection<User> usersCollection = db_.getUsers();
//
//        Iterator<User> iterator = usersCollection.iterator();
//
//        JSONObject userObject = new JSONObject();
//
//        while (iterator.hasNext()) {
//            if (iterator.next().getId() == id) {
//                User userWithId = iterator.next();
//                userObject.put("name", userWithId.getName());
//                userObject.put("age", userWithId.getAge());
//                userObject.put("id", userWithId.getId());
//            }
//        }
//
//        return new JsonRepresentation(userObject);
//    }


    @Post("json")
    public Representation createUser(JsonRepresentation representation)
            throws Exception {
        JSONObject object = representation.getJsonObject();
        String name = object.getString("name");
        int age = object.getInt("age");

        // Save the user
        User user = db_.createUser(name, age);

        // generate result
        JSONObject resultObject = new JSONObject();
        resultObject.put("name", user.getName());
        resultObject.put("age", user.getAge());
        resultObject.put("id", user.getId());
        JsonRepresentation result = new JsonRepresentation(resultObject);
        return result;
    }



}
