package com.example.hazesoftassignment.database.converters

import androidx.room.TypeConverter
import com.example.hazesoftassignment.feature.shared.model.response.gifResponse.Original
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConvertersOriginal {
    @TypeConverter // note this annotation
    fun stringToObject(optionValuesString: String?): Original? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Original>() {

        }.type
        return gson.fromJson<Original>(optionValuesString, type)
    }

    @TypeConverter
    fun objectToString(listValues: Original?): String? {
        if (listValues == null) {
            return null
        }

        val gson = Gson()
        val type = object : TypeToken<Original>() {

        }.type
        return gson.toJson(listValues, type)
    }
}