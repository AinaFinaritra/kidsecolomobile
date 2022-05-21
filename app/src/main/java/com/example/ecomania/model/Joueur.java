package com.example.ecomania.model;

import java.util.ArrayList;

public class Joueur {
    private String status;
    private boolean error;
    private ArrayList<JoueurModel> data;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }
}
