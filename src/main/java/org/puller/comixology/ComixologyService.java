package org.puller.comixology;

import retrofit.http.GET;

public interface ComixologyService {
    @GET("/")
    String getIndex();
}
