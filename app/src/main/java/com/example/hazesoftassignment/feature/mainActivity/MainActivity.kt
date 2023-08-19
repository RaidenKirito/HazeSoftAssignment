package com.example.hazesoftassignment.feature.mainActivity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.hazesoftassignment.databinding.ActivityMainBinding
import com.example.hazesoftassignment.feature.mainActivity.favouriteGifFragment.FavouriteGifFragment
import com.example.hazesoftassignment.feature.mainActivity.trendingGifFragment.TrendingGifFragment
import com.example.hazesoftassignment.feature.shared.adapter.GifFragmentPagerAdapter
import com.example.hazesoftassignment.feature.shared.base.BaseActivity
import com.example.hazesoftassignment.utils.constants.Constants
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    var trendingGifFragment: TrendingGifFragment? = null
    var favouriteGifFragment: FavouriteGifFragment? = null
    private val fragmentsList: MutableList<Fragment?> = mutableListOf()

    private val mainViewModel: MainViewModel by viewModels()

    override fun getViewModel() = mainViewModel

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    private fun setUp() {
        initViewPagerAdapter()
    }

    private fun initViewPagerAdapter() {
        trendingGifFragment = TrendingGifFragment()
        favouriteGifFragment = FavouriteGifFragment()

        fragmentsList.add(trendingGifFragment)
        fragmentsList.add(favouriteGifFragment)

        val adapter = GifFragmentPagerAdapter(this, fragmentsList)
        binding?.vpgGifFragment?.offscreenPageLimit = 4
        binding?.vpgGifFragment?.adapter = adapter

        binding?.tblGifFragment?.let {
            binding?.vpgGifFragment?.let { it1 ->
                TabLayoutMediator(it, it1) { tab, position ->
                    when (position) {
                        0 -> {
                            tab.text = Constants.Trending
                        }
                        1 -> {
                            tab.text = Constants.Favourite
                        }
                    }
                }.attach()
            }
        }
    }
}