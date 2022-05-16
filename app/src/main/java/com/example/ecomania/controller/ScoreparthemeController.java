package com.example.ecomania.controller;

import com.example.ecomania.model.Scorepartheme;

import java.util.ArrayList;
import java.util.HashMap;

public class ScoreparthemeController {
    private static ScoreparthemeController instance = null;
    private Scorepartheme scorepartheme;
    private ArrayList<HashMap<String,String>> score = new ArrayList<HashMap<String,String>>();

    private ScoreparthemeController() { super(); }

    public static final ScoreparthemeController getInstance() {
        if(ScoreparthemeController.instance == null){
            ScoreparthemeController.instance = new ScoreparthemeController();
        }
        return instance;
    }

    public ArrayList<HashMap<String, String>> getScorepartheme(){
        this.scorepartheme = new Scorepartheme();
        this.score = this.scorepartheme.getScore();
        return this.score;
    }

}
