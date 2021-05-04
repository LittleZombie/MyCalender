package com.test.mycalender.item

import java.util.*

data class H2CalendarModel(
    val year: Int,
    val calendarMonth: Int,
    val days: List<H2CalendarListItem>) {

    fun getMillis(): Long {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, calendarMonth)
            set(Calendar.DATE, 1)
        }.time.time
    }
}