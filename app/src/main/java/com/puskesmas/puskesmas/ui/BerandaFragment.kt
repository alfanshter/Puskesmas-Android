package com.puskesmas.puskesmas.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.puskesmas.puskesmas.R
import com.puskesmas.puskesmas.adapter.ShowSliderAdapter
import com.puskesmas.puskesmas.databinding.FragmentBerandaBinding
import com.smarteist.autoimageslider.SliderView
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import com.puskesmas.puskesmas.DamiuActivity
import com.puskesmas.puskesmas.TpmActivity
import com.puskesmas.puskesmas.auth.LoginActivity
import com.puskesmas.puskesmas.databinding.FragmentProfilBinding
import com.puskesmas.puskesmas.databinding.FragmentTentangAplikasiBinding
import com.puskesmas.puskesmas.session.SessionManager
import org.jetbrains.anko.support.v4.startActivity


class BerandaFragment : Fragment() {

    lateinit var binding : FragmentBerandaBinding
    lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_beranda,container,false)
        binding.lifecycleOwner
        sessionManager = SessionManager(requireContext().applicationContext)
        getslider()
        binding.btntpm.setOnClickListener {
            startActivity<TpmActivity>()
        }
        binding.btndam.setOnClickListener {

            startActivity<DamiuActivity>()
        }

        binding.btnprofil.setOnClickListener {
            showprofil()
        }
        binding.info.setOnClickListener {
            showtentang()
        }
        return binding.root
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

        dialogBinding?.txtnama!!.text = "Petugas"
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