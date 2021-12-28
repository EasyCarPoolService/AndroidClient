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
        datePicker = RellDatePicker.Builder(context)
            .setDate(Calendar.getInstance())
            .setMinDate(Calendar.getInstance().timeInMillis)
            .create()
    }

    public fun getDatePicker(): RellDatePicker{
        return datePicker
    }

    public fun getListener(): RellDatePicker.OnDatePickListener {
        return datePickerListener
    }


}