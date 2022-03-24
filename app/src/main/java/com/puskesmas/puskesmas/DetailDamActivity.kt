package com.puskesmas.puskesmas

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail_dam.*
import org.jetbrains.anko.startActivity

class DetailDamActivity : AppCompatActivity() {
    var dam : String? = null
    var desa : String? = null
    var pemilik : String? = null
    var alamat : String? = null
    var jumlah : String? = null
    var ikl : String? = null
    var sampel : String? = null
    var penjamah : String? = null
    var izin : String? = null
    var laiksehat : String? = null
    var id : Int? = null

    companion object{
        var detail  : Activity? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_dam)
        val bundle: Bundle? = intent.extras
        detail = this
        dam = bundle!!.getString("dam")
        desa = bundle.getString("desa")
        pemilik = bundle.getString("pemilik")
        alamat = bundle.getString("alamat")
        jumlah = bundle.getString("jumlah")
        ikl = bundle.getString("ikl")
        sampel = bundle.getString("sampel")
        penjamah = bundle.getString("penjamah")
        izin = bundle.getString("izin")
        laiksehat = bundle.getString("laiksehat")
        id = bundle.getInt("id")
        DetailLihatDataActivity.detail = this
        txtdam.text = dam.toString()
        txtdesa.text = desa.toString()
        txtnama.text = pemilik.toString()
        txtalamat.text = alamat.toString()
        txtjumlah.text = jumlah.toString()
        txtikl.text = ikl.toString()
        txtsampel.text = sampel.toString()
        txtpenjamah.text = penjamah.toString()
        txtizinusaha.text = izin.toString()

        btnedit.setOnClickListener {
            startActivity<DamiuActivity>(
                "dam" to  dam!!,
                "desa" to  desa!!,
                "pemilik" to  pemilik!!,
                "alamat" to  alamat!!,
                "jumlah" to  jumlah!!,
                "ikl" to  ikl!!,
                "sampel" to  sampel!!,
                "penjamah" to  penjamah!!,
                "izin" to  izin!!,
                "laiksehat" to  laiksehat!!,
                "id" to  id!!,
                )
        }
    }
}