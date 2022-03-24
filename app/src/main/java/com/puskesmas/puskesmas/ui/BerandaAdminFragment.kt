package com.puskesmas.puskesmas.ui

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.puskesmas.puskesmas.DamiuActivity
import com.puskesmas.puskesmas.DetailLihatDataActivity
import com.puskesmas.puskesmas.R
import com.puskesmas.puskesmas.adapter.ShowSliderAdapter
import com.puskesmas.puskesmas.adapter.UsersAdapter
import com.puskesmas.puskesmas.auth.LoginActivity
import com.puskesmas.puskesmas.databinding.FragmentBerandaAdminBinding
import com.puskesmas.puskesmas.databinding.FragmentProfilBinding
import com.puskesmas.puskesmas.databinding.FragmentTentangAplikasiBinding
import com.puskesmas.puskesmas.model.PostResponse
import com.puskesmas.puskesmas.model.UsersModel
import com.puskesmas.puskesmas.model.UsersResponse
import com.puskesmas.puskesmas.session.SessionManager
import com.puskesmas.puskesmas.werbservice.ApiClient
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.activity_data_pengolah_makanan.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BerandaAdminFragment : Fragment(),AnkoLogger {

    lateinit var binding : FragmentBerandaAdminBinding
    lateinit var sessionManager: SessionManager
    var api = ApiClient.instance()
    lateinit var progressDialog : ProgressDialog
    private lateinit var mAdapter: UsersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_beranda_admin,container,false)
        binding.lifecycleOwner
        progressDialog = ProgressDialog(requireActivity())
        sessionManager = SessionManager(requireContext().applicationContext)
        getslider()

        binding.btnprofil.setOnClickListener {
            showprofil()
        }
        binding.info.setOnClickListener {
            showtentang()
        }
        binding.rvmakanan.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.rvmakanan.setHasFixedSize(true)
        (binding.rvmakanan.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL
        binding.shimmerFrameLayout.startShimmer()
        showdata()
        return  binding.root

    }

    fun showdata(){
        api.getakunall()
            .enqueue(object : Callback<UsersResponse> {
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            if (response.body()!!.data!!.isEmpty()){
                                binding.shimmerFrameLayout.stopShimmer()
                                binding.shimmerFrameLayout.visibility = View.GONE
                                binding.rvmakanan.visibility = View.GONE
                                binding.txtnodata.visibility = View.VISIBLE
                            }else{
                                binding.shimmerFrameLayout.stopShimmer()
                                binding.shimmerFrameLayout.visibility = View.GONE
                                binding.rvmakanan.visibility = View.VISIBLE
                                binding.txtnodata.visibility = View.GONE
                                val notesList = mutableListOf<UsersModel>()
                                val data = response.body()
                                for (hasil in data!!.data!!) {
                                    notesList.add(hasil)
                                    mAdapter = UsersAdapter(notesList, requireContext().applicationContext)
                                    binding.rvmakanan.adapter = mAdapter
                                    mAdapter.setDialog(object : UsersAdapter.Dialog{
                                        override fun onClick(position: Int, note: UsersModel) {

                                        }

                                        override fun onEdit(
                                            position: Int,
                                            id: Int,
                                            note: UsersModel
                                        ) {


                                        }

                                        override fun onHapus(position: Int, id: Int) {
                                            val builder = AlertDialog.Builder(requireActivity())
                                            builder.setTitle("Petugas")
                                            builder.setMessage("Hapus petugas ? ")
                                            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                                                progressDialog.setTitle("Loading...")
                                                progressDialog.setCanceledOnTouchOutside(false)
                                                progressDialog.show()

                                                api.hapusakun(id).enqueue(object : Callback<PostResponse>{
                                                    override fun onResponse(
                                                        call: Call<PostResponse>,
                                                        response: Response<PostResponse>
                                                    ) {
                                                        try {
                                                            if (response.isSuccessful){
                                                                if (response.body()!!.data==1){
                                                                    progressDialog.dismiss()
                                                                    showdata()
                                                                    toast("berhasil")
                                                                }else{
                                                                    progressDialog.dismiss()
                                                                    toast("gagal")
                                                                }

                                                            }else{
                                                                progressDialog.dismiss()
                                                                toast("kesalahan aplikasi")
                                                            }
                                                        }catch (e :Exception){
                                                            progressDialog.dismiss()
                                                            info { "dinda ${e.message}" }
                                                        }
                                                    }

                                                    override fun onFailure(
                                                        call: Call<PostResponse>,
                                                        t: Throwable
                                                    ) {
                                                        progressDialog.dismiss()
                                                        info { "dinda ${t.message}" }
                                                    }

                                                })
                                            }

                                            builder.setNegativeButton(android.R.string.no) { dialog, which ->

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

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })
    }



    fun getslider(){
        val imageList: ArrayList<String> = ArrayList()
        val path: Uri = Uri.parse("android.resource://com.puskesmas.puskesmas/" + R.drawable.banner1)
        val path2: Uri = Uri.parse("android.resource://com.puskesmas.puskesmas/" + R.drawable.banner2)
        val path3: Uri = Uri.parse("android.resource://com.puskesmas.puskesmas/" + R.drawable.banner3)
        val path4: Uri = Uri.parse("android.resource://com.puskesmas.puskesmas/" + R.drawable.banner4)
        val path5: Uri = Uri.parse("android.resource://com.puskesmas.puskesmas/" + R.drawable.banner5)
        val path6: Uri = Uri.parse("android.resource://com.puskesmas.puskesmas/" + R.drawable.banner6)
        val path7: Uri = Uri.parse("android.resource://com.puskesmas.puskesmas/" + R.drawable.banner7)
        imageList.add(path.toString())
        imageList.add(path2.toString())
        imageList.add(path3.toString())
        imageList.add(path4.toString())
        imageList.add(path5.toString())
        imageList.add(path6.toString())
        imageList.add(path7.toString())
        setImageInSlider(imageList, binding.imageSlider)
    }

    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView) {
        val adapter = ShowSliderAdapter()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = true
        imageSlider.scrollTimeInSec = 3
        imageSlider.startAutoCycle()
    }


    fun showprofil(){
        val dialogBinding: FragmentProfilBinding? =
            DataBindingUtil.inflate(
                LayoutInflater.from(requireActivity()),
                R.layout.fragment_profil,
                null,
                false
            )

        val customDialog =
            AlertDialog.Builder(requireActivity(), 0).create()

        customDialog.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setView(dialogBinding?.root)
            setCancelable(false)
        }.show()

        dialogBinding?.btnclose?.setOnClickListener {
            customDialog.dismiss()
        }

        dialogBinding?.btnlogout?.setOnClickListener {
            startActivity<LoginActivity>()
            sessionManager.setLogin(false)
            sessionManager.setLoginadmin(false)
            activity!!.finish()
        }

    }

    fun showtentang(){
        val dialogBinding: FragmentTentangAplikasiBinding? =
            DataBindingUtil.inflate(
                LayoutInflater.from(requireActivity()),
                R.layout.fragment_tentang_aplikasi,
                null,
                false
            )

        val customDialog =
            AlertDialog.Builder(requireActivity(), 0).create()

        customDialog.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setView(dialogBinding?.root)
            setCancelable(false)
        }.show()

        dialogBinding?.btnclose?.setOnClickListener {
            customDialog.dismiss()
        }


    }

}