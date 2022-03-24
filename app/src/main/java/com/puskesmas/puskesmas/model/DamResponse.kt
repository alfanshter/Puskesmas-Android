package com.puskesmas.puskesmas.model

import com.google.gson.annotations.SerializedName

data class DamResponse(

	@field:SerializedName("data")
	val data: DamModel? = null,

	@field:SerializedName("message")
	val message: String? = null
)

