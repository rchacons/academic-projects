package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.database.InMemoryDatabase;
import org.inria.restlet.mta.internals.Tweet;
import org.inria.restlet.mta.internals.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class TweetsResource extends ServerResource {

    Logger log = Logger.getLogger(this.getClass().getName());
    private InMemoryDatabase db_;

    public TweetsResource()
    {
        super();
        db_ = (InMemoryDatabase) getApplication().getContext().getAttributes()
                .get("database");
    }

    @Get("json")
    public Representation getTweets() throws Exception {


        Collection<User> users = db_.getUsers();

        Collection<JSONObject> jsonUsers = new ArrayList<JSONObject>();

        for (User user : users) {
            JSONObject current = new JSONObject();
            current.put("id", user.getId());
            current.put("name", user.getName());

            Collection<JSONObject> jsonTweets = new ArrayList<JSONObject>();

            for (Tweet tweet : user.getTweets()) {
                JSONObject currentTweet = new JSONObject();
                currentTweet.put("id", tweet.getId());
                currentTweet.put("tweet", tweet.getTweetText());
                jsonTweets.add(currentTweet);
            }
            current.put("tweets", jsonTweets);
            jsonUsers.add(current);
        }
        JSONArray jsonArray = new JSONArray(jsonUsers);
        return new JsonRepresentation(jsonArray);
    }

}
