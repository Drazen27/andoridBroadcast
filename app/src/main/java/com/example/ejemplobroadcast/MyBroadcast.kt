package com.example.ejemplobroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.example.ejemplobroadcast.databinding.ActivityBateryBinding

//PAra que se comporte como una clase de tipo BroadclassReceiver deben heredar una clase abstracta de tipo BroadcastReceiver()
class MyBroadcast(
    val bgPantalla:ActivityBateryBinding
):BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        //onReceive va a funcionar para recivir los mensajes de diferentes servicios que configuremos
        when(intent?.action){
            Intent.ACTION_BATTERY_CHANGED ->  showBatteryStatus(intent)
            Intent.ACTION_BATTERY_LOW -> showBaterryLow(intent)
        }
    }
    private fun showBaterryLow(intent: Intent){
        val batteryStatus = intent?.getBooleanExtra(BatteryManager.EXTRA_BATTERY_LOW,false)
        batteryStatus?.let {
            bgPantalla.txtMensajeBateria.text="Bateria Baja"
        }
    }
    private fun showBatteryStatus(intent: Intent) {

        val bateryStatus = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL,BatteryManager.BATTERY_HEALTH_UNKNOWN)
        bateryStatus?.let {
            val message="El nivel de la bateria es $it%"
            bgPantalla.apply {
                txtPorcentajeBateria.text=message
                pbNivelBateria.progress=it
            }
        }
    }
}