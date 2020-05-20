package mx.edu.ittepic.ladm_u4_practica1_victorjoelc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.view.Gravity
import android.widget.Toast
import java.util.*

class CallReceive : BroadcastReceiver() {

    var estado = 0
    var numero = ""
    var mensaje = ""
    var numero2 = ""

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent!!.getStringExtra(TelephonyManager.EXTRA_STATE) == TelephonyManager.EXTRA_STATE_OFFHOOK){
            showToast(context,"Call started...")
            estado = 1
        }
//--------------------------------------------------------------------------------------------------
        intent!!.getStringExtra(TelephonyManager.EXTRA_STATE) == TelephonyManager.EXTRA_STATE_RINGING
            var incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            numero2 = incomingNumber + "h"
            //showToast(context,numero2)
            ciclo()

//--------------------------------------------------------------------------------------------------
        intent!!.getStringExtra(TelephonyManager.EXTRA_STATE) == TelephonyManager.EXTRA_STATE_IDLE
        //showToast(context,"Call ended...")
        if(estado != 1){
            if(numero == "3119106693" ||
                numero == "3112506076" ||
                numero == "3111005803" ||
                numero == "3111033072" ||
                numero == "3111492461"){
                mensaje = "HOLA FAMILIA, EN SEGUIDA TE REGRESO LA LLAMADA NO TE DESESPERES <3"
            }else{
                mensaje = "EN SEGUIDA TE REGRESO LA LLAMADA"
            }
            //showToast(context,numero)
            SmsManager.getDefault().sendTextMessage(numero,null,
                mensaje,null,null)
            //showToast(context,"se envió mensaje")
//--------------------------------------------------------------------------------------------------
        }





    }
    fun ciclo(){
        var a=0
        var arrayList = ArrayList<String>()
        arrayList = numero2.split("") as ArrayList<String>
        a=0
        while (a<(arrayList.size)-2){
            numero += arrayList.get(a)
            a += 1
        }
    }


    fun showToast(context: Context?,message : String){
        var toast = Toast.makeText(context,message,Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
    }
}