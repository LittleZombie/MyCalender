package com.test.mycalender

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.test.mycalender.adapter.H2CalendarRecyclerViewAdapter
import com.test.mycalender.adapter.SelectYearAdapter
import com.test.mycalender.item.H2CalendarModel
import com.test.mycalender.viewpager.H2CalendarPagerAdapter
import kotlinx.android.synthetic.main.view_h2_calendar.view.*
import java.util.*

class H2CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr),
    H2CalendarRecyclerViewAdapter.OnH2CalendarListener, SelectYearAdapter.OnYearListener {

    private val selectYearAdapter = SelectYearAdapter(this)
    private val pagerAdapter = H2CalendarPagerAdapter(this)
    private var calendarModelList: ArrayList<H2CalendarModel> = arrayListOf()
    private var yearList: ArrayList<Int> = arrayListOf()
    private var selectedDate = Date()

    companion object {
        private const val PREVIOUS = -1
        private const val NEXT = 1
    }

    init {
        val pairData = H2CalendarHelper.createCalendarData()
        calendarModelList = pairData.first
        yearList = pairData.second

        LayoutInflater.from(context).inflate(R.layout.view_h2_calendar, this, true)
        initCalendarView()
    }

    fun setEventDates(dateList: ArrayList<Date>) {
        pagerAdapter.setEventDates(dateList)
    }

    override fun onDayClicked(selectedDate: Date) {
        this.selectedDate = selectedDate
        pagerAdapter.setSelectedDate(selectedDate)

        text_month_day.text = H2CalendarHelper.getMonthDay(selectedDate)
        text_year.text = H2CalendarHelper.getYear(selectedDate)
    }


    override fun onYearSelected(year: Int) {
        val calendar = Calendar.getInstance().apply {
            time = selectedDate
            set(Calendar.YEAR, year)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.SECOND, 0)
        }
        calendarModelList.indexOfFirst {
            it.year == year && it.calendarMonth == calendar.get(Calendar.MONTH)
        }.let { pagePosition ->
            setPage(pagePosition)
        }
        onDayClicked(calendar.time)
        showCalendarView()
    }

    private fun initCalendarView() {
        setCalendarViewPager()
        setYearRecyclerView()
        showCalendarView()
        setArrowView(view_pager.currentItem)
        setDateTextView()
        setClickListener()
    }

    private fun setCalendarViewPager() {
        with(view_pager) {
            adapter = pagerAdapter.apply {
                setItems(calendarModelList)
            }
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setArrowView(position)
                }
            })
        }
        setPage(getMaxPagePosition())
    }

    private fun setPage(position: Int) {
        view_pager.setCurrentItem(position, false)
    }

    private fun setYearRecyclerView() {
        selectYearAdapter.setItems(yearList)
        recycler_view_year.adapter = selectYearAdapter
        recycler_view_year.layoutManager = LinearLayoutManager(context)
    }

    private fun setArrowView(currentPosition: Int) {
        when {
            currentPosition <= 0 -> {
                image_arrow_previous.visibility = View.INVISIBLE
                image_arrow_next.visibility = View.VISIBLE
            }
            currentPosition >= getMaxPagePosition() -> {
                image_arrow_previous.visibility = View.VISIBLE
                image_arrow_next.visibility = View.INVISIBLE
            }
            else -> {
                image_arrow_previous.visibility = View.VISIBLE
                image_arrow_next.visibility = View.VISIBLE
            }
        }
    }

    private fun setDateTextView() {
        text_year.text = H2CalendarHelper.getYear(selectedDate)
        text_month_day.text = H2CalendarHelper.getMonthDay(selectedDate)
    }

    private fun setClickListener() {
        text_year.setOnClickListener { showYearList() }
        text_month_day.setOnClickListener { showCalendarView() }
        image_arrow_previous.setOnClickListener { onArrowClicked(PREVIOUS) }
        image_arrow_next.setOnClickListener { onArrowClicked(NEXT) }
    }

    private fun showYearList() {
        recycler_view_year.visibility = View.VISIBLE

        view_pager.visibility = View.GONE
        image_arrow_previous.visibility = View.GONE
        image_arrow_next.visibility = View.GONE
    }

    private fun showCalendarView() {
        recycler_view_year.visibility = View.GONE

        view_pager.visibility = View.VISIBLE
        image_arrow_previous.visibility = View.VISIBLE
        image_arrow_next.visibility = View.VISIBLE
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

    private fun getMaxPagePosition(): Int {
        return pagerAdapter.itemCount - 1
    }

}