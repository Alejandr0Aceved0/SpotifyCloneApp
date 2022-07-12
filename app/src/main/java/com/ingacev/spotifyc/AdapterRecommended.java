package com.ingacev.spotifyc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingacev.spotifyc.Models.RecommendedPodcast;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecommended extends RecyclerView.Adapter<AdapterRecommended.ViewHolder> {


    private Context mContext;
    private ArrayList<RecommendedPodcast> mListPodcast;
    private OnItemClickListener mlistener;
    private List<RecommendedPodcast> podcastsItems;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener( OnItemClickListener listener ){
        mlistener = listener;
    }

    public AdapterRecommended(Context context, ArrayList<RecommendedPodcast> recommendedPodcast){
        mContext = context;
        mListPodcast = recommendedPodcast;
        this.podcastsItems = new ArrayList<RecommendedPodcast>();
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
        RecommendedPodcast item = mListPodcast.get(position);
        String image = item.getLogo_image();

        holder.viewNewPodcast.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_up_down_layouts));
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

        //CarrouselView
        RoundedImageView viewNewPodcast;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgvPhoto = itemView.findViewById(R.id.podcastLogo);
            viewNewPodcast = itemView.findViewById(R.id.podcastLogo);

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
