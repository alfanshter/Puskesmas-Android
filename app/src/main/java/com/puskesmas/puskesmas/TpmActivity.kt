package com.puskesmas.puskesmas

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_tpm.*
import kotlinx.android.synthetic.main.activity_tpm.btnselanjutnya
import kotlinx.android.synthetic.main.activity_tpm.edtalamat
import kotlinx.android.synthetic.main.activity_tpm.edtjumlah
import kotlinx.android.synthetic.main.activity_tpm.edtnama
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class TpmActivity : AppCompatActivity() {
    companion object{
        var fa: Activity? = null
    }
    var desa: String? = null
    var golongan: String? = null
    var laiksehat: String? = null

    var tpm: String? = null
    var pemilik: String? = null
    var alamat: String? = null
    var jumlah: String? = null
    var ikl: String? = null
    var sampel: String? = null
    var penjamah: String? = null
    var izin: String? = null
    var id: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tpm)
        val bundle: Bundle? = intent.extras
        fa = this
        if (bundle!=null){
            tpm = bundle.getString("tpm")
            pemilik = bundle.getString("pemilik")
            alamat = bundle.getString("alamat")
            jumlah = bundle.getString("jumlah")
            ikl = bundle.getString("ikl")
            sampel = bundle.getString("sampel")
            penjamah = bundle.getString("penjamah")
            izin = bundle.getString("izin")
            golongan = bundle.getString("golongan")
            desa = bundle.getString("desa")
            laiksehat = bundle.getString("laiksehat")
            id = bundle.getInt("id")

            edttpm.setText(tpm.toString())
            edtnama.setText(pemilik.toString())
            edtalamat.setText(alamat.toString())
            edtjumlah.setText(jumlah.toString())
            spndesaupdate()
            spngolonganupdate()
            btnselanjutnya.visibility = View.GONE
            btnupdate.visibility = View.VISIBLE
            btnupdate.setOnClickListener {
                val tpm = edttpm.text.toString().trim()
                val nama = edtnama.text.toString().trim()
                val alamat = edtalamat.text.toString().trim()
                val jumlahkaryawan = edtjumlah.text.toString().trim()
                if (desa != null  && tpm.isNotEmpty() && golongan!=null
                    && nama.isNotEmpty() && alamat.isNotEmpty() && jumlahkaryawan.isNotEmpty()
                ){
                    startActivity<Tpm2Activity>(
                        "desa" to desa,
                        "tpm" to tpm,
                        "nama" to nama,
                        "golongan" to golongan,
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
            spngolongan()
            btnselanjutnya.setOnClickListener {
                proses(it)
            }
        }


    }


    private fun proses(view : View) {
        val tpm = edttpm.text.toString().trim()
        val nama = edtnama.text.toString().trim()
        val alamat = edtalamat.text.toString().trim()
        val jumlahkaryawan = edtjumlah.text.toString().trim()
        if (desa != null  && tpm.isNotEmpty() && golongan!=null
            && nama.isNotEmpty() && alamat.isNotEmpty() && jumlahkaryawan.isNotEmpty()
        ) {
            startActivity<Tpm2Activity>(
                "desa" to desa,
                "tpm" to tpm,
                "nama" to nama,
                "golongan" to golongan,
                "alamat" to alamat,
                "jumlahkaryawan" to jumlahkaryawan,
                "status" to 0
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

    fun spngolongan() {
        val datakelamin = arrayOf(
            "Warung Makan",
            "Jasa Boga",
            "Makanan Jajanan",
            "Kantin Sekolah",
        )

        val spinner = find<Spinner>(R.id.spngolongan)
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
                    golongan = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    fun spngolonganupdate() {
        val datakelamin = arrayOf(
            golongan,
            "Warung Makan",
            "Jasa Boga",
            "Makanan Jajanan",
            "Kantin Sekolah",
        )

        val spinner = find<Spinner>(R.id.spngolongan)
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
                    golongan = datakelamin[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
}