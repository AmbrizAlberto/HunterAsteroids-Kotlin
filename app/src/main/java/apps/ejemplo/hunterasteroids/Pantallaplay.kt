package apps.ejemplo.hunterasteroids

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.example.hunterasteroids.R

class Pantallaplay : AppCompatActivity() {
    private lateinit var mainLayout: ViewGroup
    private lateinit var imageNave: ImageView

    private var xDelta = 0
    private var yDelta = 0

    lateinit var showerButton: Button
    lateinit var asteroide: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantallaplay)
        val botonVolver = findViewById<Button>(R.id.btnVolver)
        botonVolver.setOnClickListener {
            val lanzar = Intent(this, MainActivity::class.java)
            startActivity(lanzar)

        }

        mainLayout = findViewById(R.id.main)
        imageNave = findViewById(R.id.imageNave)

        showerButton = findViewById<Button>(R.id.showerButton)
        asteroide = findViewById(R.id.asteroid)



        //Para mover nave
        imageNave.setOnTouchListener(onTouchListener())

        //Iniciar la actividad de llover asteroides e intentar derribar la nave


        showerButton.setOnClickListener {
            ciclo()

        }

    }



    //ciclo para llover tres asteroides
    private fun ciclo(){
        for (i in 1..3){
            shower()
        }
    }

    //Codigo para hacer llover los asteroides
    private fun shower() {

        //Cree una nueva vista de asteroide en una posición X aleatoria sobre el contenedor.
        //Haz que gire el Botón sobre su centro mientras cae al fondo.

        //variables locales
        val container = asteroide.parent as ViewGroup
        val containerW = container.width
        var containerH = container.height
        var asteroideW: Float = asteroide.width.toFloat()
        var asteroideH: Float = asteroide.height.toFloat()

        // Cree un nuevo asteroide (un ImageView en el diseño que contiene una imagen de asteroide dibujable)
        // y lo agregamos al contenedor
        val newAsteroide = AppCompatImageView(this)
        newAsteroide.setImageResource(R.drawable.asteroide3)
        newAsteroide.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        container.addView(newAsteroide)

        // Escala la vista aleatoriamente entre 10-160% de su tamaño predeterminado
        newAsteroide.scaleX = Math.random().toFloat() * .6f + .3f
        newAsteroide.scaleY = newAsteroide.scaleX
        asteroideW *= newAsteroide.scaleX
        asteroideH *= newAsteroide.scaleY

        // Posiciona la vista en un lugar aleatorio entre
        // los bordes izquierdo y derecho del contenedor
        newAsteroide.translationX = Math.random().toFloat() * containerW - asteroideW / 2

        // Crear un animador que mueva la vista desde una posición de inicio justo sobre el contenedor
        // a una posición final justo debajo del contenedor. Configure un interpolador acelerado para dar
        // es una sensación de gravedad/caída
        val mover = ObjectAnimator.ofFloat(newAsteroide, View.TRANSLATION_Y, -asteroideH, containerH + asteroideH)
        mover.interpolator = AccelerateInterpolator(1f)

        // Crea un animador para rotarBotón el
        // ver alrededor de su centro hasta tres veces
        val rotator = ObjectAnimator.ofFloat(
            newAsteroide, View.ROTATION,
            (Math.random() * 1080).toFloat()
        )
        rotator.interpolator = LinearInterpolator()

        // Usa un AnimatorSet para jugar la caída y
        // animadores giratorios en paralelo durante un tiempo
        // de medio segundo a dos segundos
        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        set.duration = (Math.random() * 10000).toLong()

        // Cuando termine la animación, elimine el
        // vista creada desde el contenedor
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                container.removeView(newAsteroide)
            }
        })

        // Inicie la animación
        set.start()
    }

    //Codigo para animar la lluvia de asteroides
    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {

        // Este método de extensión escucha inicio/fin
        // eventos en una animación y deshabilita
        // la vista dada para la totalidad de esa animación.
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

    //Codigo para mover la nave
    @SuppressLint("ClickableViewAccessibility")
    private fun onTouchListener(): OnTouchListener {
        return OnTouchListener { view, event ->
            val x = event.rawX.toInt()
            val y = event.rawY.toInt()

            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    val lParams = view.layoutParams as RelativeLayout.LayoutParams
                    xDelta = x - lParams.leftMargin
                    yDelta = y - lParams.topMargin
                }
                MotionEvent.ACTION_MOVE -> {
                    val layoutParams = view.layoutParams as RelativeLayout.LayoutParams
                    layoutParams.leftMargin = x - xDelta
                    layoutParams.topMargin = y - yDelta
                    layoutParams.rightMargin = 0
                    layoutParams.bottomMargin = 0
                    view.layoutParams = layoutParams
                }
            }
            mainLayout.invalidate()
            true
        }
    }
}








