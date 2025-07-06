package org.inria.restlet.mta.internals;

public class Tweet {

    private int id;

    private String tweetText;

    public Tweet(String tweetText){
        this.tweetText = tweetText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

}
