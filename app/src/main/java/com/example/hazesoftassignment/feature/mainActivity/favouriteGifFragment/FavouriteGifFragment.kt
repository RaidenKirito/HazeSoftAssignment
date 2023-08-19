package com.example.hazesoftassignment.feature.mainActivity.favouriteGifFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hazesoftassignment.databinding.FragmentFavouriteGifBinding
import com.example.hazesoftassignment.feature.mainActivity.MainActivity
import com.example.hazesoftassignment.feature.shared.adapter.FavouriteGifAdapter
import com.example.hazesoftassignment.feature.shared.base.BaseFragment
import com.example.hazesoftassignment.feature.shared.model.response.gifResponse.GifResponse
import com.example.hazesoftassignment.utils.extensions.viewGone
import com.example.hazesoftassignment.utils.extensions.viewVisible
import com.example.hazesoftassignment.utils.util.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteGifFragment : BaseFragment<FragmentFavouriteGifBinding, FavouriteGifViewModel>() {
    private val favouriteGifViewModel: FavouriteGifViewModel by viewModels()
    private var favouriteGifAdapter: FavouriteGifAdapter? = null
    private var favouriteGifList: MutableList<GifResponse>? = mutableListOf()

    override fun getViewModel() = favouriteGifViewModel

    override fun getViewBinding() = FragmentFavouriteGifBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        initObservers()
        initAdapter()
        getFavouriteGif()
        initListeners()
    }

    private fun initListeners() {
        binding?.swpRefresh?.setOnRefreshListener {
            binding?.swpRefresh?.isRefreshing = false
            getFavouriteGif()
        }
    }

    private fun initAdapter() {
        binding?.rcvGif?.layoutManager = GridLayoutManager(requireContext(), 3)
        favouriteGifAdapter = FavouriteGifAdapter(favouriteGifList) {
            deleteGifFromDatabase(favouriteGifList?.get(it ?: 0)?.id)
        }

        binding?.rcvGif?.adapter = favouriteGifAdapter
    }

    fun deleteGifFromDatabase(gifId: String?) {
        (activity as MainActivity?)?.trendingGifFragment?.gifList?.find { it.id == gifId }?.isFavourite =
            false

        if (favouriteGifList.isNullOrEmpty()) {
            showMessageDialog("List already empty")
        } else {
            favouriteGifViewModel.deleteGifFromFavourite(gifId)
        }
    }

    fun getFavouriteGif() {
        favouriteGifViewModel.getFavouriteGif()
    }

    private fun initObservers() {
        favouriteGifViewModel.favouriteGifResponse.observe(viewLifecycleOwner) {
            hideLoading()
            if (!it.isNullOrEmpty()) {
                binding?.txvDataNotFound.viewGone()
                favouriteGifList?.clear()
                favouriteGifList?.addAll(it)
            } else {
                binding?.txvDataNotFound.viewVisible()
            }
            favouriteGifAdapter?.notifyDataSetChanged()
        }

        favouriteGifViewModel.onDeleteGifFromFavouriteResponse.observe(viewLifecycleOwner) {
            if (NetworkUtils.isNetworkAvailable(context)) {
                (activity as MainActivity?)?.trendingGifFragment?.callListApi()
            }
            getFavouriteGif()
        }
    }
}