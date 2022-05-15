package com.example.ecomania.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * usage
 *  LoadImage loadImage = new LoadImage(ImageView iv);
 *  loadImage.execute(String image_url);
 */

public class LoadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    public LoadImage(ImageView ivResult){
        this.imageView = ivResult;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String urlLink = strings[0];
        Bitmap bitmap = null;
        try{
            InputStream inputStream = new URL(urlLink).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
