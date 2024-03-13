package dev.bytetech.weatherapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.bytetech.weatherapi.databinding.ActivityMainBinding
import dev.bytetech.weatherapi.fragments.MainFragment

const val API_KEY = "5289583a9e334c9483752222242102"
//"https://api.weatherapi.com/v1/current.json?key=5289583a9e334c9483752222242102&q=London&aqi=no"
class MainActivity : AppCompatActivity() {
    var index = 0
    private lateinit var binds: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binds = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binds.root)
        supportFragmentManager
            .beginTransaction().replace(R.id.place_holder, MainFragment.newInstance())
            .commit()
    }

}