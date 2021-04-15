package id.haweje.submission1_githubuser.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.haweje.submission1_githubuser.R
import id.haweje.submission1_githubuser.ui.home.MainActivity
import java.util.*
import  kotlin.concurrent.schedule


class SplashScreen : AppCompatActivity() {

    companion object{
        const val DELAY = 1000L
    }

    private var timer = Timer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

       timer.schedule(DELAY){
           val intent = Intent(this@SplashScreen, MainActivity::class.java)
           startActivity(intent)
           finish()
       }

    }

    override fun onPause() {
        timer.cancel()
        super.onPause()
    }


}
