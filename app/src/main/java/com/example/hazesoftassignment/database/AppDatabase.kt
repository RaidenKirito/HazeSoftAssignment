package com.example.hazesoftassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hazesoftassignment.database.converters.TypeConvertersGifResponse
import com.example.hazesoftassignment.database.converters.TypeConvertersImages
import com.example.hazesoftassignment.database.converters.TypeConvertersOriginal
import com.example.hazesoftassignment.feature.shared.model.response.gifResponse.GifResponse
import com.example.hazesoftassignment.utils.constants.DbConstants

@Database(
    entities = [GifResponse::class], version = 1
)
@TypeConverters(
    TypeConvertersGifResponse::class, TypeConvertersImages::class, TypeConvertersOriginal::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAppDao(): AppDao

    companion object {
        private var appDatabase: AppDatabase? = null

        fun getAppDatabase(context: Context?) =
            if (appDatabase != null && appDatabase?.isOpen == true) {
                appDatabase
            } else {
                if (context != null) {
                    Room.databaseBuilder(context, AppDatabase::class.java, DbConstants.dbName)
                        .build()
                } else {
                    null
                }
            }
    }
}