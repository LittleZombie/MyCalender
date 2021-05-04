package com.test.mycalender.viewholder

import android.view.ViewGroup
import com.test.h2.BaseRecyclerViewHolder
import com.test.mycalender.R
import com.test.mycalender.adapter.SelectYearAdapter
import kotlinx.android.synthetic.main.item_year.view.*

class YearViewHolder(parent: ViewGroup, private val listener: SelectYearAdapter.OnYearListener) :
    BaseRecyclerViewHolder<Int>(R.layout.item_year, parent) {

    private var year: Int? = null

    init {
        itemView.setOnClickListener { year?.let { listener.onYearSelected(it) } }
    }

    override fun bind(data: Int) {
        year = data
        itemView.text_year.text = data.toString()
    }

}