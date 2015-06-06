package org.puller.comixology;

import retrofit.http.*;

import java.util.Map;

public interface ComixologyService {
    @GET("/")
    String getIndex();

    @FormUrlEncoded
    @POST("/login")
    String login(@FieldMap Map<String, String> user, @Field("PL_CSRF_TOKEN") String csrfToken);

    @GET("/printlist/{year}/{month}/{day}")
    String getWeeklyPullList(@Path("year") String year, @Path("month") String month, @Path("day") String day);
}
