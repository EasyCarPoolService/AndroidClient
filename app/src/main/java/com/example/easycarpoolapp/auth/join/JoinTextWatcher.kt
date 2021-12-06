package com.example.easycarpoolapp.auth.join

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.example.easycarpoolapp.R


@SuppressLint("ResourceAsColor")
class JoinTextWatcher (val btn : Button): TextWatcher {



    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if(p0?.length == 0){
            btn.setBackgroundColor(R.color.gray_color)
        }else{
            btn.setBackgroundColor(R.color.main_color)
        }


    }

    override fun afterTextChanged(p0: Editable?) {

    }

}