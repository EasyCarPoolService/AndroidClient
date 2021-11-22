package com.example.easycarpoolapp.intro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.InfoPageLayoutBinding

class InfoViewPagerAdapter(val context: Context, val items : ArrayList<Int>) : PagerAdapter(){
    override fun getCount(): Int {
        return items.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return(view == `object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.info_page_layout, container, false)
        val imageView = view.findViewById<ImageView>(R.id.image_page)

        imageView.setImageResource(items.get(position))

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}