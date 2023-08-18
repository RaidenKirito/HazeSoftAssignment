package com.example.hazesoftassignment.feature.shared.repository.trendingGifRepository

import com.example.hazesoftassignment.database.AppDao
import com.example.hazesoftassignment.feature.shared.base.BaseRepository
import com.example.hazesoftassignment.feature.shared.model.response.base.Resource
import com.example.hazesoftassignment.feature.shared.model.response.gifResponse.GifResponse
import javax.inject.Inject

class TrendingGifRepository @Inject constructor(
    private val appDao: AppDao?
) : BaseRepository() {

    suspend fun getTrendingGif(
        apiKey: String?, limit: Int?, offset: Int?
    ): Resource<List<GifResponse>?> {
        return try {
            apiService.getTrendingGif(apiKey, limit, offset).let { response ->
                if (response.isSuccessful && response.body() != null) {
                    val gifData = response.body()?.data
                    val pagination = response.body()?.pagination
                    gifData?.forEach {
                        it.hasNext = (pagination?.offset ?: 0) < (pagination?.totalCount ?: 0)
                    }
                    Resource.Success(gifData ?: emptyList())
                } else {
                    Resource.Error(getError(response.code(), response.errorBody()?.string()))
                }
            }
        } catch (ex: Exception) {
            Resource.Error(getError(ex))
        }
    }

    suspend fun getSearchedGif(
        apiKey: String?, searchKey: String?, limit: Int?, offset: Int?
    ): Resource<List<GifResponse>?> {
        return try {
            apiService.getSearchedGif(apiKey, searchKey, limit, offset).let { response ->
                if (response.isSuccessful && response.body() != null) {
                    val gifData = response.body()?.data
                    val pagination = response.body()?.pagination
                    gifData?.forEach {
                        it.hasNext = (pagination?.offset ?: 0) < (pagination?.totalCount ?: 0)
                    }
                    Resource.Success(gifData ?: emptyList())
                } else {
                    Resource.Error(getError(response.code(), response.errorBody()?.string()))
                }
            }
        } catch (ex: Exception) {
            Resource.Error(getError(ex))
        }
    }

    suspend fun getFavouriteGif(): Resource<List<GifResponse>> {
        return try {
            Resource.Success(appDao?.getGifFromDatabase())
        } catch (ex: Exception) {
            Resource.Error(ex)
        }
    }

    suspend fun insertGifToFavourite(gifResponse: GifResponse?): Resource<Unit> {
        try {
            appDao?.insertGifToFavourite(gifResponse).let {
                return Resource.Success(it)
            }
        } catch (e: Exception) {
            return Resource.Error(e)
        }
    }

    suspend fun deleteGifFromFavourite(id: Int?): Resource<Unit> {
        try {
            appDao?.deleteGifFromFavourite(id).let {
                return Resource.Success(it)
            }
        } catch (e: Exception) {
            return Resource.Error(e)
        }
    }
}