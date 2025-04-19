package com.laioffer.spotify.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Album(
    @PrimaryKey
    val id: Int,
    @SerializedName("album") // help map name to album in the json
    val name: String,
    val year: String,
    val cover: String,
    val artists: String,
    val description: String
): Serializable { // extend Serializable, so it can be saved in a file for safety
    companion object {
        fun empty(): Album {
            return Album(
                id = -1,
                "",
                "",
                "",
                "",
                "",
            )
        }
    }
}
