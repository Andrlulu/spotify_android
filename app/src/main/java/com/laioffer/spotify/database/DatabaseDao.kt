package com.laioffer.spotify.database

import androidx.room.*
import com.laioffer.spotify.datamodel.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun favoriteAlbum(album: Album)

    @Query("SELECT EXISTS(SELECT * FROM Album WHERE id = :id)")
    fun isFavoriteAlbum(id : Int) : Flow<Boolean>
    // ui state also a flow,

    // below is an example without using Flow
    // Flow is a data stream which is keep emitting data
    @Query("SELECT EXISTS(SELECT * FROM Album WHERE id = :id)")
    fun isFavoriteAlbum1(id : Int) : Boolean
    // one time check: isFavoriteAlbum1(true)
    // exist -> isFavoriteAlbum1(true) -> delete

    // flow example:
    // exist -> isFavoriteAlbum.collect {true, false}  -> delete

    @Delete
    fun unFavoriteAlbum(album: Album)

}