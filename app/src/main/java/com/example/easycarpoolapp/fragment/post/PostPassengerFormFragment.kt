package com.example.easycarpoolapp.fragment.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentPostPassengerFormBinding
import com.example.easycarpoolapp.fragment.post.utils.DatePickerManager
import com.example.easycarpoolapp.fragment.post.utils.SpinnerDataManager
import com.example.easycarpoolapp.fragment.post.utils.TimePickerManager
import com.karrel.timepicker.RellTimePicker

class PostPassengerFormFragment : Fragment() {

    companion object{
        public fun getInstance() : PostPassengerFormFragment{
            return PostPassengerFormFragment()
        }
    }

    private lateinit var binding :FragmentPostPassengerFormBinding
    private lateinit var spinnerDataManager: SpinnerDataManager
    private lateinit var datePickerManager: DatePickerManager
    private lateinit var timePickerManager : TimePickerManager

    private var departure_si:String? = null
    private var departure_gu:String? = null
    private var departure_dong:String? = null
    private var destination_si:String? = null
    private var destination_gu:String? = null
    private var destination_dong:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spinnerDataManager = SpinnerDataManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_post_passenger_form,
            container,
            false
        )
        //datePickerManager의 리스너는 특정 버튼의 text를 변경하므로 binding객체를 생성한 이후에 객체 생성
        datePickerManager = DatePickerManager(requireContext(), binding.btnCalendar)
        //timePickerManager의 리스너는 특정 버튼의 text를 변경하므로 binding객체를 생성한 이후에 객체 생성
        timePickerManager = TimePickerManager(requireContext(), binding.btnTime)

        createAdapter(
            binding.departureSi,
            spinnerDataManager.findSiDatas(),
            districtType = "si",
            isDeparture = true
        )
        createAdapter(
            binding.destinationSi,
            spinnerDataManager.findSiDatas(),
            districtType = "si",
            isDeparture = false
        )
        //createSiAdapter(spinnerDataManager.findSiDatas())
        return binding.root
    }//onCreateView




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCalendar.setOnClickListener {
            val datePicker = datePickerManager.getDatePicker()
            datePicker!!.show(parentFragmentManager, datePickerManager.getDatePickerListener())
        }

        binding.btnTime.setOnClickListener {
            val timePicker = timePickerManager.getTimePicker()
            timePicker!!.show(parentFragmentManager, timePickerManager.getTimePickerListener())
        }

    }


    //FragmentPostPassengerFormBinding
//======================================================================================================
private fun createAdapter(spinner : Spinner ,items: List<String>, districtType:String, isDeparture : Boolean){
    val adapter : ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, items)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    spinner.adapter = adapter
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {}
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            if(isDeparture==true){
                when(districtType){
                    "si"->{
                        departure_si = items.get(p2)
                        createAdapter(binding.departureGu, spinnerDataManager.findGuDatas(departure_si!!), "gu", true)
                    }
                    "gu"->{
                        departure_gu = items.get(p2)
                        createAdapter(binding.departureDong, spinnerDataManager.findDongDatas(selected_si = departure_si!!, selected_gu = departure_gu!!), "dong", true)
                    }
                    "dong"->{
                        departure_dong = items.get(p2)
                        binding.textDeparture.text = departure_si+" "+departure_gu+" "+departure_dong
                    }

                }
            }else{  //destination
                when(districtType){
                    "si"->{
                        destination_si = items.get(p2)
                        createAdapter(binding.destinationGu, spinnerDataManager.findGuDatas(destination_si!!), "gu", false)
                    }
                    "gu"->{
                        destination_gu = items.get(p2)
                        createAdapter(binding.destinationDong, spinnerDataManager.findDongDatas(selected_si = destination_si!!, selected_gu = destination_gu!!), "dong", false)
                    }
                    "dong"->{
                        destination_dong = items.get(p2)
                        binding.textDestination.text = destination_si+" "+destination_gu+" "+destination_dong
                    }

                }
            }
        }
    }//listener
}//createGuAdatper


//======================================================================================================


}