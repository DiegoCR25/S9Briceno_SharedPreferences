package com.example.s9briceno_sharedpreferences

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PerfilActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var editTextNombre: EditText
    private lateinit var editTextEdad: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var buttonGuardar: Button
    private lateinit var buttonCargar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        sharedPreferencesHelper = SharedPreferencesHelper(this)

        editTextNombre = findViewById(R.id.editTextNombre)
        editTextEdad = findViewById(R.id.editTextEdad)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonGuardar = findViewById(R.id.buttonGuardarPerfil)
        buttonCargar = findViewById(R.id.buttonCargarPerfil)

        buttonGuardar.setOnClickListener {
            val nombre = editTextNombre.text.toString().trim()
            val edad = editTextEdad.text.toString().trim()
            val email = editTextEmail.text.toString().trim()

            if (nombre.isEmpty() || edad.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sharedPreferencesHelper.saveString("perfil_nombre", nombre)
            sharedPreferencesHelper.saveString("perfil_edad", edad)
            sharedPreferencesHelper.saveString("perfil_email", email)

            Toast.makeText(this, "Perfil guardado", Toast.LENGTH_SHORT).show()
        }

        buttonCargar.setOnClickListener {
            val nombre = sharedPreferencesHelper.getString("perfil_nombre", "Sin nombre")
            val edad = sharedPreferencesHelper.getString("perfil_edad", "Sin edad")
            val email = sharedPreferencesHelper.getString("perfil_email", "Sin email")

            editTextNombre.setText(nombre)
            editTextEdad.setText(edad)
            editTextEmail.setText(email)

            Toast.makeText(this, "Perfil cargado", Toast.LENGTH_SHORT).show()
        }
    }

}