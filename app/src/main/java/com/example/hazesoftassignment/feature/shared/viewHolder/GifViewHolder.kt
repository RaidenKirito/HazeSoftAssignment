package com.example.hazesoftassignment.feature.shared.viewHolder

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.hazesoftassignment.R
import com.example.hazesoftassignment.databinding.ItemGifBinding
import com.example.hazesoftassignment.feature.shared.base.BaseViewHolder
import com.example.hazesoftassignment.feature.shared.model.response.gifResponse.GifResponse
import com.example.hazesoftassignment.utils.extensions.viewGone
import com.example.hazesoftassignment.utils.extensions.viewVisible

class GifViewHolder(
    private val binding: ItemGifBinding, private val onGifFavouriteClicked: (position: Int?) -> Unit
) : BaseViewHolder(binding.root) {

    init {
        binding.imvFav.setOnClickListener {
            onGifFavouriteClicked(adapterPosition)
        }
    }

    fun bind(gifResponse: GifResponse?) {
        if (gifResponse?.images?.original?.url.isNullOrEmpty()) {
            binding.imgProgress.viewGone()
            Glide.with(binding.root.context).load(R.drawable.ic_playerlist_thumbnail)
                .error(R.drawable.ic_playerlist_thumbnail).into(binding.imvGif)
        } else {
            binding.imgProgress.viewVisible()
            Glide.with(binding.root.context).load(gifResponse?.images?.original?.url)
                .error(R.drawable.ic_playerlist_thumbnail).into(binding.imvGif)
        }

        if (gifResponse?.isFavourite == true) {
            binding.imvFav.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context, R.drawable.ic_fav
                )
            )
        } else {
            binding.imvFav.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context, R.drawable.ic_unfav
                )
            )
        }
    }
}