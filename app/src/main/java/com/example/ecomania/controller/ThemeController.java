package com.example.ecomania.controller;

import android.content.Context;

import com.example.ecomania.model.Theme;

import java.util.ArrayList;
import java.util.HashMap;

public class ThemeController {

    private static ThemeController instance = null;
    private Theme theme;
    private ArrayList<HashMap<String,String>> themes = new ArrayList<HashMap<String,String>>();

    private ThemeController(){
        super();
    }

    /**
     *
     * @return
     */
    public static final ThemeController getInstance(){
        if(ThemeController.instance == null){
            ThemeController.instance = new ThemeController();
        }
        return instance;
    }

    public ArrayList<HashMap<String, String>> makeThemeTest(){


        HashMap<String,String> map = new HashMap<String,String>();
        themes = new ArrayList<HashMap<String, String>>();

        map.put("theme", "ecologie");
        map.put("description", "test ecologie");
        themes.add(map);

        HashMap<String,String> map2 = new HashMap<String,String>();
        map2.put("theme", "tri");
        map2.put("description", "test tri");
        themes.add(map2);

        HashMap<String,String> map3 = new HashMap<String,String>();
        map3.put("theme", "eco geste");
        map3.put("description", "test eco geste");
        themes.add(map3);

        return themes;
    }

    public ArrayList<HashMap<String, String>> getThemeTest(){
        return themes;
    }

}
