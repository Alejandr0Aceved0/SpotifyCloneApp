package com.ingacev.spotifyc.Models;

public class recommendedPodcast implements Comparable <recommendedPodcast>{

    private String type;
    private String id;
    private String updated_at;
    private String created_at;
    private String title;
    private String description;
    private String logo_image;
    private String banner_image;
    private String recommendation_position;

    public recommendedPodcast(String type, String id, String updated_at, String created_at, String title, String description, String logo_image, String banner_image, String recommendation_position) {

        this.type = type;
        this.id = id;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.title = title;
        this.description = description;
        this.logo_image = logo_image;
        this.banner_image = banner_image;
        this.recommendation_position = recommendation_position;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public String getLogo_image() {
        return logo_image;
    }

    public void setLogo_image(String logo_image) {
        this.logo_image = logo_image;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public String getRecommendation_position() {
        return recommendation_position;
    }

    public void setRecommendation_position(String recommendation_position) {
        this.recommendation_position = recommendation_position;
    }

    @Override
    public int compareTo(recommendedPodcast recommendedPodcast) {
        return 0;
    }
}
