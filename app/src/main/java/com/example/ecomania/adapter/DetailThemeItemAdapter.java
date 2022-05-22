package com.example.ecomania.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecomania.R;
import com.example.ecomania.model.DetailsTheme;
import com.example.ecomania.utils.LoadImage;

import java.util.ArrayList;

public class DetailThemeItemAdapter extends RecyclerView.Adapter<DetailThemeItemAdapter.MyViewHolder> {

    private ArrayList<DetailsTheme> detailsThemeArrayList;

    public DetailThemeItemAdapter(ArrayList<DetailsTheme> detailsThemeArrayList){
        this.detailsThemeArrayList = detailsThemeArrayList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView detail_title;
        private TextView detail_description;
        private ImageView image;
        private VideoView video;

        public MyViewHolder(final View view){
            super(view);
            detail_title = view.findViewById(R.id.detail_title);
            detail_description = view.findViewById(R.id.detail_description);
            image = view.findViewById(R.id.image);
            video = view.findViewById(R.id.video);
        }

    }

    @NonNull
    @Override
    public DetailThemeItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_theme_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailThemeItemAdapter.MyViewHolder holder, int position) {
        //affichage detail_title
        String detail_title = detailsThemeArrayList.get(position).getTitre();
        holder.detail_title.setText(detail_title);

        //affichage detail_description
        String detail_description = detailsThemeArrayList.get(position).getDescription();
        holder.detail_description.setText(detail_description);

        //affichage image
        String image_url = detailsThemeArrayList.get(position).getImage();
        LoadImage loadImage = new LoadImage(holder.image);
        loadImage.execute(image_url);

        //affichage video
        String video_url = detailsThemeArrayList.get(position).getVideo();
        if(video_url.compareToIgnoreCase("null")!=0 && video_url!=null && video_url != ""){
            holder.video.setVideoPath(video_url);

            MediaController mediaController = new MediaController(holder.video.getContext());
            holder.video.setMediaController(mediaController);
            mediaController.setAnchorView(holder.video);

            holder.video.start();
        }else{
            holder.video.setVisibility(RelativeLayout.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return detailsThemeArrayList.size();
    }

}
