package com.example.ejemplobroadcast

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ejemplobroadcast.databinding.ActivityBateryBinding

class BatteryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBateryBinding
    private lateinit var myBroadcast: MyBroadcast


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBateryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myBroadcast= MyBroadcast(binding)
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(myBroadcast, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        registerReceiver(myBroadcast, IntentFilter(Intent.ACTION_BATTERY_LOW))
        val intent=Intent(this,MyBroadcast::class.java)
        sendBroadcast(intent)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(myBroadcast)
    }
}