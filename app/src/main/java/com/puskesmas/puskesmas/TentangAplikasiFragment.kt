package com.puskesmas.puskesmas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.puskesmas.puskesmas.databinding.FragmentTentangAplikasiBinding

class TentangAplikasiFragment : Fragment() {

    lateinit var binding : FragmentTentangAplikasiBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_tentang_aplikasi,container,false)
        binding.lifecycleOwner

        return binding.root
    }


}