package com.example.hazesoftassignment.feature.shared.model.module

import android.content.Context
import com.example.hazesoftassignment.database.AppDatabase
import com.example.hazesoftassignment.network.ApiService
import com.example.hazesoftassignment.network.RetrofitHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context) =
        AppDatabase.getAppDatabase(context)

    @Provides
    fun providesAppDao(appDatabase: AppDatabase?) = appDatabase?.getAppDao()

    @Singleton
    @Provides
    fun providesApiService(): ApiService = RetrofitHelper.getApiService()
}