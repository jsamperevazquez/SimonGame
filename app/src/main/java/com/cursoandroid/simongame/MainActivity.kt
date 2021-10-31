package com.cursoandroid.simongame

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var contadorRonda = 4

    @RequiresApi(Build.VERSION_CODES.N)
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
            val toast =
                Toast.makeText(applicationContext, "Jugador juega", Toast.LENGTH_SHORT).show()
            Log.d("estado", "Jugador ha empezado a jugar")

            //Al clickar botón empezar mediante forEach de mi HMap activo botones
            arrayBotones.forEach { (t, u) -> u.visibility = View.VISIBLE }
            botonEmpezar.visibility = View.INVISIBLE
            mostrarRonda()
        }
        bAmarillo.setOnClickListener {
            Log.d("amarillo", "Jugador pulsa botón amarillo")

        }
        bAzul.setOnClickListener {
            Log.d("azul", "Jugador pulsa botón azul")
        }
        bVerde.setOnClickListener {
            Log.d("verde", "Jugador presiona botón verde")
        }
        bRojo.setOnClickListener {
            Log.d("rojo", "Jugador presiona botón rojo")
        }

    }

    fun mostrarRonda() {
        val toast = Toast.makeText(applicationContext, "Mostrando ronda", Toast.LENGTH_SHORT).show()
        Log.d("ronda", "Mostrando ronda")
        val ronda: TextView = findViewById(R.id.textViewRonda)
        ronda.setText("Ronda " + this.contadorRonda).toString()
        // Uso launch para crear una tarea de corrutina que identifico con job
        val job = GlobalScope.launch(Dispatchers.Main) {
            // la función estará dentro de la corrutina
            ejecutarSecuencia(contadorRonda)
        }
    }

    suspend fun ejecutarSecuencia(ronda: Int): ArrayList<Int> {
        var toast =
            Toast.makeText(applicationContext, "Mostrando secuencia", Toast.LENGTH_SHORT).show()
        var secuendiaGuardada = arrayListOf<Int>()
        var secuencia = contadorRonda
        var random = (0..3).random()
        Log.d("secuencia", "Ejecutando secuencia")
        findViewById<Button>(R.id.botonRojo).isEnabled = false
        findViewById<Button>(R.id.botonAmarillo).isEnabled = false
        findViewById<Button>(R.id.botonVerde).isEnabled = false
        findViewById<Button>(R.id.botonAzul).isEnabled = false
        val arrayBotones = hashMapOf<Int, Button>()
        arrayBotones[0] = findViewById(R.id.botonRojo)
        arrayBotones[1] = findViewById(R.id.botonVerde)
        arrayBotones[2] = findViewById(R.id.botonAmarillo)
        arrayBotones[3] = findViewById(R.id.botonAzul)
        // todo -> Crear un array en función de la ronda

        while (secuencia > 0) {
            var boton = arrayBotones[random]
            delay(500L)
            boton?.setBackgroundResource(
                when (random) {
                    0 -> R.drawable.boton_rojo_resal
                    1 -> R.drawable.boton_verde_resal
                    2 -> R.drawable.boton_amarillo_resal
                    3 -> R.drawable.boton_azul_resal

                    else -> R.drawable.boton_azul
                }
            )
            delay(500L)
            boton?.setBackgroundResource(
                when (random) {
                    0 -> R.drawable.boton_rojo
                    1 -> R.drawable.boton_verde
                    2 -> R.drawable.boton_amarillo
                    3 -> R.drawable.boton_azul

                    else -> R.drawable.boton_azul
                }
            )


            random = (0..3).random()
            delay(500L)
            secuendiaGuardada.add(boton.hashCode())
            secuencia--
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            comprobarSecuencia(secuendiaGuardada)
        }
        return secuendiaGuardada
    }
    @RequiresApi(Build.VERSION_CODES.N)
    fun comprobarSecuencia(arraySecuencia: ArrayList<Int>) {
        var secuenciaJugador = ArrayList<Int>()
        val arrayBotones = hashMapOf<Int, Button>()
        arrayBotones[0] = findViewById(R.id.botonRojo)
        arrayBotones[1] = findViewById(R.id.botonVerde)
        arrayBotones[2] = findViewById(R.id.botonAmarillo)
        arrayBotones[3] = findViewById(R.id.botonAzul)
        arrayBotones.forEach { (t, u) -> u.isEnabled = true }
    }

}
