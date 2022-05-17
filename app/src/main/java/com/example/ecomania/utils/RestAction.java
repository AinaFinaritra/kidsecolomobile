package com.example.ecomania.utils;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class RestAction {

    private JSONObject result;

    private void setResult(JSONObject result){
        this.result = result;
    }

    //constructor
    public RestAction(){}

    //getMethod
    public JSONObject getMethod(String url, Context context, final VolleyCallBack callBack){
        //attribute

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("error restapi get", response.toString());
                        callBack.onSuccess(response);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error restapi get", error.toString());
                    }
                }
        );
        requestQueue.add(objectRequest);
        return result;
    }

}
