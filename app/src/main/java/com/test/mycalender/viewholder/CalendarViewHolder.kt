package com.test.mycalender.viewholder

import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.test.h2.BaseRecyclerViewHolder
import com.test.mycalender.H2CalendarRecyclerViewAdapter
import com.test.mycalender.R
import com.test.mycalender.item.H2CalendarModel
import kotlinx.android.synthetic.main.view_pager_calendar.view.*
import java.util.*

class CalendarViewHolder(
    parent: ViewGroup,
    private val weekOfDayTextArray: Array<String>,
    listener: H2CalendarRecyclerViewAdapter.OnH2CalendarListener
) : BaseRecyclerViewHolder<H2CalendarModel>(R.layout.view_pager_calendar, parent) {

    private var calendarRecyclerViewAdapter: H2CalendarRecyclerViewAdapter =
        H2CalendarRecyclerViewAdapter(listener)

    fun setEventDates(dateList: ArrayList<Date>) {
        calendarRecyclerViewAdapter.setEventDates(dateList)
        calendarRecyclerViewAdapter.notifyDataSetChanged()
    }

    fun setSelectedDate(selectedDate: Date) {
        calendarRecyclerViewAdapter.setSelectedDate(selectedDate)
        calendarRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun bind(data: H2CalendarModel) {
        setYearWithMonthText(itemView, data)
        setWeekOfDaysText(itemView)
        setRecyclerView(itemView, data)
    }

    private fun setYearWithMonthText(itemView: View, calendarModel: H2CalendarModel) {
        val flags: Int =
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_NO_MONTH_DAY or DateUtils.FORMAT_SHOW_YEAR
        val millis: Long = Calendar.getInstance().apply {
            set(Calendar.YEAR, calendarModel.year)
            set(Calendar.MONTH, calendarModel.calendarMonth)
            set(Calendar.DATE, 1)
        }.time.time
        itemView.text_year_and_month.text =
            DateUtils.formatDateRange(itemView.context, millis, millis, flags)
    }

    private fun setWeekOfDaysText(itemView: View) {
        with(itemView) {
            text_sunday.text = weekOfDayTextArray[0]
            text_monday.text = weekOfDayTextArray[1]
            text_tuesday.text = weekOfDayTextArray[2]
            text_wednesday.text = weekOfDayTextArray[3]
            text_thursday.text = weekOfDayTextArray[4]
            text_friday.text = weekOfDayTextArray[5]
            text_saturday.text = weekOfDayTextArray[6]
        }
    }

    private fun setRecyclerView(itemView: View, calendarModel: H2CalendarModel) {
        calendarRecyclerViewAdapter.clearItems()
        calendarRecyclerViewAdapter.addItems(calendarModel.days)

        with(itemView.recycler_calendar) {
            adapter = calendarRecyclerViewAdapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 7)
        }
    }

}