package com.example.ecomania.controller;

import com.example.ecomania.model.DetailsTheme;

import java.util.ArrayList;

public class DetailsThemeController {

    private static DetailsThemeController instance = null;
    private DetailsTheme detailTheme;

    private DetailsThemeController(){
        super();
    }

    /**
     *
     * @return
     */
    public static final DetailsThemeController getInstance(){
        if(DetailsThemeController.instance == null){
            DetailsThemeController.instance = new DetailsThemeController();
        }
        return instance;
    }

    public ArrayList<DetailsTheme> getDetail_themes(){
        detailTheme = new DetailsTheme();
        return detailTheme.getStaticData();
    }

}
