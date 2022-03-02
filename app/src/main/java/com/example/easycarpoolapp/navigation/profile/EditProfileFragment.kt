package com.example.easycarpoolapp.navigation.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.R




class EditProfileFragment : Fragment() {

    companion object{
        public fun getInstance() : EditProfileFragment = EditProfileFragment()
    }

    private lateinit var binding : com.example.easycarpoolapp.databinding.FragmentEditProfileBinding
    private val viewModel : EditProfileViewModel by lazy {
        ViewModelProvider(this).get(EditProfileViewModel::class.java)
    }

    private val radioGroupListener = object : RadioGroup.OnCheckedChangeListener{

        override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
            when(p1){
                binding.radioMale.id ->{
                    viewModel.gender = "male"
                }
                binding.radioFemale.id ->{
                    viewModel.gender = "female"
                }
            }
        }//onCheckedChanged()
    }//radioGroupListener

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
                             viewModel.profile_image = bitmap
                        } else {
                            val source = ImageDecoder.createSource(requireContext().contentResolver, currentImageUri)
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            binding.btnProfile.setImageBitmap(bitmap)
                            viewModel.profile_image = bitmap
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        setUIWithLocalData()
        setUIWithServerData()

        binding.btnEdit.setOnClickListener {
            viewModel.nickname = binding.editNickname.text.toString()
            viewModel.editProfile() //서버로 수정 내용 전송
        }

        return binding.root
    }//onCreateView


    private fun setUIWithLocalData(){

        viewModel.nickname = LocalUserData.getNickname().toString()
        binding.editNickname.setText(LocalUserData.getNickname().toString())


        if(LocalUserData.getGender().equals("male")){   //성별 남자일경우
            binding.radioMale.setChecked(true)
            binding.radioFemale.setChecked(false)
            viewModel.gender = "male"
        }else{  //여자일 경우
            binding.radioMale.setChecked(false)
            binding.radioFemale.setChecked(true)
            viewModel.gender = "female"
        }
        binding.radioGroup.setOnCheckedChangeListener(radioGroupListener)

    }// 기기에 저장되어 있는 정보 로드하여 UI에 띄우기

    private fun setUIWithServerData(){
        setImageBtnProfile() //프로필 이미지 설정

    }   //서버에 저장되어 있는 정보 조회하여 UI에 띄우기

    private fun setImageBtnProfile() {

        Glide.with(this)
            .asBitmap()
            .load("http://"+ NetworkConfig.getIP()+":8080/api/image/profile?email="+LocalUserData.getEmail())
            .into(object : SimpleTarget<Bitmap?>(){
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    binding.btnProfile.setImageBitmap(resource)
                    viewModel.profile_image = resource
                }

            })



        //.into(binding.btnProfile)




        binding.btnProfile.setOnClickListener {
            getImageFromGallery()
        }

    }//프로필 이미지 설정

    private fun getImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        )
        filterActivityLauncher.launch(intent)

    }//getImageFromGallery()



}