package com.example.easycarpoolapp.fragment.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentLocationHomeBinding
import com.example.easycarpoolapp.fragment.post.PostHomeFragment


class LocationHomeFragment : Fragment() {

    companion object{
        public fun getInstance() : LocationHomeFragment{
            return LocationHomeFragment()
        }//getInstance()
    }

    private lateinit var binding : FragmentLocationHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_location_home, container, false)



        return binding.root
    }//onCreateView()

}