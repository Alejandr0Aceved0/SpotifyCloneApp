package com.ingacev.spotifyc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ingacev.spotifyc.Models.podcastsList;
import com.ingacev.spotifyc.Models.recommendedPodcast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RequestQueue mRequest;
    private ViewPager2 mRecyclerView;
    private RecyclerView mRecyclerViewVertical;
    private SearchView searchView;
    private ArrayList<recommendedPodcast> recommendedCarouselList;
    private ArrayList<podcastsList> podcastListVertical;
    private Adapter mAdapter;
    private AdapterPodcastList mAdapterPodcastList;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.navSearch);
        mRecyclerView = findViewById(R.id.newPodcast);
        mRecyclerViewVertical = findViewById(R.id.podcastVerticalList);
        mRecyclerViewVertical.setHasFixedSize(true);
        mRecyclerViewVertical.setLayoutManager(new LinearLayoutManager(this));
        mRequest = Volley.newRequestQueue(this);
        recommendedCarouselList = new ArrayList<>();
        podcastListVertical = new ArrayList<>();
        getPodcasts();
        initListener();
    }

    private void getPodcasts() {
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, Constants.getApiSpotifyClone + "recommended", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("body");
                            int longitudArray = jsonArray.length();
                            for (int i = 0; i < longitudArray; i++) {
                                JSONObject result = jsonArray.getJSONObject(i);
                                String type = result.getString("type");
                                String id = result.getString("id");
                                String updated_at = result.getString("updated_at");
                                String created_at = result.getString("created_at");
                                String title = result.getString("title");
                                String description = result.getString("description");
                                String channel_style = result.getString("channel_style");
                                String logo_image = result.getJSONObject("urls").getJSONObject("logo_image").getString("original");
                                String banner_image = result.getJSONObject("urls").getJSONObject("banner_image").getString("original");
                                String recommendation_position = result.getJSONObject("recommendation").getString("position");

                                recommendedCarouselList.add(new recommendedPodcast(type, id, updated_at, created_at, title, description, logo_image, banner_image, recommendation_position));
                                podcastListVertical.add(new podcastsList(type, id, updated_at, created_at, title, description, channel_style, logo_image, banner_image, recommendation_position));
                                Collections.sort(recommendedCarouselList);

                            }

                            mAdapter = new Adapter(MainActivity.this, recommendedCarouselList);
                            mRecyclerView.setClipToPadding(false);
                            mRecyclerView.setClipChildren(false);
                            mRecyclerView.setOffscreenPageLimit(3);
                            mRecyclerView.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
                            mRecyclerView.setAdapter(mAdapter);
                            CompositePageTransformer transformer = new CompositePageTransformer();
                            transformer.addTransformer(new MarginPageTransformer(80));
                            transformer.addTransformer(new ViewPager2.PageTransformer() {
                                @Override
                                public void transformPage(@NonNull View page, float position) {
                                    float v = 1 - Math.abs(position);
                                    page.setScaleY(0.85f + v * 0.15f);
                                }
                            });
                            //RECYCLERVIEW CARRUSEL
                            mRecyclerView.setPageTransformer(transformer);

                            mAdapterPodcastList = new AdapterPodcastList(MainActivity.this, podcastListVertical);
                            mRecyclerViewVertical.setAdapter(mAdapterPodcastList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ANIMES FALLO :( ", "That didn't work! " + error);
            }
        });
        mRequest.add(stringRequest);//EJECUTA LA PETICION REST A LA API
    }
    private void initListener(){
        searchView.setOnQueryTextListener(MainActivity.this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {//SE EJECUTA OPRIMIENDO EL ICONO SEACH DEL TECLADO
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {//SE EJEECUTA CADA QUE ESCRIBAMOS EMPIEZA A BUSCAR
        mAdapterPodcastList.filter(newText);
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
