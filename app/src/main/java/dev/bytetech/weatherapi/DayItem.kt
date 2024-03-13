package dev.bytetech.weatherapi

data class DayItem(
    val city: String,
    val time: String,
    val condition: String,
    val imgUrl: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val hours: String,
)
