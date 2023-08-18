package com.example.hazesoftassignment.feature.shared.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hazesoftassignment.databinding.ItemGifBinding
import com.example.hazesoftassignment.databinding.LayoutLoadingBinding
import com.example.hazesoftassignment.feature.shared.base.BaseAdapter
import com.example.hazesoftassignment.feature.shared.base.BaseViewHolder
import com.example.hazesoftassignment.feature.shared.model.response.gifResponse.GifResponse
import com.example.hazesoftassignment.feature.shared.viewHolder.GifViewHolder
import com.example.hazesoftassignment.feature.shared.viewHolder.LoadingViewHolder
import com.example.hazesoftassignment.utils.constants.Constants

class GifAdapter(
    private val gifList: List<GifResponse>?,
    private val onGifResponseClicked: (position: Int?) -> Unit,
    var onLastIndexReached: () -> Unit
) : BaseAdapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            Constants.gifListViewType -> {
                return GifViewHolder(
                    ItemGifBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ), onGifResponseClicked
                )
            }

            else -> {
                return LoadingViewHolder(
                    LayoutLoadingBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }

        }
    }

    override fun getItemCount() = gifList?.size ?: 0

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (getItemViewType(position)) {
            Constants.gifListViewType -> {
                holder as GifViewHolder
                holder.bind(gifList?.get(position))
            }
            else -> {
                populateProgress(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (gifList?.get(position)?.viewType) {
            Constants.gifListView -> Constants.gifListViewType
            else -> Constants.showLoadingSection
        }
    }

    private fun populateProgress(position: Int) {
        if (position == ((gifList?.count()
                ?: 0) - 1) && gifList?.lastOrNull()?.hasNext == true
        ) {
            onLastIndexReached()
        }
    }
}