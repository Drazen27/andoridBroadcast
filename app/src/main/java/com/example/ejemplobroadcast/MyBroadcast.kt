package com.example.ejemplobroadcast

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.provider.Settings
import android.widget.Toast
import com.example.ejemplobroadcast.databinding.ActivityBateryBinding
import kotlin.math.round

//PAra que se comporte como una clase de tipo BroadclassReceiver deben heredar una clase abstracta de tipo BroadcastReceiver()
class MyBroadcast(
    val bgPantalla:ActivityBateryBinding
):BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        //onReceive va a funcionar para recivir los mensajes de diferentes servicios que configuremos
        when(intent?.action){
            Intent.ACTION_BATTERY_CHANGED ->  showBatteryStatus(context,intent)
            Intent.ACTION_BATTERY_LOW -> showBaterryLow(context,intent)
        }
    }
    private fun showBaterryLow(context:Context?,intent: Intent){
        val batteryStatus = intent?.getBooleanExtra(BatteryManager.EXTRA_BATTERY_LOW,false)
        //Cuando bateria baja vamos a bajar ael brillo de la pantalla a un minimo estimado

        batteryStatus?.let {
            bgPantalla.txtMensajeBateria.text="Bateria Baja"
            //Brillo de la pantalla trabaja en un rango de 0 a 255
            val screenBrightness=20
            changeScreenBrightness(context,screenBrightness)
        }
    }

    private fun changeScreenBrightness(context: Context?, screenBrightness: Int) {
        val writePermission=hasEnableEritePermissions(context)
        if(writePermission){
            //1: el brillo de la pantalla esta configurado automáticamente
            //2: si quieren jugar con el brillo primero tienen que cambiar esa configuración
            Settings.System.putInt(
                context?.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            )
            //ajustar el brillo de la pantalla
            Settings.System.putInt(
                context?.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS,
                screenBrightness
            )
            val porcentajeBrillo = screenBrightness.toDouble() / 255
            Toast.makeText(context,"Brillo al ${round(porcentajeBrillo)}%",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context,"No tienes permisos unu",Toast.LENGTH_SHORT).show()
        }
    }
    //Evaluara si tiene los permisos de escritura en las configuraciones del sistema habilitadas
    private fun hasEnableEritePermissions(context: Context?): Boolean {
        return Settings.System.canWrite(context)
    }

    private fun showBatteryStatus(context: Context?,intent: Intent) {

        val bateryStatus = intent?.getIntExtra(BatteryManager.EXTRA_HEALTH,BatteryManager.BATTERY_HEALTH_UNKNOWN)
        bateryStatus?.let {
            val message="El nivel de la bateria es $it%"
            bgPantalla.apply {
                txtPorcentajeBateria.text=message
                pbNivelBateria.progress=it
            }
            when(it){
                BatteryManager.BATTERY_HEALTH_GOOD -> Toast.makeText(context,"Bateria en buen estado",Toast.LENGTH_SHORT).show()
                BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> Toast.makeText(context,"Bateria esta sobrecargada",Toast.LENGTH_SHORT).show()
                BatteryManager.BATTERY_HEALTH_DEAD -> Toast.makeText(context,"Bateria esta muerta",Toast.LENGTH_SHORT).show()
                BatteryManager.BATTERY_HEALTH_OVERHEAT -> Toast.makeText(context,"Bateria esta sobrecalentada",Toast.LENGTH_SHORT).show()
            }
        }
    }


}