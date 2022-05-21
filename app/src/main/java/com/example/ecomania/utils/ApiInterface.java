package com.example.ecomania.utils;

import com.example.ecomania.model.Joueur;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("https://kidsecolonode.herokuapp.com/joueur/login")
    Call<Joueur> loginJoueur(@Field("pseudo") String pseudo, @Field("mdp") String mdp);

    @FormUrlEncoded
    @POST("https://kidsecolonode.herokuapp.com/joueur/inscription")
    Call<Joueur> inscription(@Field("nom") String nom, @Field("prenoms") String prenoms, @Field("pseudo") String pseudo, @Field("mdp") String mdp);
}
