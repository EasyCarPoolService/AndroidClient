package com.example.easycarpoolapp.fragment.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.easycarpoolapp.R

class PostPassengerFormFragment : Fragment() {

    companion object{
        public fun getInstance() : PostPassengerFormFragment{
            return PostPassengerFormFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_post_passenger_form, container, false)
    }

}