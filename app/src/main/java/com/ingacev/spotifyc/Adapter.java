package com.ingacev.spotifyc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ingacev.spotifyc.Models.recommendedPodcast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    private Context mContext;
    private ArrayList<recommendedPodcast> mListPodcast;
    private OnItemClickListener mlistener;
    private List<recommendedPodcast> podcastsItems;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener( OnItemClickListener listener ){
        mlistener = listener;
    }

    public Adapter ( Context context, ArrayList<recommendedPodcast> recommendedPodcast){
        mContext = context;
        mListPodcast = recommendedPodcast;
        this.podcastsItems = new ArrayList<recommendedPodcast>();
        podcastsItems.addAll(mListPodcast);

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

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(holder.itemView.getContext(), PodcastDetail.class);
//                intent.putExtra("itemDetail", item);
//                holder.itemView.getContext().startActivity(intent);
//
//                ActivityOptionsCompat options = ActivityOptionsCompat.
//                        makeSceneTransitionAnimation((Activity) holder.itemView.getContext(), (View)view, "appcard");
//                holder.itemView.getContext().startActivity(intent, options.toBundle());
//
//            }
//        });

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
