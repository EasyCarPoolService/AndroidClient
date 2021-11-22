package com.example.easycarpoolapp.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.example.easycarpoolapp.MainActivity
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    private lateinit var viewPager : ViewPager
    private lateinit var InfoViewPagerAdapter: InfoViewPagerAdapter
    private lateinit var items : ArrayList<String>
    private lateinit var binding : ActivityInfoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_info)
        val imageList = loadImage()

        binding.infoViewPager.adapter = InfoViewPagerAdapter(this, imageList)
        setPageListener()

        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }


    private fun loadImage() : ArrayList<Int> {
        val imageList = ArrayList<Int>()

        imageList.add(R.drawable.info_delete_image)
        imageList.add(R.drawable.info_delete_image)
        imageList.add(R.drawable.info_delete_image)

        return imageList
    }

    private fun setPageListener(){
        binding.infoViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageSelected(position: Int) {
                binding.pageIndicator01.setImageResource(R.drawable.shape_circle_gray)
                binding.pageIndicator02.setImageResource(R.drawable.shape_circle_gray)
                binding.pageIndicator03.setImageResource(R.drawable.shape_circle_gray)
                when(position){
                    0 -> {
                        binding.pageIndicator01.setImageResource(R.drawable.shape_circle_maincolor)
                        binding.btnStart.visibility = View.GONE
                    }
                    1 -> {
                        binding.pageIndicator02.setImageResource(R.drawable.shape_circle_maincolor)
                        binding.btnStart.visibility = View.GONE
                    }
                    2 -> {
                        binding.pageIndicator03.setImageResource(R.drawable.shape_circle_maincolor)
                        binding.btnStart.visibility = View.VISIBLE
                    }
                }
            }
        })
    }



}