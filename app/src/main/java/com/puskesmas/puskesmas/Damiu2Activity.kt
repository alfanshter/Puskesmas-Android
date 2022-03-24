package com.puskesmas.puskesmas

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
import kotlinx.android.synthetic.main.activity_damiu2.*
import kotlinx.android.synthetic.main.activity_damiu2.btnselesai
import kotlinx.android.synthetic.main.activity_damiu2.btnupdate
import kotlinx.android.synthetic.main.activity_damiu2.ikl0
import kotlinx.android.synthetic.main.activity_damiu2.ikl1
import kotlinx.android.synthetic.main.activity_damiu2.izinusaha0
import kotlinx.android.synthetic.main.activity_damiu2.izinusaha1
import kotlinx.android.synthetic.main.activity_damiu2.laiksehat0
import kotlinx.android.synthetic.main.activity_damiu2.laiksehat1
import kotlinx.android.synthetic.main.activity_damiu2.penjamaah0
import kotlinx.android.synthetic.main.activity_damiu2.penjamaah1
import kotlinx.android.synthetic.main.activity_damiu2.radioikl
import kotlinx.android.synthetic.main.activity_damiu2.radioizinusaha
import kotlinx.android.synthetic.main.activity_damiu2.radiolaksehat
import kotlinx.android.synthetic.main.activity_damiu2.radiopenjamaah
import kotlinx.android.synthetic.main.activity_damiu2.radioujisampel
import kotlinx.android.synthetic.main.activity_damiu2.sampel0
import kotlinx.android.synthetic.main.activity_damiu2.sampel1
import kotlinx.android.synthetic.main.activity_damiu2.sampel2
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Damiu2Activity : AppCompatActivity(),AnkoLogger {

    var desa: String? = null
    var dam: String? = null
    var nama: String? = null
    var alamat: String? = null
    var jumlahkaryawan: String? = null
    var ikl: String? = null
    var ujisampel: String? = null
    var penjamaah: String? = null
    var laiksehat: String? = null
    var ijinusaha: String? = null
    var status: Int? = null
    var id: Int? = null
    lateinit var progressDialog: ProgressDialog
    lateinit var sessionManager: SessionManager
    var api = ApiClient.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_damiu2)
        val bundle: Bundle? = intent.extras
        progressDialog = ProgressDialog(this)
        desa = bundle!!.getString("desa")
        dam = bundle.getString("dam")
        status = bundle.getInt("status")
        id = bundle.getInt("status")
        nama = bundle.getString("nama")
        alamat = bundle.getString("alamat")
        jumlahkaryawan = bundle.getString("jumlahkaryawan")

        if (status == 1) {
            btnselesai.visibility = View.GONE
            btnupdate.visibility = View.VISIBLE
            ikl = bundle.getString("ikl")
            ujisampel = bundle.getString("sampel")
            penjamaah = bundle.getString("penjamah")
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

            //penjamah
            if (penjamaah == "Ada") {
                penjamaah0.isChecked = true
            } else if (penjamaah == "Belum Ada") {
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
                penjamaah = radio.text.toString()
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
            if (desa != null && dam != null && nama != null
                && alamat != null && jumlahkaryawan != null && ikl != null && ujisampel != null
                && penjamaah != null && penjamaah != null && laiksehat != null && ijinusaha != null
            ) {
                progressDialog.setTitle("Loading...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                api.insertdam(
                    dam!!,
                    desa!!,
                    nama!!,
                    alamat!!,
                    jumlahkaryawan!!,
                    ikl!!,
                    ujisampel!!,
                    penjamaah!!,
                    laiksehat!!,
                    ijinusaha!!
                ).enqueue(object : Callback<PostResponse> {
                    override fun onResponse(
                        call: Call<PostResponse>,
                        response: Response<PostResponse>
                    ) {
                        try {
                            if (response.isSuccessful) {
                                if (response.body()!!.data ==1) {
                                    progressDialog.dismiss()
                                    DamiuActivity.fa!!.finish()
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
            if (desa != null && dam != null && nama != null
                && alamat != null && jumlahkaryawan != null && ikl != null && ujisampel != null
                && penjamaah != null && laiksehat != null && ijinusaha != null
            ) {
                progressDialog.setTitle("Loading...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()
                api.updatedam(
                    id!!,
                    dam!!,
                    desa!!,
                    nama!!,
                    alamat!!,
                    jumlahkaryawan!!,
                    ikl!!,
                    ujisampel!!,
                    penjamaah!!,
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
                                    DamiuActivity.fa!!.finish()
                                    DetailDamActivity.detail!!.finish()
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
                        toast("kesalahan jaringan atau server")
                        info { "dinda ${t.message}" }
                    }

                })
            } else {
                Snackbar.make(it, "Jangan kosongi kolom", 3000).show()
            }
        }
    }
}