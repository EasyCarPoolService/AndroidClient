package com.example.easycarpoolapp.fragment.post

import android.os.Bundle
import android.util.Log
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

class PostPassengerFormFragment : Fragment() {

    companion object{
        public fun getInstance() : PostPassengerFormFragment{
            return PostPassengerFormFragment()
        }
    }

    private lateinit var binding :FragmentPostPassengerFormBinding
    private lateinit var spinnerDataManager: SpinnerDataManager

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_passenger_form, container, false)

        createAdapter(binding.departureSi, spinnerDataManager.findSiDatas(), districtType = "si", isDeparture = true)
        createAdapter(binding.destinationSi, spinnerDataManager.findSiDatas(), districtType = "si", isDeparture = false)
        //createSiAdapter(spinnerDataManager.findSiDatas())
        return binding.root
    }
//======================================================================================================
    /*private fun createSiAdapter(siDatas : List<String>){

        for(row in siDatas){
            Log.e("siData", row)
        }

        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, siDatas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.departureSi.adapter = adapter
        binding.departureSi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                departure_si = siDatas.get(p2)
                createGuAdapter(spinnerDataManager.findGuDatas(departure_si!!))
            }
        }

    }// createCityAdapter

//======================================================================================================
    private fun createGuAdapter(guDatas: List<String>) {
        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, guDatas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.departureGu.adapter = adapter
        binding.departureGu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                departure_gu = guDatas.get(p2)
                createDongAdapter(spinnerDataManager.findDong(departure_si!!, departure_gu!!))
            }
        }
    }//createGuAdatper
//======================================================================================================
    private fun createDongAdapter(dongDatas: List<String>) {
        val adapter : ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, dongDatas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.departureDong.adapter = adapter
        binding.departureDong.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                departure_dong = dongDatas.get(p2)
                binding.textDeparture.text = departure_si+departure_gu+departure_dong
            }
        }
    }//createGuAdatper
*/
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
    }
}//createGuAdatper
//======================================================================================================


}