package apps.ejemplo.hunterasteroids

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.hunterasteroids.R

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.Theme_HunterAsteroids)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //cambio de activities
        val botonPlay = findViewById<Button>(R.id.btnPlay)
        botonPlay.setOnClickListener {
            val lanzar = Intent(this, Pantallaplay::class.java)
            startActivity(lanzar)

        }
        val botonCred = findViewById<Button>(R.id.btnCreditos)
        botonCred.setOnClickListener {
            val lanzar = Intent(this, creditos::class.java)
            startActivity(lanzar)
        }


        val botonInfo = findViewById<Button>(R.id.btnInfo)
        botonInfo.setOnClickListener {
            val lanzar = Intent(this, PantallaInfo::class.java)
            startActivity(lanzar)

        }

        //Iniciar musica al abrir la aplicación
        mediaPlayer = MediaPlayer.create(this, R.raw.musica)
        mediaPlayer.start()

    }

}