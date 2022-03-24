package com.puskesmas.puskesmas.model

import com.google.gson.annotations.SerializedName

data class TpmResponse(

	@field:SerializedName("data")
	val data: List<TpmModel>? = null,

	@field:SerializedName("message")
	val message: String? = null
)
