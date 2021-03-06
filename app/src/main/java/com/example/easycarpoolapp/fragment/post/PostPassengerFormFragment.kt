package com.example.easycarpoolapp.fragment.post

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentPostPassengerFormBinding
import com.example.easycarpoolapp.fragment.post.utils.DatePickerManager
import com.example.easycarpoolapp.fragment.post.utils.SpinnerDataManager
import com.example.easycarpoolapp.fragment.post.utils.TimePickerManager

class PostPassengerFormFragment : Fragment(), TimePickerManager.Callbacks, DatePickerManager.Callbacks {

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

    private val viewModel : PostPassengerFormViewModel by lazy {
        ViewModelProvider(this).get(PostPassengerFormViewModel::class.java)
    }

    private val memoTextWatcher : TextWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            viewModel.message = s.toString()
        }

    }//memoTextWathcer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spinnerDataManager = SpinnerDataManager.getInstance(requireContext())
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
        //datePickerManager??? ???????????? ?????? ????????? text??? ??????????????? binding????????? ????????? ????????? ?????? ??????
        datePickerManager = DatePickerManager.getInstance(requireContext(), binding.btnCalendar, this)
        //timePickerManager??? ???????????? ?????? ????????? text??? ??????????????? binding????????? ????????? ????????? ?????? ??????
        timePickerManager = TimePickerManager.getInstance(requireContext(), binding.btnTime, this)

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

    //======================================================================================================

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

        binding.editMemo.addTextChangedListener(memoTextWatcher)
        setToggleButton()

        // ?????? ?????? ??????
        binding.btnRegister.setOnClickListener {
            if(isRegisterAvailable()){  //???????????? ?????? ????????? ????????? ?????? viewModel??? ?????? ??????
                viewModel.register()
            }
        }

        binding.btnTerminateFragment.setOnClickListener {
            terminateFragment()
        }   //????????? ????????? x?????? ????????? ?????? ??????????????? ??????

        viewModel.transactionFlag.observe(viewLifecycleOwner, Observer {
            if(it.equals("success")){
                Toast.makeText(requireContext(), "?????? ??????.", Toast.LENGTH_SHORT).show()
                terminateFragment()
            }

        })


    }//onViewCreated

    //======================================================================================================
    private fun terminateFragment(){
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commit()
    }   //terminateFragmet()


    private fun setToggleButton(){

        binding.toggleGroup.setOnSelectListener {

            if (it.isSelected){ // ?????? ???????????? ?????? ??????
                viewModel.gift.add(it.text.toString())
            }else{
                viewModel.gift.remove(it.text.toString())
            }

        }
    }//setToggleButton
//======================================================================================================
    private fun isRegisterAvailable() : Boolean{
        if(viewModel.departure == null){
            Toast.makeText(requireContext(), "???????????? ????????? ?????????.", Toast.LENGTH_SHORT).show()
            return false

        }else if(viewModel.destination ==null){
            Toast.makeText(requireContext(), "???????????? ????????? ?????????.", Toast.LENGTH_SHORT).show()
            return false
        }else if(viewModel.departureDate == null){
            Toast.makeText(requireContext(), "?????? ????????? ????????? ?????????.", Toast.LENGTH_SHORT).show()
            return false
        }else if (viewModel.departuretime == null) {
            Toast.makeText(requireContext(), "?????? ????????? ????????? ?????????.", Toast.LENGTH_SHORT).show()
            return false
        }else if (viewModel.gift.size==0) {
            Toast.makeText(requireContext(), "??????????????? ??? ????????? ????????? ?????????.", Toast.LENGTH_SHORT).show()
            return false
        }else{
            return true
        }
    }//isRegisterAvailable()



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
                        viewModel.departure = departure_si+" "+departure_gu+" "+departure_dong
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
                        viewModel.destination = destination_si+" "+destination_gu+" "+destination_dong
                    }

                }
            }
        }
    }//listener
}//createGuAdatper

    override fun onDateSelected(date: String) {
        viewModel.departureDate = date
    }

    override fun onTimeSelected(hourOfDay: Int, minute: Int) {
        viewModel.departuretime = hourOfDay.toString()+":"+minute.toString()
    }

//======================================================================================================


}