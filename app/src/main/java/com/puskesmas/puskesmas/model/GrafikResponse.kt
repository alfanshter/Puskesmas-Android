package com.puskesmas.puskesmas.model

import com.google.gson.annotations.SerializedName

data class GrafikResponse(

	@field:SerializedName("laiksehat1")
	val laiksehat1: Int? = null,

	@field:SerializedName("laiksehat0")
	val laiksehat0: Int? = null,

	@field:SerializedName("ikl1")
	val ikl1: Int? = null,

	@field:SerializedName("ikl0")
	val ikl0: Int? = null,

	@field:SerializedName("izin0")
	val izin0: Int? = null,

	@field:SerializedName("izin1")
	val izin1: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("penjamaah1")
	val penjamaah1: Int? = null,

	@field:SerializedName("sampel2")
	val sampel2: Int? = null,

	@field:SerializedName("sampel1")
	val sampel1: Int? = null,

	@field:SerializedName("sampel0")
	val sampel0: Int? = null,

	@field:SerializedName("penjamaah0")
	val penjamaah0: Int? = null
)
