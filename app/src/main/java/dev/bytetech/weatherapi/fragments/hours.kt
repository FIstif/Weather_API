package dev.bytetech.weatherapi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dev.bytetech.weatherapi.R
import dev.bytetech.weatherapi.adapters.WeatherAdapter
import dev.bytetech.weatherapi.adapters.WeatherModel
import dev.bytetech.weatherapi.databinding.FragmentHoursBinding

class hours : Fragment() {
    private lateinit var binds: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binds = FragmentHoursBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binds.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
    }

    private fun initRcView() = with(binds){
        rcView.layoutManager =  LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        rcView.adapter = adapter
        val lsit  = listOf(
            WeatherModel(
                "","12:00", "Sunny", "25", "", "", "", ""
            ),
            WeatherModel(
                    "","13:00", "Sunny", "25", "", "", "", ""
            ),
            WeatherModel(
                "","14:00", "Popusk", "28", "", "", "", ""
            ),
        )
        adapter.submitList(lsit)
    }

    companion object {
        @JvmStatic
        fun newInstance() = hours()
    }
}