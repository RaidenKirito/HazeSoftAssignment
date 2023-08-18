package com.example.hazesoftassignment.feature.shared.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hazesoftassignment.databinding.ItemGifBinding
import com.example.hazesoftassignment.feature.shared.base.BaseAdapter
import com.example.hazesoftassignment.feature.shared.model.response.gifResponse.GifResponse
import com.example.hazesoftassignment.feature.shared.viewHolder.GifViewHolder

class FavouriteGifAdapter(
    private val gifList: MutableList<GifResponse>?,
    private val onGifResponseClicked: (position: Int?) -> Unit
) : BaseAdapter<GifViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GifViewHolder(
        ItemGifBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), onGifResponseClicked
    )

    override fun getItemCount() = gifList?.size ?: 0

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        holder.bind(gifList?.get(position))
    }
}