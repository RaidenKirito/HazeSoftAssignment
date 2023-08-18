package com.example.hazesoftassignment.feature.shared.model.response.gifResponse

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hazesoftassignment.utils.constants.Constants
import com.example.hazesoftassignment.utils.constants.DbConstants
import com.google.gson.annotations.SerializedName

@Entity(tableName = DbConstants.tbFavouriteGif)
data class GifResponse(
    @PrimaryKey(autoGenerate = true) val roomId: Int? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("images") val images: Images? = null,
    var viewType: String? = Constants.gifListView,
    var hasNext: Boolean? = false,
    var isFavourite: Boolean = false
)