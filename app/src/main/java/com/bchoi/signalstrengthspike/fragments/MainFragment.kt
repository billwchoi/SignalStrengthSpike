package com.bchoi.signalstrengthspike.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bchoi.signalstrengthspike.R
import com.bchoi.signalstrengthspike.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGotoCellular.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_cellularFragment)
        }

        binding.btnGotoWifi.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_wifiFragment)
        }
    }
}