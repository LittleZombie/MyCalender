package com.test.mycalender.viewholder

import android.view.ViewGroup
import com.test.h2.BaseRecyclerViewHolder
import com.test.mycalender.R
import com.test.mycalender.item.H2CalendarListItem

class H2CalendarEmptyItemViewHolder(parent: ViewGroup) :
    BaseRecyclerViewHolder<H2CalendarListItem>(R.layout.item_calendar_week_title, parent) {

    override fun bind(data: H2CalendarListItem) {

    }
}