package com.example.easycarpoolapp.navigation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentReportUserBinding

class ReportUserFragment : Fragment() {

    companion object{
        public fun getInstance() : ReportUserFragment = ReportUserFragment()
    }//companion object

    private lateinit var binding : FragmentReportUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_user, container, false)


        return binding.root
    }//onCreateView()



}