package com.example.easycarpoolapp.navigation.car

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentRegisterCarImageBinding
import com.example.easycarpoolapp.navigation.NavigationRepository
import com.example.easycarpoolapp.utils.ImageFileManager


class RegisterCarImageFragment : Fragment() {

    companion object{
        public fun getInstance(carNumber: String, manufacturer: String, model: String): RegisterCarImageFragment{
            //check bundle
            val args = Bundle().apply {
                putSerializable("carNumber", carNumber)
                putSerializable("manufacturer", carNumber)
                putSerializable("model", carNumber)
            }

            return RegisterCarImageFragment().apply {
                arguments = args
            }
        }
    }

    private val OPEN_GALLERY = 1
    private lateinit var binding : FragmentRegisterCarImageBinding
    private var imageFlag = false
    private var bitmapId : Bitmap? = null
    private var bitmapCar : Bitmap? = null


    private val viewModel : RegisterCarImageViewModel by lazy {
        ViewModelProvider(this).get(RegisterCarImageViewModel::class.java)
    }


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
                                bitmapCar = bitmap
                            }else{  // false -> id
                                binding.btnId.setImageBitmap(bitmap)
                                bitmapId = bitmap
                            }
                        } else {
                            val source = ImageDecoder.createSource(requireContext().contentResolver, currentImageUri)
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            if(imageFlag){  //true -> car image
                                binding.btnCarImage.setImageBitmap(bitmap)
                                bitmapCar = bitmap
                            }else{  // false -> id
                                binding.btnId.setImageBitmap(bitmap)
                                bitmapId = bitmap
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


    override fun onAttach(context: Context) {
        super.onAttach(context)
        //repositry 초기화
        NavigationRepository.init(context)
    } //onAttach

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.carNumber = arguments?.getString("carNumber")
        viewModel.manufacturer = arguments?.getString("manufacturer")
        viewModel.model = arguments?.getString("model")
    } // onCreate


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

        binding.btnRegister.setOnClickListener {
            val fileManager = ImageFileManager(requireContext())
            //check
            bitmapId?.let { it1 -> bitmapCar?.let { it2 -> viewModel.authenticateDriver(it1, it2) } }

        }

        return binding.root
    }//onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.transactionFlag.observe(viewLifecycleOwner, Observer {
            if(it.equals("success")){
                Toast.makeText(requireContext(), "요청 완료!", Toast.LENGTH_SHORT).show()
                terminateFragment()
            }
        })
    }

    private val REQUEST_CODE = 100

    private fun terminateFragment(){
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commit()
    }   //terminateFragment()

    private fun getImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            "image/*"
        )
        filterActivityLauncher.launch(intent)
    }//getImageFromGallery()

    override fun onDetach() {
        super.onDetach()
        NavigationRepository.onDestroy()
    } // onDetach

}