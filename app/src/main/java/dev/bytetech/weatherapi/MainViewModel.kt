package dev.bytetech.weatherapi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.bytetech.weatherapi.adapters.WeatherModel

class MainViewModel: ViewModel() {
    val liveDataCurrent = MutableLiveData<WeatherModel>()
    val liveDataList = MutableLiveData<List<WeatherModel>>()
}