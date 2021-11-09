package com.cursoandroid.simongame

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SoundEffectConstants
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.coroutines.*

open class MainActivity : AppCompatActivity() {
    var contadorRonda = 1
    var secuenciaJugador = ArrayList<Int>()
    private lateinit var mediaPlayer: MediaPlayer

    @SuppressLint("WrongViewCast")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textoAnim = findViewById<TextView>(R.id.testoAnim)
        val swars: Animation = AnimationUtils.loadAnimation(this, R.anim.animationfile)
        textoAnim.startAnimation(swars)
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
        mediaPlayer = MediaPlayer.create(this,R.raw.swars)
        mediaPlayer.start()
        botonEmpezar.setOnClickListener {
            botonEmpezar.visibility = View.INVISIBLE
            val toast =
                Toast.makeText(applicationContext, "Jugador juega", Toast.LENGTH_SHORT).show()
            Log.d("estado", "Jugador ha empezado a jugar")

            //Al clickar botón empezar mediante forEach de mi HMap activo botones
            verBotones(arrayBotones)
            mostrarRonda(arrayBotones)
        }
    }

    @DelicateCoroutinesApi
    fun mostrarRonda(hashMap: HashMap<Int, Button>) {
        val textoAnim = findViewById<TextView>(R.id.testoAnim)
        textoAnim.text = ""
        val toast = Toast.makeText(applicationContext, "Mostrando ronda", Toast.LENGTH_SHORT).show()
        Log.d("ronda", "Mostrando ronda")
        val ronda: TextView = findViewById(R.id.textViewRonda)
        ronda.visibility = View.VISIBLE
        ronda.setText("Ronda " + this.contadorRonda).toString()
        // Uso launch para crear una tarea de corrutina que identifico con job
        val job = GlobalScope.launch(Dispatchers.Main) {
            // la función estará dentro de la corrutina
            ejecutarSecuencia(contadorRonda, hashMap)
        }
    }

    @DelicateCoroutinesApi
    @SuppressLint("CutPasteId")
    suspend fun ejecutarSecuencia(ronda: Int, hashMap: HashMap<Int, Button>): ArrayList<Int> {
        var toast =
            Toast.makeText(applicationContext, "Mostrando secuencia", Toast.LENGTH_SHORT).show()
        var secuendiaGuardada = arrayListOf<Int>()
        var secuencia = contadorRonda
        var random = (0..3).random()
        Log.d("secuencia", "Ejecutando secuencia")
        desactivarBotones(hashMap)

        while (secuencia > 0) {
            var boton = hashMap[random]
            delay(300L)
            boton?.setBackgroundResource(
                when (random) {
                    0 -> R.drawable.boton_rojo_resal
                    1 -> R.drawable.boton_verde_resal
                    2 -> R.drawable.boton_amarillo_resal
                    3 -> R.drawable.boton_azul_resal

                    else -> R.drawable.boton_azul
                }
            )
            delay(300L)
            boton?.setBackgroundResource(
                when (random) {
                    0 -> R.drawable.boton_rojo
                    1 -> R.drawable.boton_verde
                    2 -> R.drawable.boton_amarillo
                    3 -> R.drawable.boton_azul

                    else -> R.drawable.boton_azul
                }
            )

            secuendiaGuardada.add(random)
            random = (0..3).random()
            delay(300L)
            secuencia--
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            comprobarSecuencia(secuendiaGuardada, hashMap, ronda)
        }
        return secuendiaGuardada
    }

    @SuppressLint("ShowToast", "WrongViewCast")
    @RequiresApi(Build.VERSION_CODES.N)
    fun comprobarSecuencia(
        arraySecuencia: ArrayList<Int>,
        hashMap: HashMap<Int, Button>,
        ronda: Int
    ) {
        secuenciaJugador.removeAll(secuenciaJugador)
        val texGameOver: ImageView = findViewById(R.id.gameOverView)
        activarBotones(hashMap)
        val arraySonidos = hashMapOf<Int,MediaPlayer>()
        arraySonidos[0] = MediaPlayer.create(this,R.raw.s)
        arraySonidos[1] = MediaPlayer.create(this, R.raw.tie)
        arraySonidos[2] = MediaPlayer.create(this,R.raw.wee)
        arraySonidos[3] = MediaPlayer.create(this,R.raw.xwin)

        hashMap.forEach { (t, u) ->
            u.setOnClickListener {
                secuenciaJugador.add(t)
                arraySonidos[t]?.start()
                Log.d("BotonPulsado", "Jugador pulsa " + "${u.id}")
                if (secuenciaJugador.size == arraySecuencia.size) {
                    if (equals(arraySecuencia)) {
                        contadorRonda++
                        mostrarRonda(hashMap)
                    } else {
                        val toast =
                            Toast.makeText(applicationContext, "Game Over", Toast.LENGTH_SHORT)
                                .show()
                        GlobalScope.launch(Dispatchers.Main) {
                            mediaPlayer.stop()
                            texGameOver.visibility = View.VISIBLE;
                            noVerBotones(hashMap)
                            delay(2000L)
                            finish()
                            startActivity(intent)
                        }

                    }
                }
            }

        }
    }

    // Metodoos para tratar los botones (Reutilizar código y limpieza)
    @RequiresApi(Build.VERSION_CODES.N)
    fun activarBotones(hashMap: HashMap<Int, Button>) {
        hashMap.forEach { (t, u) -> u.isEnabled = true }
    }

    fun desactivarBotones(hashMap: HashMap<Int, Button>) {
        hashMap.forEach { (t, u) -> u.isEnabled = false }
    }

    fun verBotones(hashMap: HashMap<Int, Button>) {
        hashMap.forEach { (t, u) -> u.visibility = View.VISIBLE }
    }

    fun noVerBotones(hashMap: HashMap<Int, Button>) {
        hashMap.forEach { (t, u) -> u.visibility = View.INVISIBLE }
    }

    // Métodos para comprobar los arrays
    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is List<*>) return false

        return orderedEquals(secuenciaJugador, other)
    }

    internal fun orderedEquals(c: Collection<*>, other: Collection<*>): Boolean {
        if (c.size != other.size) return false

        val otherIterator = other.iterator()
        for (elem in c) {
            val elemOther = otherIterator.next()
            if (elem != elemOther) {
                return false
            }
        }
        return true
    }
    // Función para detener la música de fondo cuando la actividad está onPause
    @Override
    override fun onPause(){
        super.onPause()
        mediaPlayer.stop()
    }
    }

