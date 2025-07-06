package org.inria.restlet.mta.resources;

import org.inria.restlet.mta.database.InMemoryDatabase;
import org.inria.restlet.mta.internals.Tweet;
import org.inria.restlet.mta.internals.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class TweetResource extends ServerResource {

    Logger log = Logger.getLogger(this.getClass().getName());
    private User user_;
    private InMemoryDatabase db_;

    public TweetResource()
    {
        super();
        db_ = (InMemoryDatabase) getApplication().getContext().getAttributes()
                .get("database");
    }


    @Get("json")
    public Representation getTweets() throws Exception
    {
        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.valueOf(userIdString);
        user_ = db_.getUser(userId);

        Collection<JSONObject> jsonTweets = new ArrayList<JSONObject>();


        for(Tweet tweet : user_.getTweets()) {
            JSONObject current = new JSONObject();
            current.put("id", tweet.getId());
            current.put("tweet", tweet.getTweetText());
            jsonTweets.add(current);

        }
        JSONArray jsonArray = new JSONArray(jsonTweets);


        return new JsonRepresentation(jsonArray);
    }

    @Post("json")
    public Representation addTweet(JsonRepresentation representation) throws Exception
    {
        JSONObject object = representation.getJsonObject();

        String tweetText = object.getString("tweet");

        String userIdString = (String) getRequest().getAttributes().get("userId");
        int userId = Integer.valueOf(userIdString);
        user_ = db_.getUser(userId);

        Tweet newTweet = new Tweet(tweetText);
        user_.addTweet(newTweet);

        // generate result
        JSONObject resultObject = new JSONObject();
        resultObject.put("id", newTweet.getId());
        resultObject.put("tweet", newTweet.getTweetText());

        JsonRepresentation result = new JsonRepresentation(resultObject);

        return result;
    }


}
