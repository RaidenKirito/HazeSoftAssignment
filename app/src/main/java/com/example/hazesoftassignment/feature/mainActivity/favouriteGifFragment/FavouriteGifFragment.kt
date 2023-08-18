package com.example.hazesoftassignment.feature.mainActivity.favouriteGifFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hazesoftassignment.databinding.FragmentFavouriteGifBinding
import com.example.hazesoftassignment.feature.mainActivity.MainActivity
import com.example.hazesoftassignment.feature.shared.adapter.FavouriteGifAdapter
import com.example.hazesoftassignment.feature.shared.base.BaseFragment
import com.example.hazesoftassignment.feature.shared.model.response.gifResponse.GifResponse
import com.example.hazesoftassignment.utils.extensions.viewGone
import com.example.hazesoftassignment.utils.extensions.viewVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteGifFragment : BaseFragment<FragmentFavouriteGifBinding, FavouriteGifViewModel>() {
    private val favouriteGifViewModel: FavouriteGifViewModel by viewModels()
    private var favouriteGifAdapter: FavouriteGifAdapter? = null
    private var favouriteGifList: MutableList<GifResponse>? = null

    override fun getViewModel() = favouriteGifViewModel

    override fun getViewBinding() = FragmentFavouriteGifBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    private fun setUp() {
        initAdapter()
        initObservers()
        getFavouriteList()
    }

    fun getFavouriteList() {
        Log.d("FavouriteListFromFunctionStart", favouriteGifList?.size.toString())
        favouriteGifViewModel.getFavouriteGif()
    }

    private fun initAdapter() {
        binding?.rcvGif?.layoutManager = LinearLayoutManager(requireContext())
        favouriteGifAdapter = FavouriteGifAdapter(favouriteGifList) {
            favouriteGifViewModel.deleteGifFromFavourite(
                favouriteGifList?.get(
                    it ?: 0
                )?.roomId
            )
        }

        binding?.rcvGif?.adapter = favouriteGifAdapter
    }

    private fun initObservers() {
        favouriteGifViewModel.favouriteGifResponse.observe(viewLifecycleOwner) {
            Log.d("FavouriteListFromFunctionEnd", it?.size.toString())
            if (!it.isNullOrEmpty()) {
                binding?.txvDataNotFound.viewGone()
                favouriteGifList = mutableListOf()
                favouriteGifList?.clear()
                favouriteGifList?.addAll(it as MutableList)
            } else {
                binding?.txvDataNotFound.viewVisible()
            }
            favouriteGifAdapter?.notifyDataSetChanged()
        }

        favouriteGifViewModel.onDeleteGifFromFavouriteResponse.observe(viewLifecycleOwner) {
            getFavouriteList()
            (requireActivity() as MainActivity).trendingGifFragment?.getFavouriteGif()
        }
    }
}