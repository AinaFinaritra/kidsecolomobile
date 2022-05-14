package com.example.ecomania.model;

import java.util.IdentityHashMap;

public class DetailsTheme {

    // attribut
    private String id;
    private String idTheme;
    private String titre;
    private String description;
    private String image;
    private String video;

    //constructor
    public DetailsTheme() {
    }

    //getter
    public String getId() {
        return id;
    }

    public String getIdTheme() {
        return idTheme;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getVideo() {
        return video;
    }
}
