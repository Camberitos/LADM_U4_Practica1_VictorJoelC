package mx.edu.ittepic.ladm_u4_practica1_victorjoelc

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import android.telephony.PhoneStateListener
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import mx.edu.ittepic.ladm_u4_practica1_victorjoelc.R

class MainActivity : AppCompatActivity() {

    val siLecturaLlamadas = 18
    var resultado = ""
    var actualiza = 0
    val siPermiso = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_CALL_LOG),siLecturaLlamadas)
        }else if(ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_PHONE_STATE),siLecturaLlamadas)
        }else if (ActivityCompat.checkSelfPermission(this, //pregunta si es que tiene otorgado un permiso
                        android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) { //si es diferente del permiso otorgado
            //checkSelf recibe 2 parámetros, el activity y el permiso
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.SEND_SMS), siPermiso)//el tercer parámetro es el valor que se otorga si se da el permiso (1)

        }

        button.setOnClickListener(){
            leerLlamadas()
        }
    }

    var num = "3112695353"
    var men = "hola"

    fun envioSMS(){
        SmsManager.getDefault().sendTextMessage(num,null,
                men,null,null)
        Toast.makeText(this,"Se envió el sms", Toast.LENGTH_LONG)
                .show()
    }

    fun leerLlamadas(){
        resultado = ""
        val cursorllamadas = contentResolver.query(
                Uri.parse("content://call_log/calls"),
                null,
                null,
                null,
                null
        )
        if(cursorllamadas!!.moveToFirst()){
            do{
                var nombre = cursorllamadas.getString(
                        cursorllamadas.getColumnIndex(CallLog.Calls.CACHED_NAME)
                )
                var telefono = cursorllamadas.getInt(
                        cursorllamadas.getColumnIndex(CallLog.Calls.NUMBER)
                )
                var tipollamada = cursorllamadas.getString(
                        cursorllamadas.getColumnIndex(CallLog.Calls.TYPE)
                )
                resultado += "Nombre: $nombre\nTeléfono: $telefono\nTipollamada: $tipollamada\n" +
                        "---------------------------------\n"
            }while(cursorllamadas!!.moveToNext())
        }else{
            resultado = "No hay llamadas perdidas"
        }
        textView.setText(resultado)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == siLecturaLlamadas){
            setTitle("PERMISO OTORGADO")
        }
    }
}
