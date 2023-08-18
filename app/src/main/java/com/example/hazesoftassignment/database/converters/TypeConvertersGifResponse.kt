package com.example.hazesoftassignment.database.converters

import androidx.room.TypeConverter
import com.example.hazesoftassignment.feature.shared.model.response.gifResponse.GifResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConvertersGifResponse {
    @TypeConverter // note this annotation
    fun stringToObject(optionValuesString: String?): GifResponse? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<GifResponse>() {

        }.type
        return gson.fromJson<GifResponse>(optionValuesString, type)
    }

    @TypeConverter
    fun objectToString(listValues: GifResponse?): String? {
        if (listValues == null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<GifResponse>() {

        }.type
        return gson.toJson(listValues, type)
    }
}