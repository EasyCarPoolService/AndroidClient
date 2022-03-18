package com.example.easycarpoolapp.navigation.profile.report

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentReportHomeBinding


/*
관리자에게 문의와 사용자 신고로 분기

각각의 버튼 클릭 -> Callback method로 MainActivity에서 Fragment Create

 */

class ReportHomeFragment : Fragment() {

    interface Callbacks{
        public fun onReportSelected()
        public fun onAccuseSelected()
    }


    companion object{
        public fun getInstance() : ReportHomeFragment = ReportHomeFragment()
    }//companion object

    private lateinit var binding : FragmentReportHomeBinding
    private var callbacks : Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }//onAttach()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_home, container, false)

        binding.btnReport.setOnClickListener {
            callbacks?.onReportSelected()
        }   //관리자에게 문의하기 (Callback -> create Fragment)

        binding.btnAccuse.setOnClickListener {
            callbacks?.onAccuseSelected()
        }   //사용자 신고하기 (Callback -> create Fragment)

        return binding.root
    }//onCreateView()

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }//onDetach()

}