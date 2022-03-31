package com.example.easycarpoolapp.auth.join

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.auth.dto.JoinDto
import com.example.easycarpoolapp.databinding.FragmentJoinFormBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

@RequiresApi(Build.VERSION_CODES.N)
class JoinFormFragment private constructor(): Fragment() {

    interface Callbacks {
        public fun onJoinTransactionSuccess()
    }


    companion object{
        public fun getInstance(phoneNumber : String) : JoinFormFragment{
            val args = Bundle().apply{
                putSerializable("phoneNumber", phoneNumber)
            }
            return JoinFormFragment().apply {
                arguments = args
            }
        }
    }// companion object

    private var callbacks : Callbacks? = null
    private lateinit var fcmToken :String
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

    private var bitmap_profile : Bitmap? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //이전 프래그먼트에서 인증 완료한 사용자의 휴대전화 번호 -> ViewModel에 저장
        viewModel.phoneNumber = arguments?.getString("phoneNumber")

        bitmap_profile = BitmapFactory.decodeResource(requireContext().resources, R.drawable.default_image)

        getToken()  //FCM Device Token 얻기

    } // onCreate()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_join_form, container, false)
        //radioGroup
        binding.radioGroup.setOnCheckedChangeListener(radioGroupListener)

        binding.btnProfile.setOnClickListener {
            getImageFromGallery()
        }

        //회원가입 버튼 클릭
        binding.btnJoin.setOnClickListener {

            if(binding.editPassword1.text.toString() != binding.editPassword2.text.toString() ||
                binding.editPassword1.text.length < 6){
                Toast.makeText(requireContext(), "비밀번호를 다시 확인해 주세요.", Toast.LENGTH_SHORT).show()
            }else{

                viewModel.join(
                    profile_image = bitmap_profile,
                    name = binding.editName.text.toString(),
                    email = binding.editEmail.text.toString(),
                    nickname = binding.editNickname.text.toString(),
                    password = binding.editPassword1.text.toString(),
                    birth = binding.editBirth.text.toString(),
                    gender = gender,
                    fcmToken = fcmToken
                )
            }
        }
        return binding.root
    }//onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editBirth.addTextChangedListener(textWathcer("birth"))
        binding.editEmail.addTextChangedListener(textWathcer("email"))
        binding.editName.addTextChangedListener(textWathcer("name"))
        binding.editNickname.addTextChangedListener(textWathcer("nickname"))
        binding.editPassword1.addTextChangedListener(textWathcer("password1"))
        binding.editPassword2.addTextChangedListener(textWathcer("password2"))


        viewModel.transactionFlag.observe(viewLifecycleOwner, Observer {
            if(it.equals(binding.editEmail.text.toString())){
                Toast.makeText(requireContext(), "가입 완료.", Toast.LENGTH_SHORT).show()
                callbacks?.onJoinTransactionSuccess()
            }
        })

    }   //onViewCreated()

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }// onDetach()

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
    }// checkFlags()


    private fun getImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        )
        filterActivityLauncher.launch(intent)
    }//getImageFromGallery()

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
    }//textWathcer

    private val filterActivityLauncher : ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK && it.data !=null) {
                var currentImageUri = it.data?.data
                try {
                    currentImageUri?.let {
                        if(Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                requireContext().contentResolver,
                                currentImageUri
                            )
                            binding.btnProfile.setImageBitmap(bitmap)
                            bitmap_profile = bitmap
                        } else {
                            val source = ImageDecoder.createSource(requireContext().contentResolver, currentImageUri)
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            binding.btnProfile.setImageBitmap(bitmap)
                            bitmap_profile = bitmap
                        }
                    }

                }catch(e:Exception) {
                    e.printStackTrace()
                }
            } else if(it.resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(requireContext(), "사진 선택 취소", Toast.LENGTH_LONG).show()
            }else{
                Log.d("ActivityResult","something wrong")
            }
        }//filterActivityLauncher


    //FCM Service를 위한 DeviceToken을 얻는 메서드
    private fun getToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            fcmToken = task.result.toString()

            Log.e("TEST!!", fcmToken)

        })
    }//getToken()

}