package com.example.easycarpoolapp.navigation.profile.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentReportAdminBinding
import com.example.easycarpoolapp.navigation.NavigationRepository

class ReportAdminFragment : Fragment() {

    companion object{
        public fun getInstance() : ReportAdminFragment = ReportAdminFragment()
    }

    private lateinit var binding : FragmentReportAdminBinding
    private val viewModel : ReportAdminViewModel by lazy {
        ViewModelProvider(this).get(ReportAdminViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationRepository.init(requireContext())

    }//onCreate()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_admin, container, false)


        return binding.root
    }//onCreateView()


    override fun onDestroy() {
        super.onDestroy()
        NavigationRepository.onDestroy()
    }



}