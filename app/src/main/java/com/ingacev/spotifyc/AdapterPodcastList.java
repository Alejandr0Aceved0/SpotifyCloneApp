package com.ingacev.spotifyc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ingacev.spotifyc.Models.PodcastsList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdapterPodcastList extends RecyclerView.Adapter<AdapterPodcastList.ViewHolder> {

    private Context mContext;
    private ArrayList<PodcastsList> mListPodcast;
    private OnItemClickListener mlistener;
    private List<PodcastsList> podcastListItems;

    public AdapterPodcastList(Context context, ArrayList<PodcastsList> podcastList){
        mContext = context;
        mListPodcast = podcastList;
        this.podcastListItems = new ArrayList<>();
        podcastListItems.addAll(mListPodcast);
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.podcast_vertical, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        PodcastsList item = mListPodcast.get(position);
        String title = item.getTitle();
        String channel_style = item.getChannel_style();
        String image = item.getLogo_image();

        holder.podcastTitle.setText(title);
        holder.channel_style.setText(channel_style);
        holder.linearItemViewVerticalMain.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_items));

        Picasso.with(mContext).load(image).fit().centerInside().into(holder.logoPodcast);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), PodcastDetail.class);
                intent.putExtra("itemDetail", item);
                holder.itemView.getContext().startActivity(intent);

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) holder.itemView.getContext(), (View)view, "appcard");
                holder.itemView.getContext().startActivity(intent, options.toBundle());

            }
        });
    }


    @Override
    public int getItemCount() {

        return mListPodcast.size();
    }

    public void filter(String strSearch){
        if (strSearch.length() ==0){
            mListPodcast.clear();
            mListPodcast.addAll(podcastListItems);
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                mListPodcast.clear();
                List<PodcastsList> collect = podcastListItems.stream()
                        .filter(i -> i.getTitle().contains(strSearch))
                        .collect(Collectors.toList());
                mListPodcast.addAll(collect);
            }else{
                mListPodcast.clear();
                for (PodcastsList i : podcastListItems){
                    if (i.getTitle().contains(strSearch)){
                        mListPodcast.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //inicialize tv
        TextView podcastTitle;
        TextView channel_style;

        //inicialize imgv
        ImageView logoPodcast;

        //cv
        LinearLayout linearItemViewVerticalMain;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            podcastTitle = itemView.findViewById(R.id.titlePodcast);
            channel_style = itemView.findViewById(R.id.artistPodcast);
            logoPodcast = itemView.findViewById(R.id.logoPodcastV);
            linearItemViewVerticalMain = itemView.findViewById(R.id.linearItemViewVerticalMain);

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