package com.ingacev.spotifyc;


import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ingacev.spotifyc.Models.DetailListPodcast;
import com.ingacev.spotifyc.Models.PodcastsList;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PodcastDetail extends AppCompatActivity {

    private ImageView bannerBack;
    private ImageView logoBack;
    private TextView titlePodcast;
    private TextView descriptionPodcast;
    private PodcastsList itemDetail;
    private String idPodcast;
    private RequestQueue mRequest;
    private FloatingActionButton fabButton;
    private ArrayList<DetailListPodcast> detailPodcastListChannels;
    private AdapterDetailList mAdapterPodcastList;
    private RecyclerView mRecyclerViewlistDetailsPodcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast_detail);
        itemDetail = (PodcastsList) getIntent().getExtras().getSerializable("itemDetail");
        titlePodcast = findViewById(R.id.namePodcast);
        bannerBack = findViewById(R.id.bannerBackground);
        logoBack = findViewById(R.id.logoPodcastDetail);
        descriptionPodcast = findViewById(R.id.descriptionPodcast);
        fabButton = findViewById(R.id.fabButton);
        mRequest = Volley.newRequestQueue(this);
        titlePodcast.setText(itemDetail.getTitle());
        descriptionPodcast.setText(itemDetail.getDescription());
        Picasso.with(this).load(itemDetail.getLogo_image()).fit().centerInside().into(logoBack);


        mRecyclerViewlistDetailsPodcast = findViewById(R.id.listDetailsPodcast);
        mRecyclerViewlistDetailsPodcast.setHasFixedSize(true);
        mRecyclerViewlistDetailsPodcast.setLayoutManager(new LinearLayoutManager(this));

        detailPodcastListChannels = new ArrayList<>();

        if(itemDetail.getBanner_image().equals("null")) {
            Picasso.with(this).load(itemDetail.getLogo_image()).fit().centerInside().into(bannerBack);
        }else{
            Picasso.with(this).load(itemDetail.getBanner_image()).fit().centerInside().into(bannerBack);
        }

        fabButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PodcastDetail.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                finish();
            }
        });
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);//Convierte la imagen a blanco y negro
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        bannerBack.setColorFilter(filter);
        bannerBack.setAlpha(50);

        idPodcast = itemDetail.getId();
        getPodcastList(idPodcast);
        
        



    }

    private void getPodcastList(String idPodcast) {

        {
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, Constants.getApiSpotifyChannels + idPodcast + "/audio_clips", null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject jsonObject = response.getJSONObject("body");
                                JSONArray jsonArray = jsonObject.getJSONArray("audio_clips");
                                int longObject = jsonArray.length();
                                for (int i = 0; i < longObject; i++) {
                                    JSONObject result = jsonArray.getJSONObject(i);
                                    String id = result.getString("id");
                                    String episode_number = result.getString("episode_number");
                                    String title = result.getString("title");
                                    String description = result.getString("description");
                                    String updated_at = result.getString("updated_at");
                                    String [] split = updated_at.split("T");
                                    updated_at = split[0];
                                    String profile_image = result.getJSONObject("user").getJSONObject("urls").getString("image");//Caratula img
                                    String duration = result.getString("duration");
                                    float durationParse = Float.parseFloat(duration);
                                    int intParse = Math.round(durationParse);
                                    intParse = intParse/60;
                                    duration = (formatTime(intParse));

                                    String plays = result.getJSONObject("counts").getString("plays"); //CANTIDAD DE REPRODUCCIONES REALIZADAS
                                    String high_mp3 = result.getJSONObject("urls").getString("high_mp3"); //RUTA DE REPRODUCCION .mp3

                                    detailPodcastListChannels.add(new DetailListPodcast(id, episode_number, title, description, updated_at, profile_image, duration, plays, high_mp3));

                                }

                                mAdapterPodcastList = new AdapterDetailList(PodcastDetail.this, detailPodcastListChannels);
                                mRecyclerViewlistDetailsPodcast.setAdapter(mAdapterPodcastList);
                            } catch (JSONException e) {
                                System.out.println("ERRORES CON: " + e);
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("APP FALLO :( ", "That didn't work! " + error);
                }
            });
            mRequest.add(stringRequest);//EJECUTA LA PETICION REST A LA API
        }
    }


    public String formatTime(int minutos) {
        String formato = "%02d:%02d";
        long horasReales = TimeUnit.MINUTES.toHours(minutos);
        long minutosReales = TimeUnit.MINUTES.toMinutes(minutos) - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(minutos));
        String result = String.format(formato, horasReales, minutosReales);
        return result;
    }
}



