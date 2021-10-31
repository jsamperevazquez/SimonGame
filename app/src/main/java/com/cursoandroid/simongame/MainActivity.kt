package com.cursoandroid.simongame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonEmpezar: Button = findViewById(R.id.botonStart)
        val bAzul: Button = findViewById(R.id.botonAzul)
        val bAmarillo: Button = findViewById(R.id.botonAmarillo)
        val bRojo: Button = findViewById(R.id.botonRojo)
        val bVerde: Button = findViewById(R.id.botonVerde)
        val textRonda: TextView = findViewById(R.id.textViewRonda)
        val arrayBotones = hashMapOf<Int, Button>()
        arrayBotones[0] = bRojo
        arrayBotones[1] = bVerde
        arrayBotones[2] = bAmarillo
        arrayBotones[3] = bAzul
        botonEmpezar.setOnClickListener {
            val toast = Toast.makeText(applicationContext, "Jugador juega", Toast.LENGTH_SHORT).show()
            Log.d("estado", "Jugador ha empezado a jugar")
        }
    }
    fun mostrarRonda() {
        val toast = Toast.makeText(applicationContext, "Mostrando ronda", Toast.LENGTH_SHORT).show()
        Log.d("ronda", "Mostrando ronda")
    }
    fun ejecutarSecuencia() {
        var toast = Toast.makeText(applicationContext, "Mostrando secuencia", Toast.LENGTH_SHORT).show()
    }
    fun comprobarSecuencia(){}

}