package com.example.easycarpoolapp.fragment.location

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.R
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class LocationHomeFragment : Fragment() {

    companion object{
        public fun getInstance() : LocationHomeFragment{
            return LocationHomeFragment()
        }//getInstance()
    }

    private lateinit var binding : com.example.easycarpoolapp.databinding.FragmentLocationHomeBinding
    private lateinit var mapContainer : ViewGroup
    private lateinit var mapView : MapView
    private val viewModel : LocationHomeViewModel by lazy {
        ViewModelProvider(this).get(LocationHomeViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LocationRepository.init(requireContext())   //repository 초기화
    }//onAttach()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getReservedPost() //User Email에 해당하는 예약된 정보 조회
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearchUser.setOnClickListener {
            //dialogFragment에 정보 모두 넘겨주고 띄우기
            viewModel.items.value?.let { it1 -> ReservedPostDialogFragment(it1).show(requireActivity().supportFragmentManager, "ReservedPostDialogFragment") }
        }//예약된 정보 불러오기

    }// onViewCreated()

    override fun onDetach() {
        super.onDetach()
        LocationRepository.onDestroy()  //repository destroy
    }//onDetach()



}