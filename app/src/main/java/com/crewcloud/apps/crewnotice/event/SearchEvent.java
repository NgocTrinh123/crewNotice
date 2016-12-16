package com.crewcloud.apps.crewnotice.event;

/**
 * Created by tunglam on 12/16/16.
 */

public class SearchEvent {
    private int id;
    private String search;

    public SearchEvent(int id, String search) {
        this.id = id;
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
