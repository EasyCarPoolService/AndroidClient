package com.example.easycarpoolapp.fragment.post.utils

import android.content.Context
import android.widget.Button
import androidx.fragment.app.Fragment
import com.karrel.timepicker.RellTimePicker
import java.text.SimpleDateFormat
import java.util.*

class TimePickerManager private constructor(val context : Context, val button : Button, fragment : Fragment) {

    companion object{
        public fun getInstance(context : Context, button : Button, fragment : Fragment) : TimePickerManager {
            return TimePickerManager(context, button, fragment)
        }
    }

    private var callbacks : Callbacks? = null
    private lateinit var timePicker: RellTimePicker

    init{
        createTimePicker()
        callbacks = fragment as Callbacks
    }

    interface Callbacks{
        fun onTimeSelected(hourOfDay: Int, minute: Int)
    }

    private var timePickerListener = object : RellTimePicker.OnTimePickListener{

        override fun onTimePick(hourOfDay: Int, minute: Int) {
            button.setText(hourOfDay.toString()+"시 "+minute.toString()+"분")
            callbacks!!.onTimeSelected(hourOfDay, minute)

        }
    }//timePickerListener

    public fun getCurrentTime(): List<String> {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val format = SimpleDateFormat("HH:MM:aa")
        val time = format.format(date).split(':')
        return time
    } // getCurrentTime


    private fun createTimePicker() {
        val currentTime = getCurrentTime()
        timePicker = RellTimePicker.Builder(context)
            .setTime(currentTime.get(0).toInt(), currentTime.get(1).toInt())
            .create()

        button.setText(currentTime.get(0)+"시 "+currentTime.get(1).toString()+"분")

    }//createDatePicker

    public fun getTimePicker(): RellTimePicker {
        return timePicker
    }

    public fun getTimePickerListener(): RellTimePicker.OnTimePickListener {
        return timePickerListener
    }

    public fun onDestroy(){
        callbacks = null
    }


}