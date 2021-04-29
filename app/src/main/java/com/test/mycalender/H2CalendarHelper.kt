package com.test.mycalender

import android.util.MonthDisplayHelper
import com.test.mycalender.item.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object H2CalendarHelper {

    private const val MIN_YEAR = 2018

    fun createCalendarData(): ArrayList<H2CalendarModel> {
        val current: Calendar = Calendar.getInstance()
        val currentYear: Int = current.get(Calendar.YEAR)
        val currentMonth: Int = current.get(Calendar.MONTH)

        val calendarModelList: ArrayList<H2CalendarModel> = arrayListOf()
        for (year: Int in currentYear downTo MIN_YEAR) {
            val maxCalendarMonth: Int = if (currentYear == year) {
                currentMonth
            } else {
                Calendar.DECEMBER
            }
            for (calendarMonth: Int in maxCalendarMonth downTo Calendar.JANUARY) {
                calendarModelList.add(0, generateTotalCalendarViewItem(year, calendarMonth))
            }
        }
        calendarModelList.forEach {
            val month = it.calendarMonth + 1
        }
        return calendarModelList
    }

    fun generateTotalCalendarViewItem(year: Int, calendarMonth: Int): H2CalendarModel {
        val calendar: Calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, calendarMonth)
        }
        val dayOfWeekInMonth = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)
        val helper = MonthDisplayHelper(year, calendarMonth, dayOfWeekInMonth)

        val list: ArrayList<H2CalendarListItem> = arrayListOf()
        // start  sunday()
        for (day: Int in 1..helper.numberOfDaysInMonth) {
            if (day == 1) {
                // start day
                getCalendarWeekOfDays().forEach { dayOfWeek ->
                    when {
                        dayOfWeek < helper.firstDayOfMonth -> {
                            list.add(H2CalendarEmptyItem())
                        }
                        dayOfWeek == helper.firstDayOfMonth -> {
                            list.add(H2CalendarDayItem(day, createDate(year, calendarMonth, day)))
                        }
                        else -> Unit
                    }
                }
            } else {
                list.add(H2CalendarDayItem(day, createDate(year, calendarMonth, day)))
            }
        }
        return H2CalendarModel(year, calendarMonth, list)
    }

    private fun createDate(year: Int, calendarMonth: Int, day: Int): Date {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, calendarMonth)
            set(Calendar.DATE, day)
        }.time
    }

    fun createWeekOfDaysText(): Array<String> {
        val weekOfDayFormat = SimpleDateFormat("EEEEE", Locale.getDefault())
        return Array(7) { index ->
            weekOfDayFormat.format(Calendar.getInstance().apply {
                set(Calendar.DAY_OF_WEEK, getCalendarWeekOfDays()[index])
            }.time)
        }
    }

    private fun getCalendarWeekOfDays(): ArrayList<Int> {
        return arrayListOf(
            Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY,
            Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY
        )
    }

    private fun getCalendarMonths(): ArrayList<Int> {
        return arrayListOf(Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH, Calendar.APRIL,
            Calendar.MAY, Calendar.JUNE, Calendar.JULY, Calendar.AUGUST,
            Calendar.SEPTEMBER, Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER
        )
    }

}