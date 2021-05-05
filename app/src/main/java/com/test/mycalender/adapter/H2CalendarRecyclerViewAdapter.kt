package com.test.mycalender.adapter

import android.view.ViewGroup
import com.test.h2.BaseRecyclerViewAdapter
import com.test.h2.BaseRecyclerViewHolder
import com.test.mycalender.item.H2CalendarListItem
import com.test.mycalender.viewholder.H2CalendarDayItemViewHolder
import com.test.mycalender.viewholder.H2CalendarEmptyItemViewHolder
import java.util.*

class H2CalendarRecyclerViewAdapter(private val listener: OnH2CalendarListener) :
    BaseRecyclerViewAdapter<H2CalendarListItem>() {

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
    ): BaseRecyclerViewHolder<H2CalendarListItem> {
        return when (viewType) {
            H2CalendarListItem.Type.DAY_ITEM -> H2CalendarDayItemViewHolder(parent, listener)
            H2CalendarListItem.Type.EMPTY_ITEM -> H2CalendarEmptyItemViewHolder(parent)
            else -> throw IllegalArgumentException("Illegal H2CalendarAdapter viewHolder type!!")
        }
    }

    override fun onBind(viewHolder: BaseRecyclerViewHolder<H2CalendarListItem>, position: Int) {
        getItem(position)?.let {
            if (H2CalendarListItem.Type.DAY_ITEM == it.type) {
                (viewHolder as H2CalendarDayItemViewHolder).apply {
                    dateList?.run { setEventDates(this) }
                    selectedDate?.run { setSelectedDate(this) }
                }
            }
            viewHolder.bind(it)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.type ?: H2CalendarListItem.Type.EMPTY_ITEM
    }

    interface OnH2CalendarListener {
        fun onDaySelected(selectedDate: Date, isScrollToCurrentPage: Boolean = false)
    }
}