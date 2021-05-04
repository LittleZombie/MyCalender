package com.test.mycalender.viewpager

import android.view.ViewGroup
import com.test.h2.BaseRecyclerViewAdapter
import com.test.h2.BaseRecyclerViewHolder
import com.test.mycalender.H2CalendarHelper
import com.test.mycalender.adapter.H2CalendarRecyclerViewAdapter
import com.test.mycalender.item.H2CalendarModel
import com.test.mycalender.viewholder.CalendarViewHolder
import java.util.*

class H2CalendarPagerAdapter(private val listener: H2CalendarRecyclerViewAdapter.OnH2CalendarListener) :
    BaseRecyclerViewAdapter<H2CalendarModel>() {

    private val weekOfDayTextArray: Array<String> = H2CalendarHelper.createWeekOfDaysText()
    private var dateList: ArrayList<Date>? = null
    private var selectedDate: Date? = null

    fun setEventDates(dateList: ArrayList<Date>) {
        this.dateList = dateList
        notifyDataSetChanged()
    }

    fun setSelectedDate(selectedDate: Date) {
        this.selectedDate = selectedDate
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<H2CalendarModel> {
        return CalendarViewHolder(parent, weekOfDayTextArray, listener)
    }

    override fun onBind(viewHolder: BaseRecyclerViewHolder<H2CalendarModel>, position: Int) {
        getItem(position)?.run {
            (viewHolder as CalendarViewHolder).apply {
                dateList?.let { setEventDates(it) }
                selectedDate?.let { setSelectedDate(it) }
            }
            viewHolder.bind(this)
        }
    }

}