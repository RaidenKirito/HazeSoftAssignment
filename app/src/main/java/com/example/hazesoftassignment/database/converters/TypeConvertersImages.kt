package com.example.hazesoftassignment.database.converters

import androidx.room.TypeConverter
import com.example.hazesoftassignment.feature.shared.model.response.gifResponse.Images
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConvertersImages {
    @TypeConverter // note this annotation
    fun stringToObject(optionValuesString: String?): Images? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Images>() {

        }.type
        return gson.fromJson<Images>(optionValuesString, type)
    }

    @TypeConverter
    fun objectToString(listValues: Images?): String? {
        if (listValues == null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<Images>() {

        }.type
        return gson.toJson(listValues, type)
    }
}