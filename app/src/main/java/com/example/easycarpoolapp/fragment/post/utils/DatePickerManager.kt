package com.example.easycarpoolapp.fragment.post.utils

import android.content.Context
import android.widget.Button
import androidx.fragment.app.Fragment
import com.datepicker.rell.datapickerlib.RellDatePicker
import java.text.SimpleDateFormat
import java.util.*

/*
! 추후 코드 리팩토링 시 utils package의 class가 hosting fragment의 viewmodel의 존재를 알고 있는 것에 대한 검토 필요 !


 */

class DatePickerManager private constructor(val context : Context, val button : Button, fragment : Fragment) {

    companion object{
        public fun getInstance(context : Context, button : Button, fragment : Fragment) : DatePickerManager {
            return DatePickerManager(context, button, fragment)
        }
    }

    private var callbacks : Callbacks? = null
    private lateinit var datePicker : RellDatePicker

    init{
        createDatePicker()
        callbacks = fragment as Callbacks?
    }

    interface Callbacks{
        fun onDateSelected(date : String)
    }

    private var datePickerListener = object : RellDatePicker.OnDatePickListener{
        override fun onDatePick(calendar: Calendar?) {
            val format = SimpleDateFormat("yyyy.MM.dd")
            button.setText(format.format(calendar!!.getTime()))
            callbacks!!.onDateSelected(format.format(calendar!!.getTime()))
        }
    }

    private fun createDatePicker() {

        val calendar = Calendar.getInstance()

        datePicker = RellDatePicker.Builder(context)
            .setDate(calendar)
            .setMinDate(calendar.timeInMillis)
            .create()

        val format = SimpleDateFormat("yyyy.MM.dd")
        button.setText(format.format(calendar.timeInMillis))
    }

    public fun getDatePicker(): RellDatePicker{
        return datePicker
    }

    public fun getDatePickerListener(): RellDatePicker.OnDatePickListener {
        return datePickerListener
    }




}