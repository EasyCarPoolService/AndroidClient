package com.example.easycarpoolapp.navigation.progress

import android.media.Rating
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentProgressDetailBinding

class ProgressDetailFragment : Fragment() {

    companion object{
        public fun getInstance() : ProgressDetailFragment = ProgressDetailFragment()
    }


    private lateinit var binding : FragmentProgressDetailBinding
    private val viewModel : ProgressDetailViewModel by lazy {
        ViewModelProvider(this).get(ProgressDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_progress_detail, container, false)
        setRatingBar()  //ratingbar listener 추가

        return binding.root
    }   //onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    } // onViewCreated

    private fun setRatingBar(){
        binding.ratingBarRate.setOnRatingBarChangeListener { ratingBar, fl, b ->
            /*ratingbar_indicator.setRating(rating);
            ratingbar_small.setRating(rating);*/
            ratingBar.apply {
                rating = fl
            }
            binding.textRateRating.text = fl.toString() //Rating TextView 상태 변경
        }

    }//setRatingBar()






}