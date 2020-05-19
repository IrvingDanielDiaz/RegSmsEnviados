package mx.edu.ittepic.regsmsenviados

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    val siLecturaSMS = 15
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_SMS),siLecturaSMS)
        }

        button.setOnClickListener {
            leerRegistroSMS()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == siLecturaSMS){
            setTitle("PERMISO OTORGADO")
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun leerRegistroSMS() {
        var resultado = ""
        val cursorSMS = contentResolver.query(Telephony.Sms.CONTENT_URI,null,null,null,null)

        //INDICES
        val direccion = cursorSMS!!.getColumnIndex(Telephony.Sms.ADDRESS)
        val texto = cursorSMS.getColumnIndex(Telephony.Sms.BODY)
        val fecha = cursorSMS.getColumnIndex(Telephony.Sms.DATE)
        val tipo = cursorSMS.getColumnIndex(Telephony.Sms.TYPE)

        while(cursorSMS.moveToNext()){
            var direccionC = cursorSMS.getString(direccion)
            var textoC = cursorSMS.getString(texto)
            var tipoC = cursorSMS.getString(tipo)
            var fechaC = cursorSMS.getString(fecha)
            var convfechaC = Date(fechaC.toLong())
            if(tipoC != "1"){
                resultado += "---------------\n"+
                        "Número Telefónico: "+ direccionC +"\n"+
                        "Mensaje: "+ textoC +"\n"+
                        "Fecha" + convfechaC+"\n"
            }
        }

        salida.setText("SMS ENVIADOS:\n"+resultado)


    }


}
