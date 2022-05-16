package com.example.ecomania.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Scorepartheme {

    ArrayList<HashMap<String, String>> score;

    //atribut
    private String valeur;
    private String idjoueur;
    private int pts;

    //constructor
    public Scorepartheme(){}
    public Scorepartheme(String valeur, String idjoueur, int pts){
        this.valeur = valeur;
        this.idjoueur = idjoueur;
        this.pts = pts;
    }

    //static data
    public ArrayList<HashMap<String, String>> getScore(){
        this.score = new ArrayList<HashMap<String, String>>();

        HashMap<String,String> map = new HashMap<String,String>();
        map.put("valeur", "ecologie");
        map.put("pts", "12");
        score.add(map);

        HashMap<String,String> map2 = new HashMap<String,String>();
        map2.put("valeur", "tri");
        map2.put("pts", "20");
        score.add(map2);

        HashMap<String,String> map3 = new HashMap<String,String>();
        map3.put("valeur", "eco geste");
        map3.put("pts", "40");
        score.add(map3);

        return score;
    }

}
