package com.example.easycarpoolapp.fragment.calendar

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.easycarpoolapp.databinding.FragmentCalendarHomeBinding
import com.example.easycarpoolapp.fragment.post.dto.PostDto
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

import android.app.Activity

import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.spans.DotSpan


class CalendarHomeFragment : Fragment() {

    private lateinit var binding : FragmentCalendarHomeBinding
    private val viewModel : CalendarHomeViewModel by lazy {
        ViewModelProvider(this).get(CalendarHomeViewModel::class.java)
    }

    companion object{
        public fun getInstance() : CalendarHomeFragment {
            return CalendarHomeFragment()
        }
    }//companion object
    //=============================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CalendarRepository.init(requireContext())

    }//onCreate()
    //=============================================================================

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, com.example.easycarpoolapp.R.layout.fragment_calendar_home, container, false)

        binding.calendarView.setSelectedDate(CalendarDay.today())

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = CalendarAdapter(createTestData())

        return binding.root
    }//onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPostData()
        viewModel.postItems.observe(viewLifecycleOwner, Observer {
            binding.calendarView.addDecorator(CalendarUtil.getTodayDecorator())
            binding.calendarView.addDecorator(CalendarUtil.getEventDecorator(requireActivity(), it))
        })

        //날짜 선택시 이벤트 발생
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            val posts = getPostByDate(date)

        }

    }//onViewCreated
    //=============================================================================

    override fun onDestroy() {
        super.onDestroy()
        CalendarRepository.onDestroy()
    }//onDestroy
    //=============================================================================

    private fun getPostByDate(date : CalendarDay): ArrayList<PostDto> {
        fun String.convertSingleToDoubleDigit(): String = if (this.length < 2) "0$this" else this
        var year = date.year.toString().convertSingleToDoubleDigit()
        var month = (date.month+1).toString().convertSingleToDoubleDigit()
        var day = date.day.toString().convertSingleToDoubleDigit()
        var selectedDate = year+"."+month+"."+day   //선택된 날짜

        Log.e("selectedDate", selectedDate)

        val datePosts = ArrayList<PostDto>()
        val AllPosts = viewModel.postItems.value!!

        for(i in AllPosts){
            if(i.departureDate.equals(selectedDate)){
                datePosts.add(i)
            }
        }
        return datePosts
    }

    //=============================================================================
    private fun createTestData(): ArrayList<String> {
        val temp = ArrayList<String>()
        for(i in 0..20){
            temp.add(i.toString())
        }

        return temp
    }// createTestData()

    //=============================================================================

    inner class CalendarHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val textView : TextView = itemView.findViewById(com.example.easycarpoolapp.R.id.item_text)

        public fun bind(items : String){
            textView.text = items
        }


    } //CalendarHolder

    //=============================================================================

    inner class CalendarAdapter(val items : ArrayList<String>) : RecyclerView.Adapter<CalendarHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarHolder {
            val view = layoutInflater.inflate(com.example.easycarpoolapp.R.layout.item_calendar_layout, parent, false)
            return CalendarHolder(view)
        }

        override fun onBindViewHolder(holder: CalendarHolder, position: Int) {
            holder.bind(items.get(position))
        }

        override fun getItemCount(): Int {
            return items.size
        }

    }//calendarAdapter()


    //=============================================================================

}