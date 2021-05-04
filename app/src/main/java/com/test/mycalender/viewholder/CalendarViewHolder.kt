package com.test.mycalender.viewholder

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.test.h2.BaseRecyclerViewHolder
import com.test.mycalender.H2CalendarHelper
import com.test.mycalender.adapter.H2CalendarRecyclerViewAdapter
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
    }

    fun setSelectedDate(selectedDate: Date) {
        calendarRecyclerViewAdapter.setSelectedDate(selectedDate)
    }

    override fun bind(data: H2CalendarModel) {
        setYearWithMonthText(itemView, data)
        setWeekOfDaysText(itemView)
        setRecyclerView(itemView, data)
    }

    private fun setYearWithMonthText(itemView: View, calendarModel: H2CalendarModel) {
        itemView.text_year_and_month.text = H2CalendarHelper
            .getYearMonth(itemView.context, calendarModel.getMillis())
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