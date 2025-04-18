package com.laioffer.spotify.repository

import com.laioffer.spotify.datamodel.Playlist
import com.laioffer.spotify.network.NetworkApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaylistRepository @Inject constructor(private val networkApi: NetworkApi) {
    //without withContext, it will be called in Main thread, which is not a good practice
    // need to manually warp the function with withContext
    // with suspend, the function can only be called inside a coroutine scope.
    // coroutine will run the function in a Dispatchers.IO thread.
    // making sure this function is main safe.
    suspend fun getPlaylist(id: Int): Playlist = withContext(Dispatchers.IO) {
        networkApi.getPlaylist(id).execute().body()!!
    }
}