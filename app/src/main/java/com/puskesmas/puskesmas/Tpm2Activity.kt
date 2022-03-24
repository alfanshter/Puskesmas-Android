package com.puskesmas.puskesmas

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.android.material.snackbar.Snackbar
import com.puskesmas.puskesmas.model.PostResponse
import com.puskesmas.puskesmas.session.SessionManager
import com.puskesmas.puskesmas.werbservice.ApiClient
import kotlinx.android.synthetic.main.activity_tpm2.*
import kotlinx.android.synthetic.main.activity_tpm2.btnselesai
import kotlinx.android.synthetic.main.activity_tpm2.radioikl
import kotlinx.android.synthetic.main.activity_tpm2.radioizinusaha
import kotlinx.android.synthetic.main.activity_tpm2.radiolaksehat
import kotlinx.android.synthetic.main.activity_tpm2.radioujisampel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Tpm2Activity : AppCompatActivity(), AnkoLogger {
    var desa: String? = null
    var tpm: String? = null
    var nama: String? = null
    var alamat: String? = null
    var jumlahkaryawan: String? = null
    var golongan: String? = null
    var ikl: String? = null
    var ujisampel: String? = null
    var penjamah: String? = null
    var laiksehat: String? = null
    var ijinusaha: String? = null
    var status: Int? = null
    var id: Int? = null
    lateinit var progressDialog: ProgressDialog
    lateinit var sessionManager: SessionManager
    var api = ApiClient.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tpm2)
        val bundle: Bundle? = intent.extras
        progressDialog = ProgressDialog(this)
        status = bundle!!.getInt("status")
        desa = bundle.getString("desa")
        tpm = bundle.getString("tpm")
        nama = bundle.getString("nama")
        alamat = bundle.getString("alamat")
        jumlahkaryawan = bundle.getString("jumlahkaryawan")
        golongan = bundle.getString("golongan")
        if (status == 1) {
            btnselesai.visibility = View.GONE
            btnupdate.visibility = View.VISIBLE
            ikl = bundle.getString("ikl")
            ujisampel = bundle.getString("sampel")
            penjamah = bundle.getString("penjamah")
            laiksehat = bundle.getString("laiksehat")
            ijinusaha = bundle.getString("izin")
            id = bundle.getInt("id")
            //IKL
            if (ikl == "Memenuhi Syarat") {
                    ikl0.isChecked = true
            } else if (ikl == "Tidak Memenuhi Syarat") {
                    ikl1.isChecked = true
            }
            //Sampel
            if (ujisampel == "Memenuhi Syarat") {
                sampel0.isChecked = true
            } else if (ujisampel == "Tidak Memenuhi Syarat") {
                sampel1.isChecked = true
            } else if (ujisampel == "Belum Uji Sampel") {
                sampel2.isChecked = true
            }

            info { "dinda $penjamah lak $laiksehat izin $ijinusaha" }
            //penjamah
            if (penjamah == "Ada") {
                penjamaah0.isChecked = true
            } else if (penjamah == "Belum Ada") {
                penjamaah1.isChecked = true            }

            //Laik Sehat
            if (laiksehat == "Ada") {
                laiksehat0.isChecked = true
            } else if (laiksehat == "Belum Ada") {
                laiksehat1.isChecked = true
            }

            //Izin
            if (ijinusaha == "Ada") {
                izinusaha0.isChecked = true
            } else if (ijinusaha == "Belum Ada") {
                izinusaha1.isChecked = true
            }


        }



        radioikl.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { radioGroup, i ->
                val radio: RadioButton = findViewById(i)
                ikl = radio.text.toString()
            }
        )

        radioujisampel.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { radioGroup, i ->
                val radio: RadioButton = findViewById(i)
                ujisampel = radio.text.toString()
            }
        )

        radiopenjamaah.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { radioGroup, i ->
                val radio: RadioButton = findViewById(i)
                penjamah = radio.text.toString()
            }
        )

        radiolaksehat.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { radioGroup, i ->
                val radio: RadioButton = findViewById(i)
                laiksehat = radio.text.toString()
            }
        )

        radioizinusaha.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { radioGroup, i ->
                val radio: RadioButton = findViewById(i)
                ijinusaha = radio.text.toString()
            }
        )

        btnselesai.setOnClickListener {
            if (desa != null && tpm != null && nama != null && golongan != null
                && alamat != null && jumlahkaryawan != null && ikl != null && ujisampel != null
                && penjamah != null && penjamah != null && laiksehat != null && ijinusaha != null
            ) {
                progressDialog.setTitle("Loading...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                api.inserttpm(
                    tpm!!,
                    desa!!,
                    nama!!,
                    alamat!!,
                    golongan!!,
                    jumlahkaryawan!!,
                    ikl!!,
                    ujisampel!!,
                    penjamah!!,
                    laiksehat!!,
                    ijinusaha!!
                ).enqueue(object : Callback<PostResponse> {
                    override fun onResponse(
                        call: Call<PostResponse>,
                        response: Response<PostResponse>
                    ) {
                        try {
                            if (response.isSuccessful) {
                                if (response.body()!!.data == 1) {
                                    progressDialog.dismiss()
                                    TpmActivity.fa!!.finish()
                                    finish()
                                    toast("berhasil input data")
                                } else {
                                    progressDialog.dismiss()
                                    toast("gagal input data")
                                }


                            } else {
                                progressDialog.dismiss()
                                toast("kesalahan aplikasi")
                            }
                        } catch (e: Exception) {
                            progressDialog.dismiss()
                            info { "dinda ${e.message}" }
                        }
                    }

                    override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                        progressDialog.dismiss()
                        info { "dinda ${t.message}" }
                    }

                })
            } else {
                Snackbar.make(it, "Jangan kosongi kolom", 3000).show()
            }
        }

        btnupdate.setOnClickListener {
            if (desa != null && tpm != null && nama != null && golongan != null
                && alamat != null && jumlahkaryawan != null && ikl != null && ujisampel != null
                && penjamah != null && penjamah != null && laiksehat != null && ijinusaha != null
            ) {
                progressDialog.setTitle("Loading...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                api.updatetpm(
                    id!!,
                    tpm!!,
                    desa!!,
                    nama!!,
                    alamat!!,
                    golongan!!,
                    jumlahkaryawan!!,
                    ikl!!,
                    ujisampel!!,
                    penjamah!!,
                    laiksehat!!,
                    ijinusaha!!
                ).enqueue(object : Callback<PostResponse> {
                    override fun onResponse(
                        call: Call<PostResponse>,
                        response: Response<PostResponse>
                    ) {
                        try {
                            if (response.isSuccessful) {
                                if (response.body()!!.data == 1) {
                                    progressDialog.dismiss()
                                    TpmActivity.fa!!.finish()
                                    DetailLihatDataActivity.detail!!.finish()
                                    finish()
                                    toast("berhasil update data")
                                } else {
                                    progressDialog.dismiss()
                                    toast("gagal update data")
                                }


                            } else {
                                progressDialog.dismiss()
                                toast("kesalahan aplikasi")
                            }
                        } catch (e: Exception) {
                            progressDialog.dismiss()
                            info { "dinda ${e.message}" }
                        }
                    }

                    override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                        progressDialog.dismiss()
                        info { "dinda ${t.message}" }
                    }

                })
            } else {
                Snackbar.make(it, "Jangan kosongi kolom", 3000).show()
            }
        }
    }
}