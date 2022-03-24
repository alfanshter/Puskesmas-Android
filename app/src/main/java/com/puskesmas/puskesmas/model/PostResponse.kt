package com.puskesmas.puskesmas.model

import com.google.gson.annotations.SerializedName

data class PostResponse(

	@field:SerializedName("data")
	val data: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)
