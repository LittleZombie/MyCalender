package com.test.mycalender

import android.content.Context
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.util.MonthDisplayHelper
import com.test.mycalender.item.H2CalendarDayItem
import com.test.mycalender.item.H2CalendarEmptyItem
import com.test.mycalender.item.H2CalendarListItem
import com.test.mycalender.item.H2CalendarModel
import kotlinx.android.synthetic.main.view_pager_calendar.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object H2CalendarHelper {

    private const val PATTERN_YEAR = "y"
    private const val PATTERN_MONTH_DAY = "EMMMd"
    private const val PATTERN_SHORT_WEEK_DAY = "EEEEE"

    fun createCalendarData(
        minCalendar: Calendar = getMinCalendar(),
        maxCalendar: Calendar = getMaxCalendar()
    ): Pair<ArrayList<H2CalendarModel>, ArrayList<Int>> {

        val current: Calendar = Calendar.getInstance()
        val currentYear: Int = current.get(Calendar.YEAR)
        val currentMonth: Int = current.get(Calendar.MONTH)

        val minYear: Int = minCalendar.get(Calendar.YEAR)
        val minMonth: Int = minCalendar.get(Calendar.MONTH)

        val yearList: ArrayList<Int> = arrayListOf()
        val calendarModelList: ArrayList<H2CalendarModel> = arrayListOf()
        for (year: Int in currentYear downTo minYear) { // year: 2021, 2020, 2019...
            yearList.add(0, year)

            val maxCalendarMonth: Int = if (currentYear == year) {
                currentMonth
            } else {
                Calendar.DECEMBER
            }
            for (calendarMonth: Int in maxCalendarMonth downTo Calendar.JANUARY) { // month: 11, 10, 9...
                if (year == minYear && calendarMonth < minMonth) {
                    break
                } else {
                    calendarModelList.add(
                        0,
                        generateCalendarModel(
                            year,
                            calendarMonth,
                            minDate = minCalendar.time,
                            maxDate = maxCalendar.time
                        )
                    )
                }
            }
        }
        return Pair(calendarModelList, yearList)
    }

    fun createWeekOfDaysText(): Array<String> {
        val weekOfDayFormat = SimpleDateFormat(PATTERN_SHORT_WEEK_DAY, Locale.getDefault())
        return Array(7) { index ->
            weekOfDayFormat.format(Calendar.getInstance().apply {
                set(Calendar.DAY_OF_WEEK, getCalendarWeekOfDays()[index])
            }.time)
        }
    }

    fun getYearMonth(context: Context, millis: Long): String {
        val flags: Int = DateUtils.FORMAT_SHOW_DATE or
                DateUtils.FORMAT_NO_MONTH_DAY or DateUtils.FORMAT_SHOW_YEAR
        return DateUtils.formatDateRange(context, millis, millis, flags)
    }

    fun getMonthDay(date: Date): String = getDateString(date, PATTERN_MONTH_DAY)

    fun getYear(date: Date): String = getDateString(date, PATTERN_YEAR)

    private fun generateCalendarModel(
        year: Int,
        calendarMonth: Int,
        minDate: Date,
        maxDate: Date
    ): H2CalendarModel {
        val helper = MonthDisplayHelper(year, calendarMonth)
        val totalDaysInMonth: Int = helper.numberOfDaysInMonth
        val firstDayOfWeek: Int = helper.firstDayOfMonth
        val dayList: ArrayList<H2CalendarListItem> = arrayListOf()

        val addDayItemAction: (day: Int) -> Unit = { day ->
            val date: Date = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, calendarMonth)
                set(Calendar.DATE, day)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.SECOND, 0)
            }.time

            dayList.add(
                H2CalendarDayItem(
                    day = day,
                    date = date,
                    isClickable = date.before(maxDate) && date.after(minDate)
                )
            )
        }

        for (day: Int in 1..totalDaysInMonth) {
            if (day == 1) {
                getCalendarWeekOfDays().forEach { calendarWeek ->
                    when {
                        calendarWeek < firstDayOfWeek -> dayList.add(H2CalendarEmptyItem())
                        calendarWeek == firstDayOfWeek -> addDayItemAction(day)
                        else -> Unit
                    }
                }
            } else {
                addDayItemAction(day)
            }
        }
        return H2CalendarModel(year, calendarMonth, dayList)
    }

    private fun getDateString(date: Date, skeleton: String): String {
        val pattern: String = DateFormat.getBestDateTimePattern(Locale.getDefault(), skeleton)
        return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
    }

    private fun getCalendarWeekOfDays(): ArrayList<Int> {
        return arrayListOf(
            Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY,
            Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY
        )
    }

    private fun getMaxCalendar(): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.SECOND, 999)
        }
    }

    private fun getMinCalendar(): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, 2000)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DATE, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.SECOND, 0)
        }
    }

    private fun getCalendarMonths(): ArrayList<Int> {
        return arrayListOf(
            Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH, Calendar.APRIL,
            Calendar.MAY, Calendar.JUNE, Calendar.JULY, Calendar.AUGUST,
            Calendar.SEPTEMBER, Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER
        )
    }

}