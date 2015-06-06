package org.puller.comixology;

import retrofit.http.*;

public interface ComixologyService {
    @GET("/")
    String getIndex();

    @FormUrlEncoded
    @POST("/login")
    String login(@Field("username") String username,
                 @Field("password") String password, @Field("PL_CSRF_TOKEN") String csrfToken);

    @GET("/printlist/{year}/{month}/{day}")
    String getWeeklyPullList(@Path("year") String year, @Path("month") String month, @Path("day") String day);
}
