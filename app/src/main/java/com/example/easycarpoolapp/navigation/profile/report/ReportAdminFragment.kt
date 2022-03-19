package com.example.easycarpoolapp.navigation.profile.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
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

        binding.btnReport.setOnClickListener {
            viewModel.reportAdmin(report_title = binding.editTitle.text.toString(), report_content = binding.editContent.text.toString())
        }
        return binding.root
    }//onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.transaction_flag.observe(viewLifecycleOwner, Observer {
            if(it.equals("success")) terminateFragment()
            Toast.makeText(requireContext(), "관리자에게 문의내용을 전송하였습니다.", Toast.LENGTH_SHORT).show()
        })
    }//onViewCreated()


    private fun terminateFragment(){
        val fragmentManager : FragmentManager = activity?.supportFragmentManager!!
        fragmentManager.beginTransaction().remove(this).commit()
        fragmentManager.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        NavigationRepository.onDestroy()
    }



}