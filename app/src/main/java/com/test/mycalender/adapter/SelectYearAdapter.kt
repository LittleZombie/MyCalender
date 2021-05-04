package com.test.mycalender.adapter

import android.view.ViewGroup
import com.test.h2.BaseRecyclerViewAdapter
import com.test.h2.BaseRecyclerViewHolder
import com.test.mycalender.viewholder.YearViewHolder

class SelectYearAdapter(private val listener: OnYearListener): BaseRecyclerViewAdapter<Int>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<Int> {
        return YearViewHolder(parent, listener)
    }

    override fun onBind(viewHolder: BaseRecyclerViewHolder<Int>, position: Int) {
        getItem(position)?.run { viewHolder.bind(this) }
    }

    interface OnYearListener {
        fun onYearSelected(year: Int)
    }

}