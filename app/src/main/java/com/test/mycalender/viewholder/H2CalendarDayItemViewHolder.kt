package com.test.mycalender.viewholder

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.test.h2.BaseRecyclerViewHolder
import com.test.mycalender.H2CalendarRecyclerViewAdapter
import com.test.mycalender.R
import com.test.mycalender.item.H2CalendarDayItem
import com.test.mycalender.item.H2CalendarListItem
import kotlinx.android.synthetic.main.item_calendar_day.view.*
import java.util.*
import kotlin.collections.ArrayList

class H2CalendarDayItemViewHolder(parent: ViewGroup,
                                  private val listener: H2CalendarRecyclerViewAdapter.OnH2CalendarListener) :
    BaseRecyclerViewHolder<H2CalendarListItem>(R.layout.item_calendar_day, parent) {

    private var dateList: ArrayList<Date>? = null
    private var selectedDate: Date? = null
    private var itemDate: Date? = null

    init {
        itemView.setOnClickListener { onDateClicked() }
    }

    fun setEventDates(dateList: ArrayList<Date>) {
        this.dateList = dateList
    }

    fun setSelectedDate(selectedDate: Date) {
        this.selectedDate = selectedDate
    }

    override fun bind(data: H2CalendarListItem) {
        if (data is H2CalendarDayItem) {
            itemDate = data.date
            setDayText(data.day)
            setDateBackground(data.date)
            setDot(data.date)
        }
    }

    private fun isSelectedDate(): Boolean {
        return selectedDate != null && itemDate != null && selectedDate == itemDate
    }

    private fun setDayText(day: Int) {
        with(itemView.text_day) {
            text = day.toString()
            setTextColor(ContextCompat.getColor(context, if (isSelectedDate()) {
                R.color.white
            } else {
                R.color.text_black
            }))
        }
    }

    private fun setDot(date: Date) {
        with(itemView.view_dot) {
            if (!hasData(date)) {
                visibility = View.GONE
            } else {
                visibility = View.VISIBLE
                val color: Int = if (isSelectedDate()) {
                    R.color.white
                } else {
                    R.color.green
                }
                ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(
                    ContextCompat.getColor(context, color)))
            }
        }
    }

    private fun setDateBackground(date: Date) {
        when {
            isSelectedDate() -> itemView.text_day.setBackgroundResource(R.drawable.bg_calendar_solid)
            isToday(date) -> itemView.text_day.setBackgroundResource(R.drawable.bg_calendar_stroke)
            else -> itemView.text_day.background = null
        }
    }

    private fun onDateClicked() {
        itemDate?.run {
            listener.onDayClicked(this)
        }
    }

    private fun hasData(date: Date): Boolean {
        var hasData = false
        dateList?.forEach {
            if (isTheSameDay(it, date)) {
                hasData = true
            }
        }
        return hasData
    }

    private fun isToday(date: Date): Boolean {
        val today = Date()
        return isTheSameDay(today, date)
    }

    private fun isTheSameDay(first: Date, second: Date): Boolean {
        val calendar = Calendar.getInstance().apply {
            time = first
        }
        val secondCalendar = Calendar.getInstance().apply {
            time = second
        }
        return calendar.get(Calendar.ERA) == secondCalendar.get(Calendar.ERA) &&
                calendar.get(Calendar.YEAR) == secondCalendar.get(Calendar.YEAR) &&
                calendar.get(Calendar.DAY_OF_YEAR) == secondCalendar.get(Calendar.DAY_OF_YEAR)
    }
}