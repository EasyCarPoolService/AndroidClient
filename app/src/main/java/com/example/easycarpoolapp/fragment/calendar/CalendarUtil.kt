package com.example.easycarpoolapp.fragment.calendar

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.fragment.post.dto.PostDto
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

/*
리스너 , 이벤트 데코레이터 등 생성 코드 작성
 */


class CalendarUtil {

    companion object{

        val TAG  : String = "CalendarUtil"

        public fun getTodayDecorator(context: Activity) : TodayDecorator {
            return TodayDecorator(context)
        }//getTodayDecorator()

        public fun getEventDecorator(context: Activity, posts : ArrayList<PostDto>) :EventDecorator{
            return EventDecorator(createEventCalendarDay(posts), context)
        }//getEventDecorator()

        private fun createEventCalendarDay(posts: ArrayList<PostDto>): HashSet<CalendarDay> {

            val eventDays : HashSet<CalendarDay> = HashSet()
            for (post in posts){
                Log.e(TAG, post.departureDate)
                val dateArr = post.departureDate.split(".")
                val year : Int = dateArr.get(0).toInt()
                val month : Int = dateArr.get(1).toInt()-1
                val day : Int = dateArr.get(2).toInt()
                eventDays.add(CalendarDay.from(year, month, day))

            }

            return eventDays
        }//createEventCalendarDay()


    }//companion object

    //====================================================================================


    class TodayDecorator(context : Activity): DayViewDecorator {
        private var date = CalendarDay.today()
        private val drawable: Drawable

        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return day?.equals(date)!!
        }

        override fun decorate(view: DayViewFacade?) {
            /*view?.addSpan(StyleSpan(Typeface.BOLD))
            view?.addSpan(RelativeSizeSpan(1.7f))
            view?.addSpan(ForegroundColorSpan(Color.parseColor("#ffffff")))*/
            view?.setSelectionDrawable(drawable)
            //view?.addSpan(DotSpan(5f, Color.RED)); // 날자밑에 점
        }

        init {
            drawable = context.resources.getDrawable(R.drawable.icon_calendar_maincolor)
        }

    }   // TodayDecorator


    class EventDecorator(val dates: Collection<CalendarDay?>?, context: Activity) :
        DayViewDecorator {

        private val drawable: Drawable
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return dates!!.contains(day)
        }

        override fun decorate(view: DayViewFacade) {
            //view.setSelectionDrawable(drawable)
            view.addSpan(DotSpan(5f, Color.RED)); // 날자밑에 점
        }

        init {
            drawable = context.resources.getDrawable(R.drawable.icon_calendar_maincolor)
        }


    }// EventDecorator

}