package com.test.mycalender.adapter

import android.view.ViewGroup
import com.test.h2.BaseRecyclerViewAdapter
import com.test.h2.BaseRecyclerViewHolder
import com.test.mycalender.viewholder.YearViewHolder

class SelectYearAdapter(private val listener: OnYearListener) : BaseRecyclerViewAdapter<Int>() {

    private var selectedYear: Int? = null

    fun setSelectedYear(selectedYear: Int) {
        this.selectedYear = selectedYear
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<Int> {
        return YearViewHolder(parent, listener)
    }

    override fun onBind(viewHolder: BaseRecyclerViewHolder<Int>, position: Int) {
        getItem(position)?.run {
            selectedYear?.let { (viewHolder as YearViewHolder).setSelectedYear(it) }
            viewHolder.bind(this)
        }
    }

    interface OnYearListener {
        fun onYearSelected(year: Int)
    }

}