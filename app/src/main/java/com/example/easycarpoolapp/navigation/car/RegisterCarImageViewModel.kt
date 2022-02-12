package com.example.easycarpoolapp.navigation.car

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.easycarpoolapp.navigation.NavigationRepository


class RegisterCarImageViewModel : ViewModel() {

    private val repository : NavigationRepository? = NavigationRepository.getInstance()
    public var carNumber : String? = null
    public var manufacturer : String? = null
    public var model : String? = null


    public fun authenticateDriver(bitmapId : Bitmap, bitmapCar : Bitmap){
        repository?.authenticateDriver(bitmapId = bitmapId, bitmapCar = bitmapCar, carNumber = carNumber!!, manufacturer = manufacturer!!, model = model!!)
    }//authenticateDriver

}