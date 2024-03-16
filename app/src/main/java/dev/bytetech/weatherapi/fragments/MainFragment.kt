package dev.bytetech.weatherapi.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import dev.bytetech.weatherapi.MainViewModel
import dev.bytetech.weatherapi.adapters.VpAdapter
import dev.bytetech.weatherapi.adapters.WeatherAdapter
import dev.bytetech.weatherapi.adapters.WeatherModel
import dev.bytetech.weatherapi.databinding.FragmentMainBinding
import org.json.JSONObject

const val API_KEY = "" // enter your API key from this site "https://www.weatherapi.com/"

class MainFragment : Fragment() {
    private val fList = listOf(
        hours.newInstance(),
        days.newInstance()
    )
    private val tList = listOf(
        "Hours",
        "Days"
    )
    private lateinit var pLauncher: ActivityResultLauncher<String>
    private lateinit var binds: FragmentMainBinding
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binds = FragmentMainBinding.inflate(inflater, container, false)
        return binds.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chekPermission()
        init()
        requestWeaterData("Empty")
        updateCurrentCard()
    }

    private fun init() = with(binds) {
        val adapter = VpAdapter(activity as FragmentActivity, fList)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            tab.text = tList[pos]
        }.attach()
    }

    private fun updateCurrentCard() = with(binds){
        model.liveDataCurrent.observe(viewLifecycleOwner){
            val maxMinTemp = "${it.maxTemp}°C / ${it.minTemp}°C"
        tvData.text = it.time
        tvCity.text = it.city
        tvCurrentTemp.text = "${it.currentTemp.ifEmpty {"${it.maxTemp}°C / ${it.minTemp}"}}°C"
        tvCondition.text = it.condition
        tvMaxMin.text = if (it.currentTemp.isEmpty())" " else maxMinTemp
        Picasso.get().load("https:" + it.imageUrl).into(imWeather)
        }
    }

    private fun permissionListener() {
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun chekPermission() {
        if (!isPermissionGrantes(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun requestWeaterData(city: String) {
        val url =
            "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY&q=$city&days=3&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result -> parsWeatherData(result)
            },
            { error ->
                Log.d("INFVOLLEY", "Volley error $error")
            }
        )
        queue.add(request)
    }

    private fun parsWeatherData(result: String) {
        val mainObject = JSONObject(result)
        val list = parseDays(mainObject)
        parseCurrentData(mainObject, list[0])
    }

    private fun parseDays(mainObject: JSONObject): List<WeatherModel>{
        val list = ArrayList<WeatherModel>()
        val daysArray = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
        val name = mainObject.getJSONObject("location").getString("name")
        for (i in 0 until daysArray.length()){
            val day = daysArray[i] as JSONObject
            val item = WeatherModel(
                name,
                day.getString("date"),
                day.getJSONObject("day").getJSONObject("condition").getString("text"),
                "",
                day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString(),
                day.getJSONObject("day").getString("mintemp_c").toFloat().toInt().toString(),
                day.getJSONObject("day").getJSONObject("condition").getString("icon"),
                day.getJSONArray("hour").toString()
            )
            list.add(item)
        }
        model.liveDataList.value = list
        return list
    }

    private fun parseCurrentData(mainObject: JSONObject, weathewItem: WeatherModel){
        val item = WeatherModel(
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").optString("last_updated"),
            mainObject.getJSONObject("current").getJSONObject("condition").getString("text"),
            mainObject.getJSONObject("current").getString("temp_c"),
            weathewItem.maxTemp,
            weathewItem.minTemp,
            mainObject.getJSONObject("current").getJSONObject("condition").getString("icon"),
            weathewItem.hours
        )
        model.liveDataCurrent.value = item
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()

    }
}