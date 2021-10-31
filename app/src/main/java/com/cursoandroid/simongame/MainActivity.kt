package com.cursoandroid.simongame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

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
    }
    fun mostrarRonda() {}
    fun ejecutarSecuencia() {}
    fun comprobarSecuencia(){}

}