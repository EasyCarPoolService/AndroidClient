package com.example.easycarpoolapp.fragment.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentCalendarHomeBinding

class CalendarHomeFragment : Fragment() {

    private lateinit var binding : FragmentCalendarHomeBinding

    companion object{
        public fun getInstance() : CalendarHomeFragment {
            return CalendarHomeFragment()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar_home, container, false)



        return binding.root
    }//onCreateView()



}