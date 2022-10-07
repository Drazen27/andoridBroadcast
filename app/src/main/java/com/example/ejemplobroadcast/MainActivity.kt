package com.example.ejemplobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ejemplobroadcast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  var acumular=0
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
    private val getTimeChange=object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
                acumular+=1
                val tiempo="Han pasado $acumular minutos"
                binding.txtTiempo.text=tiempo
        }
    }

    private val getWifiMode=object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            //EXTRA_WIFI_STATE= llave registro entero que iforma el estado del WIFI
            val wifiMode=intent?.getIntExtra(WifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_UNKNOWN)
            wifiMode?.let {
                binding.txtWifi.text= when(it){

                    WifiManager.WIFI_STATE_ENABLED -> "El wifi esta habilitado"
                    WifiManager.WIFI_STATE_DISABLED -> "El wifi esta deshabilitado"
                    WifiManager.WIFI_STATE_UNKNOWN -> "Servicio no reconocido"
                    else -> "Posible error en el servicio"
                }
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
        registerReceiver(getTimeChange, IntentFilter(Intent.ACTION_TIME_TICK))
        registerReceiver(getWifiMode, IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION))
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(getAirplaneMode)
        unregisterReceiver(getTimeChange)
        unregisterReceiver(getWifiMode)
    }
}