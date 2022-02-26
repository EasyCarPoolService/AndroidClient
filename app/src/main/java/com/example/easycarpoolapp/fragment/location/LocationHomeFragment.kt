package com.example.easycarpoolapp.fragment.location

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.fragment.chat.dto.ReservedPostDto
import com.example.easycarpoolapp.fragment.location.dto.LocationDto
import com.example.easycarpoolapp.fragment.location.utils.FusedLocationManager
import com.example.easycarpoolapp.fragment.location.utils.MapUtils
import net.daum.mf.map.api.MapView


class LocationHomeFragment : Fragment(), ReservedPostDialogFragment.Callbacks, FusedLocationManager.Callback{

    companion object{
        public fun getInstance() : LocationHomeFragment{
            return LocationHomeFragment()
        }//getInstance()
    }

    private val TAG : String = "LocationHomeFragment"
    private lateinit var binding : com.example.easycarpoolapp.databinding.FragmentLocationHomeBinding
    //private lateinit var mapContainer : ViewGroup
    private var mapView : MapView? =  null
    private var mapUtils : MapUtils? = null
    private lateinit var fusedLocationManager: FusedLocationManager
    private var roomId : String? = null

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

        fusedLocationManager = FusedLocationManager(this, requireContext())
        fusedLocationManager.setLocationClient()

    }//onCreate()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location_home, container, false)

        return binding.root
    }//onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearchUser.setOnClickListener {
            //dialogFragment에 정보 모두 넘겨주고 띄우기
            viewModel.items.value?.let { it1 -> ReservedPostDialogFragment(it1, this).show(requireActivity().supportFragmentManager, "ReservedPostDialogFragment") }

            viewModel.opponentLocation.observe(viewLifecycleOwner, Observer {
                if(!it?.writer!!.equals(LocalUserData.getEmail())){ //상대 위치 표기 RedMark
                    mapUtils?.markOpponentLocation(it)
                }else{//내 위치 표기 blue mark
                    mapUtils?.markMyLocation(it)
                }
            })
        }//예약된 정보 불러오기

    }// onViewCreated()

    override fun onResume() {
        super.onResume()

        if(mapView==null) {
            mapView = MapView(requireContext()).also {
                mapUtils = MapUtils.getInstance(it, requireContext())
                binding.mapView.addView(it)
            }
        }

    }//onResumse()

    override fun onPause() {
        super.onPause()
        roomId = null
    }// 정지시 위치 정보 전송 중단을 위해 roomId = null


    override fun onDetach() {
        super.onDetach()
        LocationRepository.onDestroy()  //repository destroy
    }// onDetach()

    override fun onSendRequestClicked(selectedDto: ReservedPostDto?) {  //subscribe수행
        Log.e(TAG, selectedDto?.locationRoomId.toString())
        roomId = selectedDto?.locationRoomId.toString() //선택된 예약건의 locationRoomId 값
        viewModel.subscribe(roomId!!)
    }   // callback of ReservedPostDialogFragment

    override fun setMapByLocation(location: Location) { //locationDto 전송 <- locationDto는 roomId를 포함
        // 만약 타겟 정해진 경우 send if target is not null
        if(roomId != null){
            viewModel.sendLocation(roomId!!, location)
        }
        mapUtils?.moveToCenter(location)

        Log.e(TAG, location.longitude.toString())

    }// callback of fusedLocationManager

}