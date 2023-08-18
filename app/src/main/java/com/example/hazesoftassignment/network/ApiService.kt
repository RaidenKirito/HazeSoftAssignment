package com.example.hazesoftassignment.network

import com.example.hazesoftassignment.feature.shared.model.response.base.BaseArrayResponse
import com.example.hazesoftassignment.feature.shared.model.response.gifResponse.GifResponse
import com.example.hazesoftassignment.utils.constants.ApiConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ApiConstants.trendingGiphy)
    suspend fun getTrendingGif(
        @Query("api_key") apiKey: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Response<BaseArrayResponse<GifResponse>>

    @GET(ApiConstants.searchGiphy)
    suspend fun getSearchedGif(
        @Query("api_key") apiKey: String? = null,
        @Query("q") searchKey: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Response<BaseArrayResponse<GifResponse>>
}