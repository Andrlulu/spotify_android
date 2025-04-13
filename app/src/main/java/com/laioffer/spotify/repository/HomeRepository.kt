package com.laioffer.spotify.repository;

import com.laioffer.spotify.datamodel.Section;
import com.laioffer.spotify.network.NetworkApi;
import retrofit2.Response
import javax.inject.Inject;

// use Inject in constructor to directly inject the dependency
public class HomeRepository @Inject constructor(
    private val networkApi: NetworkApi
){

    // FIXME: main safe: concurrent, slow code will block the main thread
    fun getHomeSections(): List<Section> {
        val response: Response<List<Section>> = networkApi.getHomeFeed().execute()
        val sections: List<Section>? = response.body()
        return sections ?: listOf()
    }
}


