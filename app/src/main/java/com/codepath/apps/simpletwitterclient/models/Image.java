package com.codepath.apps.simpletwitterclient.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vincetulit on 2/22/15.
 */
public class Image {
    private int width;
    private int height;
    private String url;

    public static ArrayList<Image> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Image> image = new ArrayList<>();

        for(int i=0; i<jsonArray.length(); i++)
            try {
                JSONObject imgJSON = jsonArray.getJSONObject(i);
                Image img = new Image();
                img.setUrl(imgJSON.getString("media_url"));
                img.setWidth(imgJSON.getJSONObject("sizes").getJSONObject("large").getInt("w"));
                img.setHeight(imgJSON.getJSONObject("sizes").getJSONObject("large").getInt("h"));
                image.add(img);
            } catch (JSONException e) {

            }
        return image;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getUrl() {
        return url;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
