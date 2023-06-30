package com.bchoi.signalstrengthspike.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bchoi.signalstrengthspike.adapters.WifiRecyclerviewAdapter
import com.bchoi.signalstrengthspike.databinding.FragmentWifiBinding
import com.bchoi.signalstrengthspike.entities.ConnectionStatus

class WifiFragment : Fragment() {
    private lateinit var binding: FragmentWifiBinding
    private lateinit var wifiManager: WifiManager
    private val adapter = WifiRecyclerviewAdapter()

    private val connectionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            showConnectionStatus()
        }
    }
    private val scanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            println("SCAN: ")
            val wifiList = wifiManager.scanResults.map { scanResult ->
                val level = WifiManager.calculateSignalLevel(scanResult.level, 5)
                println(
                    "Name: ${scanResult.SSID}\n SignalLevel: $level\n " +
                            "BSSID:${scanResult.BSSID}\n Frequency: ${scanResult.frequency}\n" +
                            "dBm: ${scanResult.level}"
                )
                ConnectionStatus(wifiManager.connectionInfo.ssid, level, wifiManager.connectionInfo.bssid, wifiManager.connectionInfo.frequency, wifiManager.connectionInfo.rssi)
            }
            adapter.submitList(wifiList)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        binding = FragmentWifiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.wifiRecyclerView.adapter = adapter

        val activity = activity?:return
        wifiManager = activity.getSystemService(Context.WIFI_SERVICE) as WifiManager
        showConnectionStatus()
        activity.apply {
            registerReceiver(connectionReceiver, IntentFilter(WifiManager.RSSI_CHANGED_ACTION))
            registerReceiver(scanReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        }
        wifiManager.startScan()
    }

    private fun showConnectionStatus() {
        println("CURRENT: ")
        val rssi = wifiManager.connectionInfo.rssi
        val level = WifiManager.calculateSignalLevel(rssi, 5)
        println("Name: ${wifiManager.connectionInfo.ssid}\n SignalLevel: $level\n " +
                "BSSID: ${wifiManager.connectionInfo.bssid}\n Frequency: ${wifiManager.connectionInfo.frequency}\n" +
                " dBm: ${wifiManager.connectionInfo.rssi}")
        binding.connectionStatus = ConnectionStatus(wifiManager.connectionInfo.ssid, level, wifiManager.connectionInfo.bssid, wifiManager.connectionInfo.frequency, wifiManager.connectionInfo.rssi)
    }

    override fun onDestroy() {
        activity?.apply {
            unregisterReceiver(connectionReceiver)
            unregisterReceiver(scanReceiver)
        }
        super.onDestroy()
    }
}