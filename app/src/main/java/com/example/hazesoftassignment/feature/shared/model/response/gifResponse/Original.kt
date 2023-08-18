package com.example.hazesoftassignment.feature.shared.model.response.gifResponse

import com.google.gson.annotations.SerializedName

data class Original(

	@SerializedName("mp4")
	val mp4: String? = null,

	@SerializedName("size")
	val size: String? = null,

	@SerializedName("frames")
	val frames: String? = null,

	@SerializedName("width")
	val width: String? = null,

	@SerializedName("mp4_size")
	val mp4Size: String? = null,

	@SerializedName("webp")
	val webp: String? = null,

	@SerializedName("webp_size")
	val webpSize: String? = null,

	@SerializedName("url")
	val url: String? = null,

	@SerializedName("hash")
	val hash: String? = null,

	@SerializedName("height")
	val height: String? = null
)