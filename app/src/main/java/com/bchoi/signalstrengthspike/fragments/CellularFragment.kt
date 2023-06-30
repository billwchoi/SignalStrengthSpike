package com.bchoi.signalstrengthspike.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.telephony.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bchoi.signalstrengthspike.databinding.FragmentCellularBinding


class CellularFragment : Fragment() {
    lateinit var binding: FragmentCellularBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        binding = FragmentCellularBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val telephonyManager = requireActivity().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        val phoneStateListener : PhoneStateListener = object: PhoneStateListener(){
            override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
                super.onSignalStrengthsChanged(signalStrength)

                val currentSignal = signalStrength.cellSignalStrengths.firstOrNull()
                val signalType = when(currentSignal) {
                    is CellSignalStrengthLte -> "LTE"
                    is CellSignalStrengthCdma -> "CDMA"
                    is CellSignalStrengthNr -> "NR"
                    is CellSignalStrengthGsm -> "GSM"
                    is CellSignalStrengthTdscdma -> "TDSCDMA"
                    is CellSignalStrengthWcdma -> "WCDMA"
                    else -> "UNKNOWN"
                }

                val level = when (currentSignal?.level) {
                    1 -> "POOR"
                    2 -> "MODERATE"
                    3 -> "GOOD"
                    4 -> "GREAT"
                    else -> "NONE"
                }

                binding.type.text = "Type: $signalType"
                binding.level.text = "Signal strength: $level"
                binding.carrier.text = "Carrier: ${telephonyManager.networkOperatorName}"
                binding.country.text = "Country: ${telephonyManager.networkCountryIso}"
            }
        }

        telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_SIGNAL_STRENGTHS)
    }
}