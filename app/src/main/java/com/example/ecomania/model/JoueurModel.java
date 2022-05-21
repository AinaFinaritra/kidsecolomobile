package com.example.ecomania.model;

public class JoueurModel {
    private String id;
    private String nom;
    private String prenoms;
    private String pseudo;
    private String mdp;
    private int etat;

    public JoueurModel(String id, String nom, String prenoms, String pseudo, String mdp, int etat) {
        this.id = id;
        this.nom = nom;
        this.prenoms = prenoms;
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.etat = etat;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
