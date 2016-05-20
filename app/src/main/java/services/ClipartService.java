package services;

import data.Clipart;
import retrofit.Callback;
import retrofit.http.EncodedPath;
import retrofit.http.GET;

/**
 * Created by sean on 5/12/16.
 */
public interface ClipartService {
    @GET("/search/json/{query}")
    public void getClipArt(@EncodedPath("query") String ids, Callback<Clipart> response);


}
