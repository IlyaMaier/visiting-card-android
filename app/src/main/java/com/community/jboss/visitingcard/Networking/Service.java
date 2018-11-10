package com.community.jboss.visitingcard.Networking;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Service {

    @POST("/api/card")
    Call<String> post(@Body VisitingCard visitingCard);

    @GET("/api/card")
    Call<VisitingCard> get();

}
