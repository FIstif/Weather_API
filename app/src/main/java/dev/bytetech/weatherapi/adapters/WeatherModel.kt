package dev.bytetech.weatherapi.adapters

import android.icu.text.UnicodeFilter
import android.icu.text.UnicodeSet


data class WeatherModel(
    val city: String,
    val time: String,
    val condition: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val imageUrl: String,
    val hours: String

)
