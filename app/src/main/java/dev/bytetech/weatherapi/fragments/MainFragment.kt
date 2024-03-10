package dev.bytetech.weatherapi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.bytetech.weatherapi.R
import dev.bytetech.weatherapi.databinding.ActivityMainBinding
import dev.bytetech.weatherapi.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binds: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binds = FragmentMainBinding.inflate(inflater, container, false)
        return binds.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()

    }
}