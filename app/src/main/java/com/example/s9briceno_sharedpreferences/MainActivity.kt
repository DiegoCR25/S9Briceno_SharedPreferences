package com.example.s9briceno_sharedpreferences

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var editTextUsername: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonLoad: Button
    private lateinit var buttonClear: Button
    private lateinit var textViewResult: TextView

    private lateinit var textViewVisitCount: TextView
    private lateinit var buttonResetCount: Button
    private lateinit var buttonIrPerfil: Button
    private lateinit var switchModoOscuro: Switch
    private lateinit var mainLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar SharedPreferencesHelper
        sharedPreferencesHelper = SharedPreferencesHelper(this)

        // Inicializar vistas
        initViews()

        // Aplicar tema
        val esModoOscuro = sharedPreferencesHelper.getBoolean("modo_oscuro", false)
        switchModoOscuro.isChecked = esModoOscuro
        aplicarModo(esModoOscuro)

        // Configurar listeners
        setupListeners()

        // Verificar si es la primera vez que se abre la app
        checkFirstTime()

        //Actualizar el contador de visitas de la app
        updateVisitCount()

    }
    private fun initViews() {
        editTextUsername = findViewById(R.id.editTextUsername)
        buttonSave = findViewById(R.id.buttonSave)
        buttonLoad = findViewById(R.id.buttonLoad)
        buttonClear = findViewById(R.id.buttonClear)
        textViewResult = findViewById(R.id.textViewResult)

        textViewVisitCount = findViewById(R.id.textViewVisitCount)
        buttonResetCount = findViewById(R.id.buttonResetCount)
        buttonIrPerfil = findViewById(R.id.buttonIrPerfil)
        switchModoOscuro = findViewById(R.id.switchModoOscuro)
        mainLayout = findViewById(R.id.main)
    }

    private fun setupListeners() {
        buttonSave.setOnClickListener {
            saveData()
        }

        buttonLoad.setOnClickListener {
            loadData()
        }

        buttonClear.setOnClickListener {
            clearAllData()
        }

        buttonResetCount.setOnClickListener {
            sharedPreferencesHelper.resetVisitCount()
            textViewVisitCount.text = "Visitas: 0"
            Toast.makeText(this, "Contador reiniciado", Toast.LENGTH_SHORT).show()
        }

        buttonIrPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }

        switchModoOscuro.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferencesHelper.saveBoolean("modo_oscuro", isChecked)
            aplicarModo(isChecked)
        }

    }

    private fun saveData() {
        val username = editTextUsername.text.toString().trim()

        if (username.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa un nombre", Toast.LENGTH_SHORT).show()
            return
        }

        // Guardar datos
        sharedPreferencesHelper.saveString(SharedPreferencesHelper.KEY_USERNAME, username)
        sharedPreferencesHelper.saveBoolean(SharedPreferencesHelper.KEY_IS_FIRST_TIME, false)
        sharedPreferencesHelper.saveInt(SharedPreferencesHelper.KEY_USER_ID, (1000..9999).random())

        Toast.makeText(this, "Datos guardados exitosamente", Toast.LENGTH_SHORT).show()
        editTextUsername.setText("")
    }

    private fun loadData() {
        val username = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_USERNAME, "Sin nombre")
        val isFirstTime = sharedPreferencesHelper.getBoolean(SharedPreferencesHelper.KEY_IS_FIRST_TIME, true)
        val userId = sharedPreferencesHelper.getInt(SharedPreferencesHelper.KEY_USER_ID, 0)

        val result = "Usuario: $username\nID: $userId\nPrimera vez: ${if (isFirstTime) "Sí" else "No"}"
        textViewResult.text = result
    }

    private fun clearAllData() {
        sharedPreferencesHelper.clearAll()
        textViewResult.text = ""
        editTextUsername.setText("")
        Toast.makeText(this, "Todas las preferencias han sido eliminadas", Toast.LENGTH_SHORT).show()
    }

    private fun checkFirstTime() {
        val isFirstTime = sharedPreferencesHelper.getBoolean(SharedPreferencesHelper.KEY_IS_FIRST_TIME, true)

        if (isFirstTime) {
            Toast.makeText(this, "¡Bienvenido por primera vez!", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateVisitCount() {
        val currentCount = sharedPreferencesHelper.getVisitCount() + 1
        sharedPreferencesHelper.saveVisitCount(currentCount)
        textViewVisitCount.text = "Visitas: $currentCount"
    }

    private fun aplicarModo(modoOscuro: Boolean) {
        if (modoOscuro) {
            mainLayout.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
            textViewResult.setTextColor(resources.getColor(android.R.color.white))
            textViewVisitCount.setTextColor(resources.getColor(android.R.color.white))
        } else {
            mainLayout.setBackgroundColor(resources.getColor(android.R.color.white))
            textViewResult.setTextColor(resources.getColor(android.R.color.black))
            textViewVisitCount.setTextColor(resources.getColor(android.R.color.black))
        }
    }

}