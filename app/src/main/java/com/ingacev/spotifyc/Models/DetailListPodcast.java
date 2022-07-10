package com.ingacev.spotifyc.Models;

import java.io.Serializable;

public class DetailListPodcast implements Serializable {

    private String id;
    private String episode_number;
    private String title;
    private String description;
    private String updated_at;
    private String profile_image;
    private String duration;
    private String plays;
    private String high_mp3;


    public DetailListPodcast(String id, String episode_number, String title, String description, String updated_at, String profile_image, String duration, String plays, String high_mp3) {

        this.id = id;
        this.episode_number = episode_number;
        this.title = title;
        this.description = description;
        this.updated_at = updated_at;
        this.profile_image = profile_image;
        this.duration = duration;
        this.plays = plays;
        this.high_mp3 = high_mp3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(String episode_number) {
        this.episode_number = episode_number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPlays() {
        return plays;
    }

    public void setPlays(String plays) {
        this.plays = plays;
    }

    public String getHigh_mp3() {
        return high_mp3;
    }

    public void setHigh_mp3(String high_mp3) {
        this.high_mp3 = high_mp3;
    }
}
