package com.example.ejemplobroadcast

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.example.ejemplobroadcast.databinding.ActivityBateryBinding

class BatteryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBateryBinding
    private lateinit var myBroadcast: MyBroadcast


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBateryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myBroadcast= MyBroadcast(binding)
        binding.fabPermisos.setOnClickListener { enablePermissions() }
    }

    private fun enablePermissions() {
        //abrir la pantalla de tu celular donde esta ubicado la configuraacion
        // de permisos de escritura a los servicios del celular
        val intent=Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        startActivity(intent)
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