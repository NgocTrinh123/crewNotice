package com.crewcloud.apps.crewnotice.data;

/**
 * Created by tunglam on 12/16/16.
 */

public class Photo {
    private String id;

    private String url;

    private String name;

    public Photo() {
    }

    public Photo(String id, String url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
