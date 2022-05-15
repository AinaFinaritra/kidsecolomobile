package com.example.ecomania.model;

import java.util.ArrayList;
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
    public DetailsTheme(String idTheme, String titre, String description, String image, String video) {
        this.idTheme = idTheme;
        this.titre = titre;
        this.description = description;
        this.image = image;
        this.video = video;
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

    //data static
    public ArrayList<DetailsTheme> getStaticData(){
        ArrayList<DetailsTheme> result = new ArrayList<DetailsTheme>();

        DetailsTheme data1 = new DetailsTheme("1", "transformation ordure", "bla bla bla ", "https://www.e-sakafo.mg/assets/front/images/bg-login.jpg", "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1");
        result.add(data1);
        DetailsTheme data2 = new DetailsTheme("1", "transformation ordure", "bla bla bla ", "https://www.e-sakafo.mg/assets/front/images/bg-login.jpg", "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1");
        result.add(data2);
        DetailsTheme data3 = new DetailsTheme("1", "transformation ordure", "bla bla bla ", "https://www.e-sakafo.mg/assets/front/images/bg-login.jpg", "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1");
        result.add(data3);
        DetailsTheme data4 = new DetailsTheme("1", "transformation ordure", "bla bla bla ", "https://www.e-sakafo.mg/assets/front/images/bg-login.jpg", "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1");
        result.add(data4);

        return result;
    }
}
