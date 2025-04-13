package com.laioffer.spotify.repository;

import com.laioffer.spotify.datamodel.Section;
import com.laioffer.spotify.network.NetworkApi;
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject;

// use Inject in constructor to directly inject the dependency
public class HomeRepository @Inject constructor(
    private val networkApi: NetworkApi
){

    // FIXME: main safe: concurrent, slow code will block the main thread
//    fun getHomeSections(): List<Section> {
//        val response: Response<List<Section>> = networkApi.getHomeFeed().execute()
//        val sections: List<Section>? = response.body()
//        return sections ?: listOf()
//    }

    // suspend: a security to avoid callback hell, making the function enforced by kotlin coroutine
    // no scope, use withContext to specify config and coroutine
    // suspend indicates that this function have scope manage
    // withContext: change the thread
    suspend fun getHomeSections(): List<Section> = withContext(Dispatchers.IO) {
        networkApi.getHomeFeed().execute().body() ?: listOf()
    }
}


