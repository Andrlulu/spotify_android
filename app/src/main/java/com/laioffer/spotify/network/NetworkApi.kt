package com.laioffer.spotify.network

import com.laioffer.spotify.datamodel.Section
import retrofit2.Call
import retrofit2.http.GET

interface NetworkApi {

    // base url: 0.0../8080, api: /feed, restful api: Get
    @GET("/feed")
    fun getHomeFeed(): Call<List<Section>>

    // base url: 0.0../8080, api: /Playlist, restful api: Get
//    fun getPlaylist(): List<PLaylist>
}