package com.puskesmas.puskesmas.model

import com.google.gson.annotations.SerializedName


data class TpmModel(

    @field:SerializedName("desa")
    val desa: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("ikl")
    val ikl: String? = null,

    @field:SerializedName("laiksehat")
    val laiksehat: String? = null,

    @field:SerializedName("izinusaha")
    val izinusaha: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("tpm")
    val tpm: String? = null,

    @field:SerializedName("dam")
    val dam: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("ujisampel")
    val ujisampel: String? = null,

    @field:SerializedName("golongan")
    val golongan: String? = null,

    @field:SerializedName("pemilik")
    val pemilik: String? = null,

    @field:SerializedName("karyawan")
    val karyawan: String? = null,

    @field:SerializedName("sertifikatpenjamaah")
    val sertifikatpenjamaah: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)
