package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.database.InMemoryDatabase;
import org.inria.restlet.mta.internals.User;
import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;

import org.restlet.resource.*;

import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * Resource exposing a user.
 *
 * @author msimonin
 * @author ctedeschi
 *
 */
public class UserResource extends ServerResource
{

    /** database. */
    private InMemoryDatabase db_;

    /** Utilisateur géré par cette resource. */
    private User user_;

    /**
     * Constructor.
     * Call for every single user request.
     */
    public UserResource()
    {
        db_ = (InMemoryDatabase) getApplication().getContext().getAttributes()
                .get("database");
    }


    @Get("json")
    public Representation getUser() throws Exception
    {
        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.valueOf(userIdString);
        user_ = db_.getUser(userId);

        JSONObject userObject = new JSONObject();
        userObject.put("name", user_.getName());
        userObject.put("age", user_.getAge());
        userObject.put("id", user_.getId());

        return new JsonRepresentation(userObject);
    }

//    @Delete()
//    public void deleteUser() throws Exception
//    {
//        String userIdString = (String) getRequest().getAttributes().get("userId");
//        int userId = Integer.valueOf(userIdString);
//        db_.deleteUser(userId);
//
//    }


    @Delete("json")
    public Representation DeleteUserWithId(){
        try {
            String userIdString = (String) getRequest().getAttributes().get("userId");
            int userId = Integer.valueOf(userIdString);
            db_.deleteUser(userId);
        } catch (Exception exception) {
            return new StringRepresentation("ERROR: " + exception.getMessage(), MediaType.TEXT_PLAIN);
        }
        return new StringRepresentation("OK!", MediaType.TEXT_PLAIN);
    }


}
