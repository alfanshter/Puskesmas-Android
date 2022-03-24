package com.puskesmas.puskesmas

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_damiu.*
import kotlinx.android.synthetic.main.activity_damiu.btnselanjutnya
import kotlinx.android.synthetic.main.activity_damiu.edtalamat
import kotlinx.android.synthetic.main.activity_damiu.edtjumlah
import kotlinx.android.synthetic.main.activity_damiu.edtnama
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class DamiuActivity : AppCompatActivity() {
    var desa: String? = null
    var laiksehat: String? = null

    var dam: String? = null
    var pemilik: String? = null
    var alamat: String? = null
    var jumlah: String? = null
    var ikl: String? = null
    var sampel: String? = null
    var penjamah: String? = null
    var izin: String? = null
    var id: Int? = null

    companion object{
        var fa : Activity? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_damiu)
        val bundle: Bundle? = intent.extras
        fa = this
        if (bundle!=null){
            dam = bundle.getString("dam")
            pemilik = bundle.getString("pemilik")
            alamat = bundle.getString("alamat")
            jumlah = bundle.getString("jumlah")
            ikl = bundle.getString("ikl")
            sampel = bundle.getString("sampel")
            penjamah = bundle.getString("penjamah")
            izin = bundle.getString("izin")
            desa = bundle.getString("desa")
            laiksehat = bundle.getString("laiksehat")
            id = bundle.getInt("id")

            edtdam.setText(dam.toString())
            edtnama.setText(pemilik.toString())
            edtalamat.setText(alamat.toString())
            edtjumlah.setText(jumlah.toString())
            spndesaupdate()
            btnselanjutnya.visibility = View.GONE
            btnupdate.visibility = View.VISIBLE
            btnupdate.setOnClickListener {
                val dam = edtdam.text.toString().trim()
                val nama = edtnama.text.toString().trim()
                val alamat = edtalamat.text.toString().trim()
                val jumlahkaryawan = edtjumlah.text.toString().trim()
                if (desa != null  && dam.isNotEmpty() && nama.isNotEmpty() && alamat.isNotEmpty() && jumlahkaryawan.isNotEmpty()
                ){
                    startActivity<Damiu2Activity>(
                        "desa" to desa,
                        "dam" to dam,
                        "nama" to nama,
                        "alamat" to alamat,
                        "jumlahkaryawan" to jumlahkaryawan,
                        "ikl" to ikl,
                        "sampel" to sampel,
                        "penjamah" to penjamah,
                        "izin" to izin,
                        "laiksehat" to laiksehat,
                        "status" to 1,
                        "id" to id

                    )
                }else{
                    Snackbar.make(it,"Jangan kosongi Kolom",3000).show()
                }
            }
        }
        else{
            spndesa()
            btnselanjutnya.setOnClickListener {
                proses(it)
            }
        }
    }

    private fun proses(view : View) {
        val dam = edtdam.text.toString().trim()
        val nama = edtnama.text.toString().trim()
        val alamat = edtalamat.text.toString().trim()
        val jumlahkaryawan = edtjumlah.text.toString().trim()
        if (desa != null &&  dam.isNotEmpty()
            && nama.isNotEmpty() && alamat.isNotEmpty() && jumlahkaryawan.isNotEmpty()
        ) {
            startActivity<Damiu2Activity>(
                "desa" to desa,
                "dam" to dam,
                "nama" to nama,
                "alamat" to alamat,
                "jumlahkaryawan" to jumlahkaryawan
            )
        }else{
            Snackbar.make(view,"Jangan kosongi Kolom",3000).show()
        }
    }


    fun spndesa() {
        val datakelamin = arrayOf(
            "Kel.Mendawai",
            "Kel.Padang",
            "Desa Pudu",
            "Desa Natai Sedawak",
            "Desa Kartamulia",
            "Desa Sukaraja",
            "Desa Pangkalan Muntai",
            "Desa Petarikan"
        )

        val spinner = find<Spinner>(R.id.spndesa)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, datakelamin
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    desa = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    fun spndesaupdate() {
        val datakelamin = arrayOf(
            desa,
            "Kel.Mendawai",
            "Kel.Padang",
            "Desa Pudu",
            "Desa Natai Sedawak",
            "Desa Kartamulia",
            "Desa Sukaraja",
            "Desa Pangkalan Muntai",
            "Desa Petarikan"
        )

        val spinner = find<Spinner>(R.id.spndesa)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, datakelamin
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    desa = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }

}