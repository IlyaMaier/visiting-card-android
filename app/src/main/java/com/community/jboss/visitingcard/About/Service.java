package com.community.jboss.visitingcard.About;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {

    @GET("repos/JBossOutreach/visiting-card-android/contributors")
    Call<List<Contributor>> getContributors();

}
