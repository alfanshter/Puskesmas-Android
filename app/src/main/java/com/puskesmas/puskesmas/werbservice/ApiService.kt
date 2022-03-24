package com.puskesmas.puskesmas.werbservice

import com.puskesmas.puskesmas.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("hapusakun")
    fun hapusakun(
        @Field("id") id : Int
    ): Call<PostResponse>

    //Get Grafik DAMIU desa
    @GET("getakunall")
    fun getakunall(): Call<UsersResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("nama") nama : String,
        @Field("alamat") alamat: String,
        @Field("username") username: String,
        @Field("role") role: String,
        @Field("password") password: String
    ): Call<PostResponse>


    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username : String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("insertdam")
    fun insertdam(
        @Field("dam") dam : String,
        @Field("desa") desa: String,
        @Field("pemilik") pemilik: String,
        @Field("alamat") alamat: String,
        @Field("karyawan") karyawan: String,
        @Field("ikl") ikl: String,
        @Field("ujisampel") ujisampel: String,
        @Field("sertifikatpenjamaah") sertifikatpenjamaah: String,
        @Field("laiksehat") laiksehat: String,
        @Field("izinusaha") izinusaha: String
    ): Call<PostResponse>

    @FormUrlEncoded
    @POST("inserttpm")
    fun inserttpm(
        @Field("tpm") tpm : String,
        @Field("desa") desa: String,
        @Field("pemilik") pemilik: String,
        @Field("alamat") alamat: String,
        @Field("golongan") golongan: String,
        @Field("karyawan") karyawan: String,
        @Field("ikl") ikl: String,
        @Field("ujisampel") ujisampel: String,
        @Field("sertifikatpenjamaah") sertifikatpenjamaah: String,
        @Field("laiksehat") laiksehat: String,
        @Field("izinusaha") izinusaha: String
    ): Call<PostResponse>

    @FormUrlEncoded
    @POST("updatetpm")
    fun updatetpm(
        @Field("id") id : Int,
        @Field("tpm") tpm : String,
        @Field("desa") desa: String,
        @Field("pemilik") pemilik: String,
        @Field("alamat") alamat: String,
        @Field("golongan") golongan: String,
        @Field("karyawan") karyawan: String,
        @Field("ikl") ikl: String,
        @Field("ujisampel") ujisampel: String,
        @Field("sertifikatpenjamaah") sertifikatpenjamaah: String,
        @Field("laiksehat") laiksehat: String,
        @Field("izinusaha") izinusaha: String
    ): Call<PostResponse>

    @FormUrlEncoded
    @POST("updatedam")
    fun updatedam(
        @Field("id") id : Int,
        @Field("dam") dam : String,
        @Field("desa") desa: String,
        @Field("pemilik") pemilik: String,
        @Field("alamat") alamat: String,
        @Field("karyawan") karyawan: String,
        @Field("ikl") ikl: String,
        @Field("ujisampel") ujisampel: String,
        @Field("sertifikatpenjamaah") sertifikatpenjamaah: String,
        @Field("laiksehat") laiksehat: String,
        @Field("izinusaha") izinusaha: String
    ): Call<PostResponse>


    //Get TPM desa
    @GET("readtpmdesa")
    fun readtpmdesa(
        @Query("desa") desa : String
    ): Call<TpmResponse>

    //Get TPM ALl
    @GET("readtpmall")
    fun readtpmall(): Call<TpmResponse>


    //Get DAM ALl
    @GET("readdamall")
    fun readdamall(): Call<TpmResponse>

    //Get TPM desa
    @GET("readdamdesa")
    fun readdamdesa(
        @Query("desa") desa : String
    ): Call<TpmResponse>

    //Get Grafik DAMIU desa
    @GET("getdatadam")
    fun getdatadam(
        @Query("desa") desa : String
    ): Call<GrafikResponse>

    //Get Grafik DAMIU desa
    @GET("getdatatpm")
    fun getdatatpm(
        @Query("desa") desa : String
    ): Call<GrafikResponse>


    @FormUrlEncoded
    @POST("deletetpm")
    fun deletetpm(
        @Field("id") id : Int
    ): Call<PostResponse>

    @FormUrlEncoded
    @POST("deletedam")
    fun deletedam(
        @Field("id") id : Int
    ): Call<PostResponse>



}