package com.codepath.apps.simpletwitterclient.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vincetulit on 2/17/15.
 */
@Table(name= "user")
public class User extends Model {
    // Attributes
    @Column(name= "name")
    private String name;

    @Column(name= "uid")
    private long uid;

    @Column(name= "screenname")
    private String screenName;

    @Column(name= "profileImageUrl")
    private String profileImageUrl;
    // Deserialize the user json =>user
    public static User fromJSON(JSONObject json) {
        User u = new User();
        // Extract and full the values
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public User() {
        super();
    }
}
