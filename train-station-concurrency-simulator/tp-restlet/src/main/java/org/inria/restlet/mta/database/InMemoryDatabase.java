package org.inria.restlet.mta.database;

import java.util.*;

import org.inria.restlet.mta.internals.User;

/**
 *
 * In-memory database
 *
 * @author ctedeschi
 * @author msimonin
 *
 */
public class InMemoryDatabase
{

    /** User count (next id to give).*/
    private int userCount_;

    /** User Hashmap. */
    Map<Integer, User> users_;


    public InMemoryDatabase()
    {
        users_ = new HashMap<Integer, User>();
    }

    /**
     *
     * Synchronized user creation.
     * @param name
     * @param age
     *
     * @return the user created
     */
    public synchronized User createUser(String name, int age)
    {
        User user = new User(name, age);
        user.setId(userCount_);
        users_.put(userCount_, user);
        userCount_ ++;
        return user;
    }

    public synchronized void deleteUser(int id) {
        if(id < users_.size())
       users_.remove(id);
    }


    public Collection<User> getUsers()
    {
        return users_.values();
    }

    public User getUser(int id)
    {
        return users_.get(id);
    }



}
