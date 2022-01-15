package com.example.easycarpoolapp.navigation.car

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentRegisterCarImageBinding


class RegisterCarImageFragment : Fragment() {

    companion object{
        public fun getInstance() : RegisterCarImageFragment{
            return RegisterCarImageFragment()
        }
    }

    private val OPEN_GALLERY = 1
    private lateinit var binding : FragmentRegisterCarImageBinding
    private var imageFlag = false
    private val filterActivityLauncher : ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK && it.data !=null) {
                var currentImageUri = it.data?.data
                try {
                    currentImageUri?.let {
                        if(Build.VERSION.SDK_INT < 28) {
                            val bitmap = MediaStore.Images.Media.getBitmap(
                                requireContext().contentResolver,
                                currentImageUri
                            )
                            if(imageFlag){  //true -> car image
                                binding.btnCarImage.setImageBitmap(bitmap)
                            }else{  // false -> id
                                binding.btnId.setImageBitmap(bitmap)
                            }
                        } else {
                            val source = ImageDecoder.createSource(requireContext().contentResolver, currentImageUri)
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            if(imageFlag){  //true -> car image
                                binding.btnCarImage.setImageBitmap(bitmap)
                            }else{  // false -> id
                                binding.btnId.setImageBitmap(bitmap)
                            }
                        }
                    }

                }catch(e:Exception) {
                    e.printStackTrace()
                }
            } else if(it.resultCode == RESULT_CANCELED){
                Toast.makeText(requireContext(), "사진 선택 취소", Toast.LENGTH_LONG).show()
            }else{
                Log.d("ActivityResult","something wrong")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_car_image, container, false)

        binding.btnCarImage.setOnClickListener {
            imageFlag = true    //callback에서 car image에 사진을 set하도록 설정
            getImageFromGallery()
        }

        binding.btnId.setOnClickListener {
            imageFlag = false   //callback에서 신분증에 사진을 set하도록 설정
            getImageFromGallery()
        }

        return binding.root
    }

    private val REQUEST_CODE = 100

    private fun getImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        )
        filterActivityLauncher.launch(intent)
    }

}