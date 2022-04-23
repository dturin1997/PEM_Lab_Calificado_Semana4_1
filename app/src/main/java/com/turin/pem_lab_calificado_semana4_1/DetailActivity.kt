package com.turin.pem_lab_calificado_semana4_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.turin.pem_lab_calificado_semana4_1.databinding.ActivityDetailBinding

const val PARAMETER_EXTRA_NOMBRE = "nombre"
const val PARAMETER_EXTRA_CORREO = "correo"
const val PARAMETER_EXTRA_OFICINA = "oficina"
const val PARAMETER_EXTRA_NUMERO = "numero"

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        val extras = this.intent.extras

        if (extras != null) {
            if (extras.get(PARAMETER_EXTRA_NOMBRE) != null) {
                binding.edtNombre.setText(extras.getString(PARAMETER_EXTRA_NOMBRE))
            }

            if (extras.get(PARAMETER_EXTRA_CORREO) != null) {
                binding.edtCorreo.setText(extras.getString(PARAMETER_EXTRA_CORREO))
            }

            if (extras.get(PARAMETER_EXTRA_OFICINA) != null) {
                binding.edtOficina.setText(extras.getString(PARAMETER_EXTRA_OFICINA))
            }
            if (extras.get(PARAMETER_EXTRA_NUMERO) != null) {
                binding.edtNumero.setText(extras.getString(PARAMETER_EXTRA_NUMERO))
            }
        }

        listenerCallButtons()
    }

    fun listenerCallButtons() {
        binding.btnGuardar.setOnClickListener {
            val intent = Intent()
            intent.putExtra("nombre", binding.edtNombre.getText().toString())
            intent.putExtra("correo", binding.edtCorreo.getText().toString())
            intent.putExtra("oficina", binding.edtOficina.getText().toString())
            intent.putExtra("numero", binding.edtNumero.getText().toString())
            setResult(RESULT_OK, intent) // Set ResultCode and DataIntent
            finish()
        }
    }
}