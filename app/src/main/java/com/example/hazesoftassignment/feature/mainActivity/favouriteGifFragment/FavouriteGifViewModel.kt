package com.example.hazesoftassignment.feature.mainActivity.favouriteGifFragment

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
class FavouriteGifViewModel @Inject constructor(
    private val trendingGifRepository: TrendingGifRepository?
) : BaseViewModel() {

    private val _favouriteGifResponse = SingleLiveEvent<List<GifResponse>?>()
    val favouriteGifResponse: LiveData<List<GifResponse>?> get() = _favouriteGifResponse

    private val _onDeleteGifFromFavouriteResponse = SingleLiveEvent<Unit>()
    val onDeleteGifFromFavouriteResponse: LiveData<Unit> get() = _onDeleteGifFromFavouriteResponse

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

    fun deleteGifFromFavourite(gifId: String?) {
        showLoading(R.string.saving_data)
        viewModelScope.launch(Dispatchers.IO) {
            trendingGifRepository?.deleteGifFromFavourite(gifId)?.onSuccess {
                hideLoading()
                _onDeleteGifFromFavouriteResponse.postValue(it)
            }?.onFailure {
                hideLoading()
                handleError(R.string.failed_to_save_data) {}
            }
        }
    }
}