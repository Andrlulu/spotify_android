package com.laioffer.spotify.datamodel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Album(
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
