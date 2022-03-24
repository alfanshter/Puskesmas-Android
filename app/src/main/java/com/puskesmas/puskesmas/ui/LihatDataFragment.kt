package com.puskesmas.puskesmas.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.puskesmas.puskesmas.DataPengolahMakananActivity
import com.puskesmas.puskesmas.DataPengolahMinumanActivity
import com.puskesmas.puskesmas.R
import com.puskesmas.puskesmas.auth.LoginActivity
import com.puskesmas.puskesmas.databinding.FragmentLihatDataBinding
import com.puskesmas.puskesmas.databinding.FragmentProfilBinding
import com.puskesmas.puskesmas.databinding.FragmentTentangAplikasiBinding
import com.puskesmas.puskesmas.session.SessionManager
import org.jetbrains.anko.support.v4.startActivity

class LihatDataFragment : Fragment() {

    lateinit var binding : FragmentLihatDataBinding
    lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_lihat_data,container,false)
        binding.lifecycleOwner
        sessionManager = SessionManager(requireContext().applicationContext)
        binding.btntpm.setOnClickListener {
            startActivity<DataPengolahMakananActivity>()
        }

        binding.btndam.setOnClickListener {
            startActivity<DataPengolahMinumanActivity>()
        }


        binding.btnprofil.setOnClickListener {
            showprofil()
        }
        binding.info.setOnClickListener {
            showtentang()
        }
        return binding.root
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