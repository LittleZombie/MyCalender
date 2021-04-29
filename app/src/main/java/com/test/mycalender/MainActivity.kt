package com.test.mycalender

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendar_view.postDelayed({
            calendar_view.setEventDates(createFakeEvent())
        }, 1500)
    }

    private fun createFakeEvent(): ArrayList<Date> {
        val dateList = arrayListOf<Date>()
        dateList.add(Calendar.getInstance().time)
        dateList.add(Calendar.getInstance().apply {
            set(Calendar.DATE, 26)
        }.time)
        dateList.add(Calendar.getInstance().apply {
            set(Calendar.DATE, 25)
        }.time)
        dateList.add(Calendar.getInstance().apply {
            set(Calendar.DATE, 24)
        }.time)

        dateList.add(Calendar.getInstance().apply {
            set(Calendar.DATE, 12)
        }.time)
        dateList.add(Calendar.getInstance().apply {
            set(Calendar.DATE, 13)
        }.time)
        dateList.add(Calendar.getInstance().apply {
            set(Calendar.DATE, 14)
        }.time)
        dateList.add(Calendar.getInstance().apply {
            set(Calendar.DATE, 15)
        }.time)
        dateList.add(Calendar.getInstance().apply {
            set(Calendar.DATE, 16)
        }.time)

        return dateList
    }

}