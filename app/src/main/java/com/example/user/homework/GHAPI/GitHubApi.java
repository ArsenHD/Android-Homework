package com.example.user.homework.GHAPI;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubApi {
    @GET("repos/square/{name1}/contributors")
    Observable<List<User>> getContributors(@Path("name1") String repoName, @Query("apiKey") String apiKey);
}
