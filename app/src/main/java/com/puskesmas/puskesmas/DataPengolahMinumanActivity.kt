package com.puskesmas.puskesmas

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.puskesmas.puskesmas.adapter.DamAdapter
import com.puskesmas.puskesmas.model.PostResponse
import com.puskesmas.puskesmas.model.TpmModel
import com.puskesmas.puskesmas.model.TpmResponse
import com.puskesmas.puskesmas.werbservice.ApiClient
import kotlinx.android.synthetic.main.activity_data_pengolah_minuman.*
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DataPengolahMinumanActivity : AppCompatActivity(),AnkoLogger {
    var desa: String? = null
    var api = ApiClient.instance()
    private val filePath = File(Environment.getExternalStorageDirectory().toString() +"/"+"DAM${System.currentTimeMillis()}"+".xls")

    lateinit var progressDialog: ProgressDialog
    private lateinit var mAdapter: DamAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_pengolah_minuman)
        progressDialog = ProgressDialog(this)
        spndesa()

        rvdam.layoutManager = LinearLayoutManager(this)
        rvdam.setHasFixedSize(true)
        (rvdam.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        shimmerFrameLayout.startShimmer()
        showdata()
        btndownload.setOnClickListener {
            exportexcel(it)
        }
    }


    fun spndesa() {
        val datakelamin = arrayOf(
            "Kecamatan",
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
                    shimmerFrameLayout.startShimmer()
                    desa = datakelamin[position]
                    txtdesa.text = desa.toString()
                    showdata()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }
    fun showdata(){
        if (desa!=null){
            if (desa=="Kecamatan"){
                api.readdamall()
                    .enqueue(object : Callback<TpmResponse> {
                        override fun onResponse(
                            call: Call<TpmResponse>,
                            response: Response<TpmResponse>
                        ) {
                            try {
                                if (response.isSuccessful) {
                                    if (response.body()!!.data!!.isEmpty()){
                                        shimmerFrameLayout.stopShimmer()
                                        shimmerFrameLayout.visibility = View.GONE
                                        rvdam.visibility = View.GONE
                                        txtnodata.visibility = View.VISIBLE
                                    }else{
                                        shimmerFrameLayout.stopShimmer()
                                        shimmerFrameLayout.visibility = View.GONE
                                        rvdam.visibility = View.VISIBLE
                                        txtnodata.visibility = View.GONE
                                        val notesList = mutableListOf<TpmModel>()
                                        val data = response.body()
                                        for (hasil in data!!.data!!) {
                                            notesList.add(hasil)
                                            mAdapter = DamAdapter(notesList, this@DataPengolahMinumanActivity)
                                            rvdam.adapter = mAdapter
                                            mAdapter.setDialog(object : DamAdapter.Dialog{
                                                override fun onClick(
                                                    position: Int,
                                                    note: TpmModel
                                                ) {
                                                    startActivity<DetailDamActivity>(
                                                        "dam" to note.dam,
                                                        "desa" to note.desa,
                                                        "pemilik" to note.pemilik,
                                                        "alamat" to note.alamat,
                                                        "jumlah" to note.karyawan,
                                                        "ikl" to note.ikl,
                                                        "sampel" to note.ujisampel,
                                                        "penjamah" to note.sertifikatpenjamaah,
                                                        "izin" to note.izinusaha,
                                                        "laiksehat" to note.laiksehat,
                                                        "id" to note.id
                                                    )
                                                }

                                                override fun onEdit(
                                                    position: Int,
                                                    id: Int,
                                                    note: TpmModel
                                                ) {
                                                    startActivity<DamiuActivity>(
                                                        "dam" to  note.dam!!,
                                                        "desa" to  note.desa!!,
                                                        "pemilik" to  note.pemilik!!,
                                                        "alamat" to  note.alamat!!,
                                                        "jumlah" to  note.karyawan!!,
                                                        "ikl" to  note.ikl!!,
                                                        "sampel" to  note.ujisampel!!,
                                                        "penjamah" to  note.sertifikatpenjamaah!!,
                                                        "izin" to  note.izinusaha!!,
                                                        "laiksehat" to  note.laiksehat!!,
                                                        "id" to  note.id,

                                                        )
                                                }
                                                override fun onHapus(position: Int, id: Int) {
                                                    val builder = AlertDialog.Builder(this@DataPengolahMinumanActivity)
                                                    builder.setTitle("Hapus")
                                                    builder.setMessage("Hapus data ? ")
                                                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                                                        progressDialog.setTitle("Loading...")
                                                        progressDialog.setCanceledOnTouchOutside(false)
                                                        progressDialog.show()

                                                        api.deletedam(id).enqueue(object : Callback<PostResponse>{
                                                            override fun onResponse(
                                                                call: Call<PostResponse>,
                                                                response: Response<PostResponse>
                                                            ) {
                                                                try {
                                                                    if (response.isSuccessful){
                                                                        if (response.body()!!.data==1){
                                                                            progressDialog.dismiss()
                                                                            toast("berhasil di delete")
                                                                            showdata()
                                                                        }else{
                                                                            progressDialog.dismiss()
                                                                            toast("gagal di delete")
                                                                        }
                                                                    }
                                                                }catch (e :Exception){
                                                                    progressDialog.dismiss()
                                                                    info {  "dinda ${e.message}"}
                                                                }
                                                            }

                                                            override fun onFailure(
                                                                call: Call<PostResponse>,
                                                                t: Throwable
                                                            ) {
                                                                progressDialog.dismiss()
                                                                toast("kesalahan jaringan")
                                                                info { "dinda ${t.message}" }
                                                            }

                                                        })
                                                    }

                                                    builder.setNegativeButton(android.R.string.no) { dialog, which ->
                                                        toast("tidak")
                                                    }

                                                    builder.show()

                                                }

                                            })

                                            mAdapter.notifyDataSetChanged()
                                        }
                                    }

                                } else {
                                    toast("gagal mendapatkan response")
                                }
                            } catch (e: Exception) {
                                info { "dinda ${e.message}" }
                            }
                        }

                        override fun onFailure(call: Call<TpmResponse>, t: Throwable) {
                            info { "dinda ${t.message}" }
                        }

                    })
            }
            else{
                api.readdamdesa(
                    desa!!
                )
                    .enqueue(object : Callback<TpmResponse> {
                        override fun onResponse(
                            call: Call<TpmResponse>,
                            response: Response<TpmResponse>
                        ) {
                            try {
                                if (response.isSuccessful) {
                                    if (response.body()!!.data!!.isEmpty()){
                                        shimmerFrameLayout.stopShimmer()
                                        shimmerFrameLayout.visibility = View.GONE
                                        rvdam.visibility = View.GONE
                                        txtnodata.visibility = View.VISIBLE
                                    }else{
                                        shimmerFrameLayout.stopShimmer()
                                        shimmerFrameLayout.visibility = View.GONE
                                        rvdam.visibility = View.VISIBLE
                                        txtnodata.visibility = View.GONE
                                        val notesList = mutableListOf<TpmModel>()
                                        val data = response.body()
                                        for (hasil in data!!.data!!) {
                                            notesList.add(hasil)
                                            mAdapter = DamAdapter(notesList, this@DataPengolahMinumanActivity)
                                            rvdam.adapter = mAdapter
                                            mAdapter.setDialog(object : DamAdapter.Dialog{
                                                override fun onClick(
                                                    position: Int,
                                                    note: TpmModel
                                                ) {
                                                    startActivity<DetailDamActivity>(
                                                        "dam" to note.dam,
                                                        "desa" to note.desa,
                                                        "pemilik" to note.pemilik,
                                                        "alamat" to note.alamat,
                                                        "jumlah" to note.karyawan,
                                                        "ikl" to note.ikl,
                                                        "sampel" to note.ujisampel,
                                                        "penjamah" to note.sertifikatpenjamaah,
                                                        "izin" to note.izinusaha,
                                                        "laiksehat" to note.laiksehat,
                                                        "id" to note.id
                                                    )
                                                }

                                                override fun onEdit(
                                                    position: Int,
                                                    id: Int,
                                                    note: TpmModel
                                                ) {
                                                    startActivity<DamiuActivity>(
                                                        "dam" to  note.dam!!,
                                                        "desa" to  note.desa!!,
                                                        "pemilik" to  note.pemilik!!,
                                                        "alamat" to  note.alamat!!,
                                                        "jumlah" to  note.karyawan!!,
                                                        "ikl" to  note.ikl!!,
                                                        "sampel" to  note.ujisampel!!,
                                                        "penjamah" to  note.sertifikatpenjamaah!!,
                                                        "izin" to  note.izinusaha!!,
                                                        "laiksehat" to  note.laiksehat!!,
                                                        "id" to  note.id,

                                                        )
                                                }
                                                override fun onHapus(position: Int, id: Int) {
                                                    val builder = AlertDialog.Builder(this@DataPengolahMinumanActivity)
                                                    builder.setTitle("Hapus")
                                                    builder.setMessage("Hapus data ? ")
                                                    builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                                                        progressDialog.setTitle("Loading...")
                                                        progressDialog.setCanceledOnTouchOutside(false)
                                                        progressDialog.show()

                                                        api.deletedam(id).enqueue(object : Callback<PostResponse>{
                                                            override fun onResponse(
                                                                call: Call<PostResponse>,
                                                                response: Response<PostResponse>
                                                            ) {
                                                                try {
                                                                    if (response.isSuccessful){
                                                                        if (response.body()!!.data==1){
                                                                            progressDialog.dismiss()
                                                                            toast("berhasil di delete")
                                                                            showdata()
                                                                        }else{
                                                                            progressDialog.dismiss()
                                                                            toast("gagal di delete")
                                                                        }
                                                                    }
                                                                }catch (e :Exception){
                                                                    progressDialog.dismiss()
                                                                    info {  "dinda ${e.message}"}
                                                                }
                                                            }

                                                            override fun onFailure(
                                                                call: Call<PostResponse>,
                                                                t: Throwable
                                                            ) {
                                                                progressDialog.dismiss()
                                                                toast("kesalahan jaringan")
                                                                info { "dinda ${t.message}" }
                                                            }

                                                        })
                                                    }

                                                    builder.setNegativeButton(android.R.string.no) { dialog, which ->
                                                        toast("tidak")
                                                    }

                                                    builder.show()

                                                }

                                            })

                                            mAdapter.notifyDataSetChanged()
                                        }
                                    }

                                } else {
                                    toast("gagal mendapatkan response")
                                }
                            } catch (e: Exception) {
                                info { "dinda ${e.message}" }
                            }
                        }

                        override fun onFailure(call: Call<TpmResponse>, t: Throwable) {
                            info { "dinda ${t.message}" }
                        }

                    })
            }

        }
    }

    fun exportexcel(view : View){
        api.readdamall().enqueue(object : Callback<TpmResponse>{
            override fun onResponse(call: Call<TpmResponse>, response: Response<TpmResponse>) {
                try {
                    if (response.isSuccessful){
                        if (response.body()!!.data!!.isNotEmpty()){
                            progressDialog.dismiss()
                            val hssfWorkbook = HSSFWorkbook()
                            val hssfSheet = hssfWorkbook.createSheet("Custom Sheet")

                            val row7 = hssfSheet.createRow(7)
                            val row1 = hssfSheet.createRow(1)
                            val cell1 = row7.createCell(0)
                            val cell3 = row7.createCell(1)
                            val cell4 = row7.createCell(2)
                            val cell5 = row7.createCell(3)
                            val cell6 = row7.createCell(4)
                            val cell7 = row7.createCell(5)
                            val cell8 = row7.createCell(6)
                            val cell9 = row7.createCell(7)
                            val cell10 = row7.createCell(8)

                            val cell1row1 = row1.createCell(6)
                            cell1row1.setCellValue("REKAPITULASI TEMPAT PENGOLAHAN MAKANAN KECAMATAN SUKAMARA")

                            cell1.setCellValue("NAMA DAMIU")
                            cell3.setCellValue("NAMA PEMILIK")
                            cell4.setCellValue("ALAMAT")
                            cell5.setCellValue("KEL/DESA")
                            cell6.setCellValue("HASIL INSPEKSI SANITASI")
                            cell7.setCellValue("HASIL UJI SAMPEL")
                            cell8.setCellValue("KEPEMILIKAN SERTIFIKAT PENJAMAH")
                            cell9.setCellValue("SERTIFIKAT LAIK SEHAT")
                            cell10.setCellValue("Memiliki Izin Usaha")
                            var row =7
                            for (data in response.body()!!.data!!){
                                row++
                                var datarow = hssfSheet.createRow(row)
                                val datacell = datarow.createCell(0)
                                val datacell3 = datarow.createCell(1)
                                val datacell4 = datarow.createCell(2)
                                val datacell5 = datarow.createCell(3)
                                val datacell6 = datarow.createCell(4)
                                val datacell7 = datarow.createCell(5)
                                val datacell8 = datarow.createCell(6)
                                val datacell9 = datarow.createCell(7)
                                val datacell10 = datarow.createCell(8)


                                datacell.setCellValue(data.dam.toString())
                                datacell3.setCellValue(data.pemilik.toString())
                                datacell4.setCellValue(data.alamat.toString())
                                datacell5.setCellValue(data.desa.toString())
                                datacell6.setCellValue(data.ikl.toString())
                                datacell7.setCellValue(data.ujisampel.toString())
                                datacell8.setCellValue(data.sertifikatpenjamaah.toString())
                                datacell9.setCellValue(data.laiksehat.toString())
                                datacell10.setCellValue(data.izinusaha.toString())

                            }


                            var fileOutputStream  : FileOutputStream? = null

                            try {
                                if (!filePath.exists()) {
                                    filePath.createNewFile()
                                    toast("berhasil di export dengan nama ${filePath.name}")

                                }
                                fileOutputStream = FileOutputStream(filePath)

                                hssfWorkbook.write(fileOutputStream)
                                Toast.makeText(applicationContext, "OK", Toast.LENGTH_LONG).show()
                                if (fileOutputStream != null) {
                                    fileOutputStream.flush()
                                    fileOutputStream.close()
                                }
                            } catch (e: IOException) {
                                e.printStackTrace()
                                Toast.makeText(applicationContext, "NO OK", Toast.LENGTH_LONG).show()
                                try {
                                    fileOutputStream!!.close()
                                } catch (ex: IOException) {
                                    ex.printStackTrace()
                                }
                            }
                        }else{
                            progressDialog.dismiss()
                            Snackbar.make(view,"Belum ada data",3000).show()
                        }
                    }else{
                        progressDialog.dismiss()
                        toast("kesalahan aplikasi")

                    }
                }catch (e :Exception){
                    info { "dinda ${e.message}" }
                    progressDialog.dismiss()
                }
            }

            override fun onFailure(call: Call<TpmResponse>, t: Throwable) {
                info { "dinda ${t.message}" }
                progressDialog.dismiss()
                Snackbar.make(view,"Kesalahan jaringan",3000).show()

            }

        })

    }



}