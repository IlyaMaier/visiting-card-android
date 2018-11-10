package com.community.jboss.visitingcard.Networking;

import com.google.gson.annotations.SerializedName;

public class VisitingCard {

    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("number")
    private String number;
    @SerializedName("github")
    private String github;
    @SerializedName("linkedin")
    private String linkedin;
    @SerializedName("twitter")
    private String twitter;
    @SerializedName("photo")
    private byte[] photo;

    public VisitingCard(String name, String email, String number, String github, String linkedin, String twitter, byte[] photo) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.github = github;
        this.linkedin = linkedin;
        this.twitter = twitter;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

}
