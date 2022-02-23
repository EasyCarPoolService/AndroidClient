package com.example.easycarpoolapp.fragment.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentLocationHomeBinding
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class LocationHomeFragment : Fragment() {

    companion object{
        public fun getInstance() : LocationHomeFragment{
            return LocationHomeFragment()
        }//getInstance()
    }


    private lateinit var binding : FragmentLocationHomeBinding
    private lateinit var mapContainer : ViewGroup
    private lateinit var mapView : MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location_home, container, false)

        mapContainer = binding.mapView
        mapView = MapView(requireActivity())
        mapContainer.addView(mapView)

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);


        return binding.root
    }//onCreateView()



}