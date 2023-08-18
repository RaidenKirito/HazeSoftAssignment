package com.example.hazesoftassignment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hazesoftassignment.feature.shared.model.response.gifResponse.GifResponse
import com.example.hazesoftassignment.utils.constants.DbConstants

@Dao
interface AppDao {

    @Query("SELECT * FROM ${DbConstants.tbFavouriteGif}")
    suspend fun getGifFromDatabase(): List<GifResponse>

    @Insert
    suspend fun insertGifToFavourite(gifResponse: GifResponse?)

    @Query("DELETE FROM ${DbConstants.tbFavouriteGif} WHERE roomId == :id ")
    suspend fun deleteGifFromFavourite(id: Int?)
}