/*
 * Copyright (c) 2016.
 * create implementation of GitHubService
 */

package com.monigarr.monigarrrepos;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by monicapeters on 9/2/16.
 */

public interface GitHubService {
    //search github usernames with starred repos
    @GET("users/{user}/starred") Observable<List<GitHubRepo>> getStarredRepositories(@Path("user") String userName);

    //search MoniGarr github repos for keyword
    //@GET("search/users") Observable<List<GitHubRepo>> getStarredRepositories(@Query("user") String userName);

}
