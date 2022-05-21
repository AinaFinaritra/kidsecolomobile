package com.example.ecomania.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionResponse {
    private static QuestionResponse instance = null;
    public ArrayList<HashMap<String, String>> question;
    public ArrayList<ArrayList<HashMap<String, String>>> choices;
    public ArrayList<HashMap<String, String>> correctAnswers;

    private QuestionResponse() { super(); }

    public static final QuestionResponse getInstance() {
        if(QuestionResponse.instance == null){
            QuestionResponse.instance = new QuestionResponse();
        }
        return instance;
    }

    public void configureQuestionReponse(JSONArray jsonArray){
        question = new ArrayList<HashMap<String, String>>();
        choices = new ArrayList<ArrayList<HashMap<String, String>>>();
        correctAnswers = new ArrayList<HashMap<String, String>>();

        for(int i = 0; i < jsonArray.length(); i++){
            try {
                JSONObject item = jsonArray.getJSONObject(i);

                //one question
                HashMap<String, String> one_question = new HashMap<String, String>();
                one_question.put("idTheme", item.getString("idtheme"));
                one_question.put("question", item.getString("question"));
                one_question.put("idNiveau", item.getString("idniveau"));
                question.add(one_question);

                //declaring multiple response for an question
                ArrayList<HashMap<String, String>> lst_response = new ArrayList<HashMap<String, String>>();
                JSONArray responses = item.getJSONArray("reponses");
                for(int a = 0; a < responses.length(); a++){
                    HashMap<String, String> one_response = new HashMap<String, String>();
                    one_response.put("idreponse", responses.getJSONObject(a).getString(("id")));
                    one_response.put("idquestion", responses.getJSONObject(a).getString(("idquestion")));
                    one_response.put("reponse", responses.getJSONObject(a).getString("libelle"));
                    one_response.put("score", ""+responses.getJSONObject(a).getInt("pts"));
                    lst_response.add(one_response);

                    //stocker la reponse correct
                    if(responses.getJSONObject(a).getInt("pts") != 0){
                        correctAnswers.add(one_response);
                    }
                }
                choices.add(lst_response);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void renitialize(){
        question = new ArrayList<HashMap<String, String>>();
        choices = new ArrayList<ArrayList<HashMap<String, String>>>();
        correctAnswers = new ArrayList<HashMap<String, String>>();
    }

}
