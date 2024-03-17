package dev.bytetech.weatherapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dev.bytetech.weatherapi.databinding.ActivityMainBinding
import dev.bytetech.weatherapi.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    var index = 0
    private lateinit var binds: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binds = ActivityMainBinding.inflate(layoutInflater)
        installSplashScreen()
        setContentView(binds.root)
        supportFragmentManager
            .beginTransaction().replace(R.id.place_holder, MainFragment.newInstance())
            .commit()
    }

}