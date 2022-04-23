package com.turin.pem_lab_calificado_semana4_1

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.turin.pem_lab_calificado_semana4_1.databinding.ActivityMainBinding

import kotlinx.android.synthetic.main.activity_main.*

const val ACTIVITY_B_REQUEST = 992
const val ACTIVITY_C_REQUEST = 993

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)
        listenerCallButtons()
    }
    private fun listenerCallButtons(){

        binding.btnEditar.setOnClickListener {
        val nombre = binding.tvNombre.text.toString()
        val correo = binding.tvCorreo.text.toString()
        val oficina = binding.tvOficina.text.toString()
        val numero = binding.tvNumero.text.toString()

        validateInputFields(nombre, correo, oficina, numero)
        goDetailActivity(nombre, correo, oficina, numero)
        }

        binding.button1.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tvNumero.text.toString()))
            startActivity(intent)
        }
        binding.button2.setOnClickListener {
            var mobileNumber = tvNumero.text.toString()
            val url =
                "https://api.whatsapp.com/send?phone=${mobileNumber}&text=You%20can%20now%20send%20me%20audio%20and%20video%20messages%20on%20the%20app%20-%20Chirp.%20%0A%0Ahttps%3A//bit.ly/chirp_android"

            val intent = Intent(Intent.ACTION_VIEW).apply {
                this.data = Uri.parse(url)
                this.`package` = "com.whatsapp"
            }

            try {
                startActivity(intent)
            } catch (ex : ActivityNotFoundException){
                Toast.makeText(this, "Whatsapp no esta instalado", Toast.LENGTH_SHORT).show()
            }
        }
        binding.button3.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + tvNumero.text.toString()))
            intent.putExtra("sms_body", tvNombre.text.toString())
            startActivity(intent)
        }
    }

    private fun goDetailActivity(nombre: String, correo: String, oficina: String, numero: String) {

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(PARAMETER_EXTRA_NOMBRE, nombre)
        intent.putExtra(PARAMETER_EXTRA_CORREO, correo)
        intent.putExtra(PARAMETER_EXTRA_OFICINA, oficina)
        intent.putExtra(PARAMETER_EXTRA_NUMERO, numero)
        startActivityForResult(intent, ACTIVITY_B_REQUEST)

    }

    private fun validateInputFields(nombre: String, correo: String, oficina: String, numero: String) {
        if (nombre.isBlank() && correo.isBlank() && oficina.isBlank() && numero.isBlank()) return
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(ContentValues.TAG, "requestCode:$requestCode")
        Log.d(ContentValues.TAG, "resultCode:$resultCode")
        Log.d(ContentValues.TAG, "data:" + android.R.attr.data)

        when (requestCode) {
            ACTIVITY_B_REQUEST -> {
                Toast.makeText(this, "Regresamos del Activity Detail", Toast.LENGTH_SHORT).show()
                if (resultCode === RESULT_OK) {
                    val extras = data?.extras

                    if (extras != null) {
                        if (extras.get(PARAMETER_EXTRA_NOMBRE) != null) {
                            tvNombre.text = extras.getString(PARAMETER_EXTRA_NOMBRE)
                        }

                        if (extras.get(PARAMETER_EXTRA_CORREO) != null) {
                            tvCorreo.text = extras.getString(PARAMETER_EXTRA_CORREO)
                        }

                        if (extras.get(PARAMETER_EXTRA_OFICINA) != null) {
                            tvOficina.text = extras.getString(PARAMETER_EXTRA_OFICINA)
                        }

                        if (extras.get(PARAMETER_EXTRA_NUMERO) != null) {
                            tvNumero.text = extras.getString(PARAMETER_EXTRA_NUMERO)
                        }
                    }
                }

            }
        }

    }
}