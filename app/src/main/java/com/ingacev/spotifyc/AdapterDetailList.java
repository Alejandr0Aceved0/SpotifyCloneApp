package com.ingacev.spotifyc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingacev.spotifyc.Models.DetailListPodcast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterDetailList extends RecyclerView.Adapter<AdapterDetailList.ViewHolder> {

    private Context mContext;
    private ArrayList<DetailListPodcast> mListDetail;
    private AdapterPodcastList.OnItemClickListener mlistener;
    private List<DetailListPodcast> detailItems;

    public AdapterDetailList(Context context, ArrayList<DetailListPodcast> detailList){
        mContext = context;
        mListDetail = detailList;
        this.detailItems = new ArrayList<>();
        detailItems.addAll(mListDetail);
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.item_details, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DetailListPodcast item = mListDetail.get(position);

//        String id = item.getId();
//        String episode_number = item.getEpisode_number();
        String title = item.getTitle();
//        String description = item.getDescription();
//        String updated_at = item.getUpdated_at();
        String profile_image = item.getProfile_image();
//        String duration = item.getDuration();
//        String plays = item.getPlays();
//        String high_mp3 = item.getHigh_mp3();

        holder.tittle.setText(title);
        Picasso.with(mContext).load(profile_image).fit().centerInside().into(holder.logo);

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
        return mListDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //inicialize tv
        TextView tittle;
//        TextView channel_style;

        //inicialize imgv
        ImageView logo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tittle = itemView.findViewById(R.id.titlePodcastItemDetailsTv);
//            channel_style = itemView.findViewById(R.id.artistPodcast);

            logo = itemView.findViewById(R.id.logoPodcasItemDetailsImv);

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
