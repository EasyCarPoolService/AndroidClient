package com.example.easycarpoolapp.navigation.profile.report

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentAccuseUserBinding
import com.example.easycarpoolapp.navigation.NavigationRepository

class AccuseUserFragment : Fragment(), AccuseDialogFragment.Callbacks {

    companion object{
        public fun getInstance() : AccuseUserFragment = AccuseUserFragment()
    }

    private val comment1 = "광고성 및 홍보성 게시글"
    private val comment2 = "욕설, 비속어 사용"
    private val comment3 = "기타"
    private val comments = arrayOf(comment1, comment2, comment3)
    private lateinit var adapter : ArrayAdapter<String>

    private lateinit var binding : FragmentAccuseUserBinding
    private val viewModel : AccuseUserViewModel by lazy {
        ViewModelProvider(this).get(AccuseUserViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NavigationRepository.init(requireContext())
        adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, comments)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_accuse_user, container, false)
        binding.spinner.adapter = adapter


        return binding.root
    } // onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.accuse_type = comments.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }// onItemSelectedListener



        viewModel.transaction_flag.observe(viewLifecycleOwner, Observer {
            if(it.equals("success")) terminateFragment()
            Toast.makeText(requireContext(), "관리자에게 사용자 신고문의를 전달하였습니다.", Toast.LENGTH_SHORT).show()
        })

        //신고 버튼 클릭
        binding.btnAccuse.setOnClickListener {
            AccuseDialogFragment(this).show(requireActivity().supportFragmentManager, "AccuseDialogFragment")
        }


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

    override fun onAccuseConfirmSelected() {
        viewModel.accuseUser(binding.editAccuseNickname.text.toString(), binding.editContent.text.toString())   //callbacks method에서 수행하도록 변경
    }   //dialog Fragment에서 확인 클릭 -> 신고 메시지 서버로 전송

}