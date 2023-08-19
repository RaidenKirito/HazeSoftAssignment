package com.example.hazesoftassignment.feature.mainActivity.trendingGifFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.hazesoftassignment.R
import com.example.hazesoftassignment.feature.shared.base.BaseViewModel
import com.example.hazesoftassignment.feature.shared.model.response.gifResponse.GifResponse
import com.example.hazesoftassignment.feature.shared.repository.trendingGifRepository.TrendingGifRepository
import com.example.hazesoftassignment.utils.extensions.onFailure
import com.example.hazesoftassignment.utils.extensions.onSuccess
import com.example.hazesoftassignment.utils.helper.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingGifViewModel @Inject constructor(
    private val trendingGifRepository: TrendingGifRepository?
) : BaseViewModel() {

    private val _trendingGifResponse = SingleLiveEvent<List<GifResponse>?>()
    val trendingGifResponse: LiveData<List<GifResponse>?> get() = _trendingGifResponse

    private val _searchedGifResponse = SingleLiveEvent<List<GifResponse>?>()
    val searchedGifResponse: LiveData<List<GifResponse>?> get() = _searchedGifResponse

    private val _favouriteGifResponse = SingleLiveEvent<List<GifResponse>?>()
    val favouriteGifResponse: LiveData<List<GifResponse>?> get() = _favouriteGifResponse

    private val _onInsertGifToFavouriteResponse = SingleLiveEvent<Unit>()
    val onInsertGifToFavouriteResponse: LiveData<Unit> get() = _onInsertGifToFavouriteResponse

    fun getTrendingGif(
        apiKey: String?, limit: Int?, offset: Int?
    ) {
        showLoading(R.string.loading)
        viewModelScope.launch(Dispatchers.IO) {
            trendingGifRepository?.getTrendingGif(apiKey, limit, offset)?.onSuccess { gifResponse ->
                hideLoading()
                _trendingGifResponse.postValue(gifResponse)
            }?.onFailure { throwable ->
                hideLoading()
                performActionOnException(throwable) {}
            }
        }
    }

    fun getSearchedGif(
        apiKey: String?, searchKey: String?, limit: Int?, offset: Int?
    ) {
        showLoading(R.string.loading)
        viewModelScope.launch(Dispatchers.IO) {
            trendingGifRepository?.getSearchedGif(apiKey, searchKey, limit, offset)
                ?.onSuccess { gifResponse ->
                    hideLoading()
                    _searchedGifResponse.postValue(gifResponse)
                }?.onFailure { throwable ->
                    hideLoading()
                    performActionOnException(throwable) {}
                }
        }
    }

    fun getFavouriteGif() {
        showLoading(R.string.loading)
        viewModelScope.launch(Dispatchers.IO) {
            trendingGifRepository?.getFavouriteGif()?.onSuccess { gifResponse ->
                hideLoading()
                _favouriteGifResponse.postValue(gifResponse)
            }?.onFailure { throwable ->
                hideLoading()
                performActionOnException(throwable) {}
            }
        }
    }

    fun insertGifToFavourite(gifResponse: GifResponse?) {
        showLoading(R.string.saving_data)
        viewModelScope.launch(Dispatchers.IO) {
            trendingGifRepository?.insertGifToFavourite(gifResponse)?.onSuccess {
                hideLoading()
                _onInsertGifToFavouriteResponse.postValue(it)
            }?.onFailure {
                hideLoading()
                handleError(R.string.failed_to_save_data) {}
            }
        }
    }
}