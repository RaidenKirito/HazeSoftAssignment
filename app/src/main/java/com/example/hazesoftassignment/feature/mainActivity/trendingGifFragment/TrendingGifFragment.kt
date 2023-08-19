package com.example.hazesoftassignment.feature.mainActivity.trendingGifFragment

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.hazesoftassignment.databinding.FragmentTrendingGifBinding
import com.example.hazesoftassignment.feature.mainActivity.MainActivity
import com.example.hazesoftassignment.feature.shared.adapter.GifAdapter
import com.example.hazesoftassignment.feature.shared.base.BaseFragment
import com.example.hazesoftassignment.feature.shared.model.response.gifResponse.GifResponse
import com.example.hazesoftassignment.utils.constants.ApiConstants
import com.example.hazesoftassignment.utils.constants.Constants
import com.example.hazesoftassignment.utils.extensions.hideSoftKeyboard
import com.example.hazesoftassignment.utils.extensions.viewGone
import com.example.hazesoftassignment.utils.extensions.viewVisible
import com.example.hazesoftassignment.utils.util.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TrendingGifFragment : BaseFragment<FragmentTrendingGifBinding, TrendingGifViewModel>() {
    private val trendingGifViewModel: TrendingGifViewModel by viewModels()
    private var gifAdapter: GifAdapter? = null
    private var trendingGifList: MutableList<GifResponse>? = mutableListOf()
    private var favouriteGifList: MutableList<GifResponse>? = mutableListOf()
    private var searchedGifList: MutableList<GifResponse>? = mutableListOf()
    var gifList: MutableList<GifResponse>? = mutableListOf()
    private var limit: Int? = 10
    private var offset: Int = 0

    override fun getViewModel() = trendingGifViewModel

    override fun getViewBinding() = FragmentTrendingGifBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        getFavouriteGif()
        initAdapter()
        initListeners()
        initObservers()
    }

    private fun getTrendingGif(limit: Int?, offset: Int?) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            binding?.txvInternetNotFound?.viewGone()
            trendingGifViewModel.getTrendingGif(ApiConstants.apiKey, limit, offset)
        } else {
            binding?.txvInternetNotFound?.viewVisible()
        }
    }

    private fun getFavouriteGif() {
        trendingGifViewModel.getFavouriteGif()
    }

    private fun getSearchedGif(searchKey: String?, limit: Int?, offset: Int?) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            binding?.txvInternetNotFound?.viewGone()
            trendingGifViewModel.getSearchedGif(ApiConstants.apiKey, searchKey, limit, offset)
        } else {
            binding?.txvInternetNotFound?.viewVisible()
        }
    }

    private fun initAdapter() {
        gifAdapter = GifAdapter(gifList, {
            if (gifList?.get(it ?: 0)?.isFavourite == false) {
                gifList?.get(it ?: 0)?.isFavourite = true
                trendingGifViewModel.insertGifToFavourite(gifList?.get(it ?: 0))
            } else {
                gifList?.get(it ?: 0)?.isFavourite = false
                (activity as MainActivity?)?.favouriteGifFragment?.deleteGifFromDatabase(
                    gifList?.get(
                        it ?: 0
                    )?.id
                )
            }
        }, {
            getMoreGifData(binding?.edtSearch?.text.toString().trim().lowercase(Locale.ROOT))
        })
        binding?.rcvGif?.adapter = gifAdapter
    }

    private fun getMoreGifData(searchKey: String?) {
        offset++
        if (searchKey.isNullOrEmpty()) {
            getTrendingGif(limit, offset)
        } else {
            getSearchedGif(searchKey, limit, offset)
        }
    }

    private fun initListeners() {
        binding?.swpRefresh?.setOnRefreshListener {
            view?.let { requireContext().hideSoftKeyboard(it) }
            binding?.swpRefresh?.isRefreshing = false
            binding?.edtSearch?.setText("")
            getFavouriteGif()
        }

        binding?.edtSearch?.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getFavouriteGif()
                view?.let { requireContext().hideSoftKeyboard(it) }
                return@OnEditorActionListener true
            }
            false
        })

        addTextChangeListener(binding?.edtSearch, afterTextChange = {
            if (binding?.edtSearch?.text?.isEmpty() == true) {
                getTrendingGif(limit, offset)
            }
        })
    }

    fun callListApi() {
        if (binding?.edtSearch?.text.toString().trim().lowercase().isEmpty()) {
            getTrendingGif(limit, offset)
        } else {
            searchedGifList?.clear()
            getSearchedGif(binding?.edtSearch?.text.toString(), limit, offset)
        }
    }

    private fun initObservers() {
        trendingGifViewModel.trendingGifResponse.observe(viewLifecycleOwner) { trendingGifResponse ->
            populateList(trendingGifResponse, trendingGifList)
        }

        trendingGifViewModel.searchedGifResponse.observe(viewLifecycleOwner) { searchedResponse ->
            populateList(searchedResponse, searchedGifList)
        }

        trendingGifViewModel.favouriteGifResponse.observe(viewLifecycleOwner) {
            favouriteGifList?.clear()
            favouriteGifList?.addAll(it ?: emptyList())
            callListApi()
        }

        trendingGifViewModel.onInsertGifToFavouriteResponse.observe(viewLifecycleOwner) {
            (activity as MainActivity?)?.favouriteGifFragment?.getFavouriteGif()
            gifAdapter?.notifyDataSetChanged()
        }
    }

    private fun populateList(
        apiGifResponse: List<GifResponse>?, apiGifList: MutableList<GifResponse>?
    ) {
        if (!apiGifResponse.isNullOrEmpty()) {
            binding?.txvDataNotFound.viewGone()
            gifList?.clear()
            apiGifList?.addAll(apiGifResponse)
            apiGifList?.forEach { trendingListResponse ->
                trendingListResponse.isFavourite =
                    favouriteGifList?.any { it.id == trendingListResponse.id } == true
            }
            gifList?.addAll(apiGifList ?: emptyList())
            addOrRemoveLoadingForAdapter()
        } else {
            binding?.txvDataNotFound.viewVisible()
        }
        gifAdapter?.notifyDataSetChanged()
    }

    private fun addOrRemoveLoadingForAdapter() {
        val loadingIndex = gifList?.indexOfFirst {
            it.viewType == Constants.showLoading
        }
        if (loadingIndex != null && loadingIndex != -1) {
            gifList?.removeAt(loadingIndex)
        }
        if (gifList?.lastOrNull()?.hasNext == true) {
            gifList?.add(
                GifResponse(viewType = Constants.showLoading, hasNext = true)
            )
        }
    }
}