package com.example.ecomania.model;

import android.content.Context;
import android.util.Log;

import com.example.ecomania.utils.RestAction;

import java.util.ArrayList;
import java.util.HashMap;

public class Theme {

    //attribut
    private String id;
    private String valeur;
    private String description;
    private int etat;

    //constructor
    public Theme(String valeur, String description) {
        this.valeur = valeur;
        this.description = description;
        this.etat = 1;
    }
    public Theme(){}

    //getter
    public String getId() {
        return id;
    }
    public String getValeur() {
        return valeur;
    }
    public String getDescription() {
        return description;
    }
    public int getEtat() {
        return etat;
    }

    //avoir theme RESTFUL
    /*public ArrayList<HashMap<String, String>> getThemes(Context context){

        ArrayList<HashMap<String,String>> themes = new ArrayList<HashMap<String,String>>();
        HashMap<String,String> map = new HashMap<String,String>();

        RestAction api = new RestAction();
        api.getMethod("https://kidsecolonode.herokuapp.com/theme/all", context);
        //Log.e("res? ", api.res);

        String test = "";

        return themes;
    }*/

}
