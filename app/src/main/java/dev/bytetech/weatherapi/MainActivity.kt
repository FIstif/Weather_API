package dev.bytetech.weatherapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.toolbox.Volley
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import dev.bytetech.weatherapi.databinding.ActivityMainBinding
import org.json.JSONObject

const val API_KEY = "5289583a9e334c9483752222242102"
class MainActivity : AppCompatActivity() {
    private lateinit var binds: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binds = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binds.root)
        binds.get.setOnClickListener {
            getRes("London")
     }
    }
        // API Link https://www.weatherapi.com/
    private fun getRes(name: String){
        val url = "https://api.weatherapi.com/v1/current.json?key=$API_KEY&q=$name&aqi=no"
            val queue = Volley.newRequestQueue(this)
            val strinReq = StringRequest(Request.Method.GET,
                url,
                {
                    response ->
                    val obj = JSONObject(response)
                    val temp = obj.getJSONObject("current")
                    val tempC = temp.getString("temp_c")
                    Log.d("LOG_INF", "Response: $response")
                },
                {
                    Log.d("LOG_INF", "Volley error: $it")
                }
            )
            queue.add(strinReq)
    }

}