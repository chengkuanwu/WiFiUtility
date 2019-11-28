package com.mycompany.wifistatus.ui.dashboard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mycompany.wifistatus.R

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var wifiManager : WifiManager
    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            val resultList: ArrayList<ScanResult> = wifiManager.scanResults as ArrayList<ScanResult>
            Log.d("TESTING", "onReceive Called")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        //val textView: TextView = root.findViewById(R.id.text_dashboard)
        //dashboardViewModel.text.observe(this, Observer {
            //textView.text = it
        //})
        val myAdapter = ArrayAdapter<String>(this.requireContext(), android.R.layout.simple_list_item_1)
        myAdapter.addAll("三個鉛筆", "四個腳踏車")
        val listView: ListView = root.findViewById(R.id.list_dashboard)
        listView.adapter = myAdapter
        wifiManager = this.requireContext().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        startScanning()
        if (wifiManager.isWifiEnabled) {
            val scanResult: List<ScanResult> = wifiManager.scanResults;
            Log.d("TEST", "Got result")
        } else {
            Log.d("TEST", "Disabled")
        }
        return root
    }

    /*override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            stopScanning()
        } else {
            startScanning()
        }
        Log.d("Testing", "onHiddenChanged")
    }*/

    fun startScanning() {
        this.activity?.registerReceiver(broadcastReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        wifiManager.startScan()
    }

    fun stopScanning() {
        this.activity?.unregisterReceiver(broadcastReceiver)
        /*val axisList = ArrayList<Axis>()
        for (result in resultList) {
            axisList.add(Axis(result.BSSID, result.level))
        }
        Log.d("TESTING", axisList.toString())*/

    }
}