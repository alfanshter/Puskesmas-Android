package com.puskesmas.puskesmas

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.puskesmas.puskesmas.model.GrafikResponse
import com.puskesmas.puskesmas.model.Score
import com.puskesmas.puskesmas.werbservice.ApiClient
import kotlinx.android.synthetic.main.activity_damiu_grafik.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DamiuGrafikActivity : AppCompatActivity() {
    val api = ApiClient.instance()
    var desa: String? = null


    private var scoreList = ArrayList<Score>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_damiu_grafik)
        spndamiu()

        initbardamiu()


        //now draw bar chart with dynamic data

    }


    fun spndamiu() {
        val memenuhisyarat = ArrayList<BarEntry>()
        val tidakmemenuhisyarat = ArrayList<BarEntry>()
        val ada = ArrayList<BarEntry>()

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
        val spinner = find<Spinner>(R.id.spndamiu)
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

                    api.getdatadam(desa!!).enqueue(object : Callback<GrafikResponse> {
                        override fun onResponse(
                            call: Call<GrafikResponse>,
                            response: Response<GrafikResponse>
                        ) {
                            try {
                                if (response.isSuccessful){
                                    scoreList.clear()
                                    scoreList.add(Score("IKL", response.body()!!.ikl0!!))
                                    scoreList.add(Score("IKL", response.body()!!.ikl1!!))
                                    scoreList.add(Score("HUS", response.body()!!.sampel0!!))
                                    scoreList.add(Score("HUS", response.body()!!.sampel1!!))
                                    scoreList.add(Score("HUS", response.body()!!.sampel2!!))
                                    scoreList.add(Score("PP", response.body()!!.penjamaah0!!))
                                    scoreList.add(Score("PP", response.body()!!.penjamaah1!!))
                                    scoreList.add(Score("SLS", response.body()!!.laiksehat0!!))
                                    scoreList.add(Score("SLS", response.body()!!.laiksehat1!!))
                                    scoreList.add(Score("IU", response.body()!!.izin0!!))
                                    scoreList.add(Score("IU", response.body()!!.izin1!!))



                                    initbardamiu()
                                    val entries: ArrayList<BarEntry> = ArrayList()




                                    //you can replace this data object with  your custom object
                                    for (i in scoreList.indices) {
                                        val score = scoreList[i]
                                        entries.add(BarEntry(i.toFloat(), score.score.toFloat()))
                                    }

                                    val barDataSet = BarDataSet(entries, "")

                                    barDataSet.colors = ColorTemplate.createColors(intArrayOf(resources.getColor(R.color.hijau),resources.getColor(R.color.biru),resources.getColor(R.color.hijau),resources.getColor(R.color.biru),resources.getColor(R.color.kuning),resources.getColor(R.color.orange),resources.getColor(R.color.ungu),resources.getColor(R.color.orange),resources.getColor(R.color.ungu),resources.getColor(R.color.orange),resources.getColor(R.color.ungu)))


                                    val data = BarData(barDataSet)
                                    bardamiu.data = data

                                    bardamiu.invalidate()

                                  /*


                                    memenuhisyarat.add(BarEntry(0F, 10f))
                                    memenuhisyarat.add(BarEntry(1F, 11F))
                                    memenuhisyarat.add(BarEntry(2F, 19F))
                                    memenuhisyarat.add(BarEntry(3F, 10F))
                                    memenuhisyarat.add(BarEntry(4F, 18F))
                                    memenuhisyarat.add(BarEntry(5F, 0f))

                                    tidakmemenuhisyarat.add(BarEntry(0F, 8f))
                                    tidakmemenuhisyarat.add(BarEntry(1F, 9F))
                                    tidakmemenuhisyarat.add(BarEntry(2F, 2F))
                                    tidakmemenuhisyarat.add(BarEntry(3F, 1F))
                                    tidakmemenuhisyarat.add(BarEntry(4F, 1F))
                                    tidakmemenuhisyarat.add(BarEntry(5f, 0f))


                                    ada.add(BarEntry(1F, 13F))
                                    ada.add(BarEntry(2F, 11F))
                                    ada.add(BarEntry(3F, 10F))
                                    ada.add(BarEntry(4F, 7F))
                                    ada.add(BarEntry(5f, 0f))

                                    val memenuhisyaratBarDataSet = BarDataSet(memenuhisyarat, "Hasil Inspeksi Kesling")
                                    memenuhisyaratBarDataSet.color = Color.BLUE

                                    val tidakmemenuhisyaratBarDataSet = BarDataSet(tidakmemenuhisyarat, "Tidak Memenuhi Syarat")
                                    tidakmemenuhisyaratBarDataSet.color = Color.GREEN

                                    val belumujisampel = BarDataSet(tidakmemenuhisyarat, "Belum Uji Sampel")
                                    belumujisampel.color = Color.CYAN


                                    val adaBarDataSet = BarDataSet(ada, "Ada")
                                    adaBarDataSet.color = Color.RED


                                    val tidakada = BarDataSet(ada, "Tidak Ada")
                                    tidakada.color = Color.GRAY

                                    val groupSpace = 0.08f
                                    val barSpace = 0.03f
                                    val barWidth = 0.27f
                                    val groupBar = BarData(memenuhisyaratBarDataSet, tidakmemenuhisyaratBarDataSet, adaBarDataSet)
                                    groupBar.barWidth = barWidth
                                    bardamiu.data = groupBar
                                    bardamiu.notifyDataSetChanged()
                                    bardamiu.groupBars(0f, groupSpace, barSpace)
                                    bardamiu.animateXY(100, 500)
                                    bardamiu.invalidate()*/
                                }else{

                                }
                            } catch (e: Exception) {

                            }
                        }

                        override fun onFailure(call: Call<GrafikResponse>, t: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })


                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }

    }



    private fun initbardamiu() {


//        hide grid lines
        bardamiu.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = bardamiu.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        bardamiu.axisRight.isEnabled = false

        //remove legend
        bardamiu.legend.isEnabled = false


        //remove description label
        bardamiu.description.isEnabled = false


        //add animation
        bardamiu.animateY(3000)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.setDrawLabels(true)
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f

    }



    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if (index < scoreList.size) {
                scoreList[index].name
            } else {
                scoreList[index].name
            }
        }
    }

}