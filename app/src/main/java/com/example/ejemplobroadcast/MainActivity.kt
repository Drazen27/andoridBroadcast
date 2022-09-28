package com.example.ejemplobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ejemplobroadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val getAirplaneMode = object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            //puede ser true or false
            //true si esta habilitado el modo avion
            val airplaneMode = intent?.getBooleanExtra("state",false)
            airplaneMode?.let {
                val mensaje=if(it) "Modo avion activado" else "Modo avion desactivado"
                binding.txtModoAvion.text=mensaje
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


    override fun onStart() {
        super.onStart()
        registerReceiver(getAirplaneMode, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(getAirplaneMode)
    }
}