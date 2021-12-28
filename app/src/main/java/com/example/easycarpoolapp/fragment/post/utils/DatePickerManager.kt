package com.example.easycarpoolapp.fragment.post.utils

import android.content.Context
import android.widget.Button
import com.datepicker.rell.datapickerlib.RellDatePicker
import java.text.SimpleDateFormat
import java.util.*

class DatePickerManager(val context : Context, val button : Button) {

    private lateinit var datePicker : RellDatePicker

    init{
        createDatePicker()
    }



    private var datePickerListener = object : RellDatePicker.OnDatePickListener{
        override fun onDatePick(calendar: Calendar?) {
            val format = SimpleDateFormat("yyyy-MM-dd")
            button.setText(format.format(calendar!!.getTime()))
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