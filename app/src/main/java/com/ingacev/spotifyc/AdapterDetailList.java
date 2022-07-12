package com.ingacev.spotifyc;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingacev.spotifyc.Models.DetailListPodcast;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdapterDetailList extends RecyclerView.Adapter<AdapterDetailList.ViewHolder> {

    private Context mContext;
    private ArrayList<DetailListPodcast> mListDetail;
    private AdapterPodcastList.OnItemClickListener mlistener;
    private List<DetailListPodcast> detailItems;
    public  ViewHolder holderBefore;
    MediaPlayer mediaPlayer = new MediaPlayer();
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

        String id = item.getId();
        String episode_number = item.getEpisode_number();
        String title = item.getTitle();
        String description = item.getDescription();
        String updated_at = item.getUpdated_at();
        String profile_image = item.getProfile_image();
        String duration = item.getDuration();
        String plays = item.getPlays();
        String high_mp3 = item.getHigh_mp3();

        holder.episode_number.setText(episode_number);
        holder.tittle.setText(title);
        holder.updated_at.setText(updated_at);
        holder.duration.setText(duration);
        holder.plays.setText(plays);
        holder.lyItemsPodcastList.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_items));
        Picasso.with(mContext).load(profile_image).fit().centerInside().into(holder.logo);
        holder.btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if (mediaPlayer.isPlaying()){

                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                        mediaPlayer = new MediaPlayer();
                        holderBefore.btnPlayPause.setBackgroundResource(R.drawable.ic_play_music);
                    }

                    if (!mediaPlayer.isPlaying()){
                        String url = high_mp3;
                        holder.btnPlayPause.setBackgroundResource(R.drawable.ic_pause_music_disenabled);
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setDataSource(url);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        System.out.println("DURACION: " + mediaPlayer.getDuration());
                        holderBefore = holder;
                        holder.btnPlayPause.setBackgroundResource(R.drawable.ic_pause_music);

                    }

                }catch (Exception e){
                    System.out.println("FALLA EN: "+ e);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //inicialize tv
        TextView episode_number;
        TextView tittle;
        TextView updated_at;
        TextView duration;
        TextView plays;

        Button btnPlayPause;

        //inicialize imgv
        ImageView logo;

        //LinearItems
        LinearLayout lyItemsPodcastList;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            episode_number = itemView.findViewById(R.id.numCapitulo);
            tittle = itemView.findViewById(R.id.titlePodcastItemDetailsTv);
            updated_at = itemView.findViewById(R.id.updated_atPodcastItemDetails);
            duration = itemView.findViewById(R.id.durationPodcastItemDetails);
            plays = itemView.findViewById(R.id.playsPodcastItemDetails);
            btnPlayPause = itemView.findViewById(R.id.btnPlayAndPause);
            logo = itemView.findViewById(R.id.logoPodcasItemDetailsImv);
            lyItemsPodcastList = itemView.findViewById(R.id.linearItemViewPodcastDetail);

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
