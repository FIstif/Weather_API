package dev.bytetech.weatherapi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dev.bytetech.weatherapi.MainViewModel
import dev.bytetech.weatherapi.R
import dev.bytetech.weatherapi.adapters.WeatherAdapter
import dev.bytetech.weatherapi.adapters.WeatherModel
import dev.bytetech.weatherapi.databinding.FragmentDaysBinding

class days : Fragment(), WeatherAdapter.Listener {
    private lateinit var binds: FragmentDaysBinding
    private lateinit var adapter: WeatherAdapter
    private val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binds = FragmentDaysBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binds.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.liveDataList.observe(viewLifecycleOwner){
            adapter.submitList(it.subList(1, it.size))
        }
    }

    private fun init() = with(binds){
        adapter = WeatherAdapter(this@days)
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = days()
    }

    override fun onClick(item: WeatherModel) {
        model.liveDataCurrent.value = item
    }
}