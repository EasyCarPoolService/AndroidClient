package com.example.easycarpoolapp.auth.join

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.auth.dto.JoinDto
import com.example.easycarpoolapp.databinding.FragmentJoinFormBinding

@RequiresApi(Build.VERSION_CODES.N)
class JoinFormFragment private constructor(): Fragment() {

    companion object{
        public fun getInstance(phoneNumber : String) : JoinFormFragment{
            val args = Bundle().apply{
                putSerializable("phoneNumber", phoneNumber)
            }
            return JoinFormFragment().apply {
                arguments = args
            }
        }
    }

    private lateinit var binding : FragmentJoinFormBinding
    private val viewModel : JoinFormViewModel by lazy {
        ViewModelProvider(this).get(JoinFormViewModel::class.java)
    }
    private var gender :String = "none"
    private var flags = HashMap<String, Boolean>().apply {
        put("name", false)
        put("nickname", false)
        put("email", false)
        put("gender", false)
        put("birth", false)
        put("password1", false)
        put("password2", false)
    }


    private val radioGroupListener = object : RadioGroup.OnCheckedChangeListener{

        override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
            when(p1){
                binding.radioMale.id ->{
                    gender = "male"
                    flags.replace("gender", true)
                    checkFlags()
                }
                binding.radioFemale.id ->{
                    gender = "female"
                    flags.replace("gender", true)
                    checkFlags()
                }
            }
        }
    }//radioGroupListener



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //이전 프래그먼트에서 인증 완료한 사용자의 휴대전화 번호 -> ViewModel에 저장
        viewModel.phoneNumber = arguments?.getString("phoneNumber")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_join_form, container, false)
        //radioGroup
        binding.radioGroup.setOnCheckedChangeListener(radioGroupListener)

        //회원가입 버튼 클릭
        binding.btnJoin.setOnClickListener {

            if(binding.editPassword1.text.toString() != binding.editPassword2.text.toString() ||
                binding.editPassword1.text.length < 6){
                Toast.makeText(requireContext(), "비밀번호를 다시 확인해 주세요.", Toast.LENGTH_SHORT).show()
            }else{

                //check  -> joinDto 에 driverAuthentication 추가
                viewModel.join(JoinDto(
                    name = binding.editName.text.toString(),
                    email = binding.editEmail.text.toString(),
                    nickname = binding.editNickname.text.toString(),
                    password = binding.editPassword1.text.toString(),
                    birth = binding.editBirth.text.toString(),
                    gender = gender,
                    driverAuthentication = false    // 최초 회원가입시 아직 인증하지 않았으므로 false
                ))

            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editBirth.addTextChangedListener(textWathcer("birth"))
        binding.editEmail.addTextChangedListener(textWathcer("email"))
        binding.editName.addTextChangedListener(textWathcer("name"))
        binding.editNickname.addTextChangedListener(textWathcer("nickname"))
        binding.editPassword1.addTextChangedListener(textWathcer("password1"))
        binding.editPassword2.addTextChangedListener(textWathcer("password2"))
    }

    private fun checkFlags(){
        var values = flags.values

        for(i in values){
            Log.e("HASH", i.toString())
        }


        for(i in values){
            if (i ==false ){
                binding.btnJoin.setEnabled(false)
                binding.btnJoin.setClickable(false)
                binding.btnJoin.setBackgroundResource(R.drawable.btn_radius_gray)
                return
            }
        }

        binding.btnJoin.setEnabled(true)
        binding.btnJoin.setClickable(true)
        binding.btnJoin.setBackgroundResource(R.drawable.btn_radius_main)
    }



    private inner class textWathcer(val item : String) : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(p0?.length == 0){
                flags.replace(item, false)
                checkFlags()
            }else{
                flags.replace(item, true)
                checkFlags()
            }
        }
    }

}