package com.puskesmas.puskesmas.ui

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.puskesmas.puskesmas.MainActivity
import com.puskesmas.puskesmas.R
import com.puskesmas.puskesmas.admin.HomeAdminActivity
import com.puskesmas.puskesmas.auth.LoginActivity
import com.puskesmas.puskesmas.databinding.FragmentPegawaiBinding
import com.puskesmas.puskesmas.databinding.FragmentProfilBinding
import com.puskesmas.puskesmas.databinding.FragmentTentangAplikasiBinding
import com.puskesmas.puskesmas.model.PostResponse
import com.puskesmas.puskesmas.session.SessionManager
import com.puskesmas.puskesmas.werbservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PegawaiFragment : Fragment(),AnkoLogger {
    lateinit var progressDialog: ProgressDialog
    lateinit var binding : FragmentPegawaiBinding
    lateinit var sessionManager: SessionManager
    var api = ApiClient.instance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_pegawai,container,false)
        binding.lifecycleOwner
        progressDialog = ProgressDialog(requireActivity())
sessionManager = SessionManager(requireContext().applicationContext)
        binding.btnselanjutnya.setOnClickListener {
            insertdata(it)
        }

        binding.btnprofil.setOnClickListener {
            showprofil()
        }
        binding.info.setOnClickListener {
            showtentang()
        }
        return binding.root
    }

    fun insertdata(view : View){
        val nama = binding.edtnama.text.toString()
        val alamat = binding.edtalamat.text.toString()
        val username = binding.edtusername.text.toString()
        val password = binding.edtpassword.text.toString()
        val cfrpassword = binding.edtconfrimpassword.text.toString()

        if (nama.isNotEmpty() && alamat.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && cfrpassword.isNotEmpty()){
            if (password == cfrpassword){
                progressDialog.setTitle("Loading...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()
                api.register(nama,alamat,username,"user",password).enqueue(object : Callback<PostResponse>{
                    override fun onResponse(
                        call: Call<PostResponse>,
                        response: Response<PostResponse>
                    ) {
                        try {
                            if (response.isSuccessful){
                                if (response.body()!!.data==1){
                                    progressDialog.dismiss()
                                    Snackbar.make(view, "Pendaftaran berhasil",3000).show()
                                }else{
                                    progressDialog.dismiss()
                                    Snackbar.make(view, "Username sudah terdaftar",3000).show()
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

                    override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                        progressDialog.dismiss()
                        toast("kesalahan server / jaringan anda")
                        info { "dinda ${t.message}" }
                    }

                })
            }else{
                Snackbar.make(view,"Password tidak sama",3000).show()
            }
        }else{
            Snackbar.make(view,"Jangan kosongi kolom",3000).show()

        }
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