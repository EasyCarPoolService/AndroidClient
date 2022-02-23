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



import android.app.Activity

import android.graphics.drawable.Drawable
import android.widget.LinearLayout
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentCalendarHomeBinding
import com.example.easycarpoolapp.fragment.chat.dto.ReservedPostDto
import com.example.easycarpoolapp.fragment.post.dto.PostDto
import com.prolificinteractive.materialcalendarview.CalendarDay


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
        binding.recyclerView.adapter = CalendarAdapter(ArrayList<ReservedPostDto>())

        return binding.root
    }//onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPostData() //reserved Post  조회하기

        viewModel.postItems.observe(viewLifecycleOwner, Observer {
            binding.calendarView.addDecorator(CalendarUtil.getTodayDecorator(requireActivity()))
            binding.calendarView.addDecorator(CalendarUtil.getEventDecorator(requireActivity(), it))
            //reservedPost기반 recyclerview set하기
        })

        //날짜 선택시 이벤트 발생
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            val posts = getPostByDate(date)
            setRecyclerView(posts)
        }

    }//onViewCreated
    //=============================================================================

    override fun onDestroy() {
        super.onDestroy()
        CalendarRepository.onDestroy()
    }//onDestroy
    //=============================================================================
    private fun setRecyclerView(items : ArrayList<ReservedPostDto>){
        binding.recyclerView.adapter = CalendarAdapter(items)
    }// setRecyclerView()



    //=============================================================================

    private fun getPostByDate(date : CalendarDay): ArrayList<ReservedPostDto> {
        fun String.convertSingleToDoubleDigit(): String = if (this.length < 2) "0$this" else this
        var year = date.year.toString().convertSingleToDoubleDigit()
        var month = (date.month+1).toString().convertSingleToDoubleDigit()
        var day = date.day.toString().convertSingleToDoubleDigit()
        var selectedDate = year+"."+month+"."+day   //선택된 날짜

        Log.e("selectedDate", selectedDate)

        val datePosts = ArrayList<ReservedPostDto>()
        val AllPosts = viewModel.postItems.value!!

        for(i in AllPosts){
            if(i.date.equals(selectedDate)){
                datePosts.add(i)
            }
        }
        return datePosts
    }

    //=============================================================================

    inner class CalendarHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val itemLayout : LinearLayout = itemView.findViewById(R.id.item_calendar_layout)
        val driver : TextView = itemView.findViewById(R.id.item_calendar_driver)
        val passenger : TextView = itemView.findViewById(R.id.item_calendar_passenger)
        val date : TextView = itemView.findViewById(R.id.item_calendar_date)
        val time : TextView = itemView.findViewById(R.id.item_calendar_time)



        public fun bind(item : ReservedPostDto){
            driver.text = driver.text.toString()+item.driver
            passenger.text = passenger.text.toString()+item.passenger
            date.text = date.text.toString()+item.date
            time.text = time.text.toString()+item.time
        }//bind()

        init{
            itemLayout.setOnClickListener {
                Toast.makeText(requireContext(), "item layout clicked", Toast.LENGTH_SHORT).show()
            }

        }//init

    } //CalendarHolder

    //=============================================================================

    inner class CalendarAdapter(val items : ArrayList<ReservedPostDto>) : RecyclerView.Adapter<CalendarHolder>(){
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