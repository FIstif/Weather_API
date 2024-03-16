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
import com.google.android.material.tabs.TabLayoutMediator
import dev.bytetech.weatherapi.adapters.VpAdapter
import dev.bytetech.weatherapi.databinding.FragmentMainBinding

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
    }

    private fun init() = with(binds){
        val adapter = VpAdapter(activity as FragmentActivity, fList)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout,viewPager){
            tab, pos -> tab.text = tList[pos]
        }.attach()
    }

    private fun permissionListener(){
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            Toast.makeText(activity,"Permission is $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun chekPermission(){
        if(!isPermissionGrantes(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()

    }
}