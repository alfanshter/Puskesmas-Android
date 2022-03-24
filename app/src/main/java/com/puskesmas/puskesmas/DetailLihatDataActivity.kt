package com.puskesmas.puskesmas

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail_lihat_data.*
import org.jetbrains.anko.startActivity

class DetailLihatDataActivity : AppCompatActivity() {
    var tpm : String? = null
    var desa : String? = null
    var pemilik : String? = null
    var alamat : String? = null
    var jumlah : String? = null
    var ikl : String? = null
    var sampel : String? = null
    var penjamah : String? = null
    var izin : String? = null
    var laiksehat : String? = null
    var golongan : String? = null
    var id : Int? = null

    companion object{
        var detail  : Activity? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lihat_data)
        val bundle: Bundle? = intent.extras
        tpm = bundle!!.getString("tpm")
        desa = bundle.getString("desa")
        pemilik = bundle.getString("pemilik")
        alamat = bundle.getString("alamat")
        jumlah = bundle.getString("jumlah")
        ikl = bundle.getString("ikl")
        sampel = bundle.getString("sampel")
        penjamah = bundle.getString("penjamah")
        izin = bundle.getString("izin")
        golongan = bundle.getString("golongan")
        laiksehat = bundle.getString("laiksehat")
        id = bundle.getInt("id")
        detail = this
        txttpm.text = tpm.toString()
        txtdesa.text = desa.toString()
        txtnama.text = pemilik.toString()
        txtalamat.text = alamat.toString()
        txtjumlah.text = jumlah.toString()
        txtikl.text = ikl.toString()
        txtsampel.text = sampel.toString()
        txtpenjamah.text = penjamah.toString()
        txtizinusaha.text = izin.toString()
        txtgolongan.text = golongan.toString()

        btnedit.setOnClickListener {
            startActivity<TpmActivity>(
                "tpm" to  tpm!!,
                "desa" to  desa!!,
                "pemilik" to  pemilik!!,
                "alamat" to  alamat!!,
                "jumlah" to  jumlah!!,
                "ikl" to  ikl!!,
                "sampel" to  sampel!!,
                "penjamah" to  penjamah!!,
                "izin" to  izin!!,
                "golongan" to  golongan!!,
                "laiksehat" to  laiksehat!!,
                "id" to  id!!,

            )
        }

    }
}