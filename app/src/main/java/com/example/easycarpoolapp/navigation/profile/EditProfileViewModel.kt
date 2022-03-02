package com.example.easycarpoolapp.navigation.profile

import android.graphics.Bitmap
import android.provider.ContactsContract
import androidx.lifecycle.ViewModel

class EditProfileViewModel : ViewModel() {

    public lateinit var gender : String
    public lateinit var nickname : String
    public var introduce_message : String = ""
    public lateinit var profile_image : Bitmap


}