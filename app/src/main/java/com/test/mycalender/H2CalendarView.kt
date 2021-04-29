package com.test.mycalender

import android.content.Context
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.test.mycalender.viewpager.H2CalendarPagerAdapter
import kotlinx.android.synthetic.main.view_h2_calendar.view.*
import java.util.*
import kotlin.collections.ArrayList

class H2CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr),
    H2CalendarRecyclerViewAdapter.OnH2CalendarListener {

    private val pagerAdapter: H2CalendarPagerAdapter = H2CalendarPagerAdapter(this).apply {
        setItems(H2CalendarHelper.createCalendarData())
    }
    private var selectedDate = Date()

    companion object {
        private const val PREVIOUS = -1
        private const val NEXT = 1
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_h2_calendar, this, true)
        initCalendarView()
    }

    fun setEventDates(dateList: ArrayList<Date>) {
        pagerAdapter.setEventDates(dateList)
    }

    override fun onDayClicked(selectedDate: Date) {
        this.selectedDate = selectedDate
        pagerAdapter.setSelectedDate(selectedDate)

        text_today.text = DateUtils.formatDateTime(
            text_today.context, selectedDate.time,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
                    or DateUtils.FORMAT_SHOW_WEEKDAY
        )
    }

    private fun initCalendarView() {
        view_pager.adapter = pagerAdapter
        view_pager.currentItem = pagerAdapter.itemCount - 1
        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setArrowView(position)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                //TODO LEE: arrow
            }
        })
        setArrowView(view_pager.currentItem)
        image_arrow_previous.setOnClickListener { onArrowClicked(PREVIOUS) }
        image_arrow_next.setOnClickListener { onArrowClicked(NEXT) }
    }

    private fun onArrowClicked(type: Int) {
        val goToPage: (current: Int) -> Unit = { position ->
            view_pager.setCurrentItem(position, true)
            setArrowView(position)
        }
        val position: Int = view_pager.currentItem + type
        if (PREVIOUS == type && position >= 0) {
            goToPage.invoke(position)
        } else if (NEXT == type && position < pagerAdapter.itemCount) {
            goToPage.invoke(position)
        }
    }

    private fun setArrowView(currentPosition: Int) {
        when {
            currentPosition <= 0 -> {
                image_arrow_previous.visibility = View.GONE
                image_arrow_next.visibility = View.VISIBLE
            }
            currentPosition >= pagerAdapter.itemCount - 1 -> {
                image_arrow_previous.visibility = View.VISIBLE
                image_arrow_next.visibility = View.GONE
            }
            else -> {
                image_arrow_previous.visibility = View.VISIBLE
                image_arrow_next.visibility = View.VISIBLE
            }
        }
    }

}