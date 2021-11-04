# Simon Dice Juego

## Descripción del proyecto

Desarrollo de un juego de Simón Dice, programado en Kotlin con Android Studio. 
En el juego trata de repetir la secuencia dada por la app, la cual va aumentando en una cada vez que aciertas la misma.
Se realiza la secuencia iluminando y remarcando el botón que tendrá que pulsar el jugador en el mismo orden mostrado.

## Diagrama de flujo de la app
![Estructura app](/app/media/estructura.png)

## Métodos de la app
### onCreate
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
    botonEmpezar.visibility = View.INVISIBLE
    val toast =
    Toast.makeText(applicationContext, "Jugador juega", Toast.LENGTH_SHORT).show()
    Log.d("estado", "Jugador ha empezado a jugar")

            //Al clickar botón empezar mediante forEach de mi HMap activo botones
            verBotones(arrayBotones)
            mostrarRonda(arrayBotones)
        }
    }

### mostrarRonda
    @DelicateCoroutinesApi
    fun mostrarRonda(hashMap: HashMap<Int, Button>) {
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

### ejecutarSecuencia
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

            secuendiaGuardada.add(random)
            random = (0..3).random()
            delay(500L)
            secuencia--
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            comprobarSecuencia(secuendiaGuardada, hashMap, ronda)
        }
        return secuendiaGuardada
    }
### comprobarSecuencia
    @SuppressLint("ShowToast")
    @RequiresApi(Build.VERSION_CODES.N)
    fun comprobarSecuencia(
    arraySecuencia: ArrayList<Int>,
    hashMap: HashMap<Int, Button>,
    ronda: Int
    ) {
    secuenciaJugador.removeAll(secuenciaJugador)

        activarBotones(hashMap)


        hashMap.forEach { (t, u) ->
            u.setOnClickListener {
                secuenciaJugador.add(t)
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
                            delay(2000L)
                            finish()
                            startActivity(intent)
                        }

                    }
                }
            }

        }
    }
## Métodos para tratar botones
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

### Métodos para comprobar Arrays
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

## Uso de la APP
### Pantalla inicial

![Pantalla inicial](/app/media/inicio.png)


Botón empezar a la escucha

### Uso de la APP

Una vez pulsado botón de empezar


![Demostración](https://github.com/jsamperevazquez/SimonGame/blob/desarrollo/app/media/20211104_170644.gif)

Jugador repite secuencia y en caso de error **"Game over"**