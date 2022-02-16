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
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentCalendarHomeBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar_home, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = CalendarAdapter(createTestData())

        binding.calendarView.setSelectedDate(CalendarDay.today())

        //calendar를 꾸미기위한 Decorator추가
        binding.calendarView.addDecorator(TodayDecorator())


        //날짜 선택시 이벤트 발생
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            getWordsFromCalendarTBL(date)
        }

        return binding.root
    }//onCreateView()

    //=============================================================================

    override fun onDestroy() {
        super.onDestroy()
        CalendarRepository.onDestroy()
    }//onDestroy
    //=============================================================================

    fun getWordsFromCalendarTBL(date : CalendarDay){
        fun String.convertSingleToDoubleDigit(): String = if (this.length < 2) "0$this" else this
        var year = date.year.toString().convertSingleToDoubleDigit()
        var month = (date.month+1).toString().convertSingleToDoubleDigit()
        var day = date.day.toString().convertSingleToDoubleDigit()
        var selectedDate = year+"-"+month+"-"+day

        Toast.makeText(requireContext(), selectedDate.toString(), Toast.LENGTH_SHORT).show()
        Log.e("selectedDate", selectedDate)
        //calendarViewModel.getWordsFromCalendarTBL(selectedDate)
    }//getWordsFromCalendarTBL



    //=============================================================================
    private fun createTestData(): ArrayList<String> {
        val temp = ArrayList<String>()
        for(i in 0..20){
            temp.add(i.toString())
        }

        return temp
    }

    //=============================================================================

    inner class CalendarHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val textView : TextView = itemView.findViewById(R.id.item_text)

        public fun bind(items : String){
            textView.text = items
        }


    } //CalendarHolder

    //=============================================================================

    inner class CalendarAdapter(val items : ArrayList<String>) : RecyclerView.Adapter<CalendarHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarHolder {
            val view = layoutInflater.inflate(R.layout.item_calendar_layout, parent, false)
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


    inner class TodayDecorator: DayViewDecorator {
        private var date = CalendarDay.today()

        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return day?.equals(date)!!
        }

        override fun decorate(view: DayViewFacade?) {
            view?.addSpan(StyleSpan(Typeface.BOLD))
            view?.addSpan(RelativeSizeSpan(1.7f))
            view?.addSpan(ForegroundColorSpan(Color.parseColor("#ffffff")))
        }
    }   // TodayDecorator

    //=============================================================================





}