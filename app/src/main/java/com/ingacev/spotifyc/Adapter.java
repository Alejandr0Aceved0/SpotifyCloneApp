package com.ingacev.spotifyc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingacev.spotifyc.Models.recommendedPodcast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context mContext;
    private ArrayList<recommendedPodcast> mListPodcast;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener( OnItemClickListener listener ){
        mlistener = listener;
    }

    public Adapter ( Context context, ArrayList<recommendedPodcast> podcastList){
        mContext = context;
        mListPodcast = podcastList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.podcast_carousel, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        recommendedPodcast item = mListPodcast.get(position);
        String image = item.getLogo_image();
        Picasso.with(mContext).load(image).fit().centerInside().into(holder.imgvPhoto);
    }

    @Override
    public int getItemCount() {
        return mListPodcast.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //inicialize tv
        ImageView imgvPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgvPhoto = itemView.findViewById(R.id.podcastLogo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mlistener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mlistener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
