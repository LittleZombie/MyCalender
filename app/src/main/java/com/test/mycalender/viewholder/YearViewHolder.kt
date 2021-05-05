package com.test.mycalender.viewholder

import android.util.TypedValue
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.test.h2.BaseRecyclerViewHolder
import com.test.mycalender.R
import com.test.mycalender.adapter.SelectYearAdapter
import kotlinx.android.synthetic.main.item_year.view.*

class YearViewHolder(parent: ViewGroup, private val listener: SelectYearAdapter.OnYearListener) :
    BaseRecyclerViewHolder<Int>(R.layout.item_year, parent) {

    private var selectedYear: Int? = null
    private var year: Int? = null

    init {
        itemView.setOnClickListener { year?.let { listener.onYearSelected(it) } }
    }

    fun setSelectedYear(selectedYear: Int) {
        this.selectedYear = selectedYear
    }

    override fun bind(data: Int) {
        year = data

        val yearText: String = data.toString()
        if (selectedYear == year) {
            setTextView(yearText,24f, R.color.green)
        } else {
            setTextView(yearText,18f, R.color.black)
        }
    }

    private fun setTextView(year: String, size: Float, @ColorRes color: Int) {
        with(itemView.text_year) {
            text = year
            setTextSize(TypedValue.COMPLEX_UNIT_DIP, size)
            setTextColor(ContextCompat.getColor(context, color))
        }
    }


}