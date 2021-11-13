package com.reddit.app.client

import com.reddit.app.model.RedditObject
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface RedditClient {

    @GET("/top.json")
    fun getTopAfter(
        @Query("after") after: String?,
        @Query("limit") limit: Int
    ): Observable<RedditObject?>?


    @GET("/top.json?t=all")
    fun getTop(): Observable<RedditObject?>?
}