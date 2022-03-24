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
import com.puskesmas.puskesmas.DamiuGrafikActivity
import com.puskesmas.puskesmas.GrafikTpmActivity
import com.puskesmas.puskesmas.R
import com.puskesmas.puskesmas.auth.LoginActivity
import com.puskesmas.puskesmas.databinding.FragmentLIhatGrafikBinding
import com.puskesmas.puskesmas.databinding.FragmentLihatDataBinding
import com.puskesmas.puskesmas.databinding.FragmentProfilBinding
import com.puskesmas.puskesmas.databinding.FragmentTentangAplikasiBinding
import com.puskesmas.puskesmas.session.SessionManager
import org.jetbrains.anko.support.v4.startActivity

class LIhatGrafikFragment : Fragment() {

    lateinit var binding : FragmentLIhatGrafikBinding
    lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_l_ihat_grafik,container,false)
        binding.lifecycleOwner
        sessionManager = SessionManager(requireContext().applicationContext)

        binding.btngrafikdamiu.setOnClickListener {
            startActivity<DamiuGrafikActivity>()
        }

        binding.btngrafiktpm.setOnClickListener {
            startActivity<GrafikTpmActivity>()
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