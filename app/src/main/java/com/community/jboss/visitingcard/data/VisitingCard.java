package com.community.jboss.visitingcard.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity
public class VisitingCard {

    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String number;
    private String email;
    private String linkedin;
    private String twitter;
    private String github;
    private byte[] photo;
    private int favourite;

    public VisitingCard(String name, String number, String email, String linkedin, String twitter, String github, byte[] photo, int favourite) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.number = number;
        this.email = email;
        this.linkedin = linkedin;
        this.twitter = twitter;
        this.github = github;
        this.photo = photo;
        this.favourite = favourite;
    }

    @Ignore
    public VisitingCard(@NonNull String id, String name, String number, String email, String linkedin, String twitter, String github, byte[] photo, int favourite) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.linkedin = linkedin;
        this.twitter = twitter;
        this.github = github;
        this.photo = photo;
        this.favourite = favourite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

}
