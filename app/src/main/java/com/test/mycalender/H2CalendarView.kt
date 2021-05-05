package com.test.mycalender

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.test.mycalender.H2CalendarHelper.toMaxHourMinutes
import com.test.mycalender.H2CalendarHelper.toMinHourMinutes
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

    var selectedDate = Date()
        private set
    private val selectYearAdapter = SelectYearAdapter(this)
    private val pagerAdapter = H2CalendarPagerAdapter(this)
    private var calendarModelList: ArrayList<H2CalendarModel> = arrayListOf()
    private var yearList: ArrayList<Int> = arrayListOf()
    private var minCalendar: Calendar = H2CalendarHelper.getMinCalendar()
    private var maxCalendar: Calendar = H2CalendarHelper.getMaxCalendar()

    companion object {
        private const val PREVIOUS = -1
        private const val NEXT = 1
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_h2_calendar, this, true)
    }

    fun setMinCalendar(minCalendar: Calendar) {
        this.minCalendar = minCalendar.apply {
            toMinHourMinutes()
        }
    }

    fun setMaxCalendar(maxCalendar: Calendar) {
        this.maxCalendar = maxCalendar.apply {
            toMaxHourMinutes()
        }
    }

    fun setEventDates(dateList: ArrayList<Date>) {
        this.pagerAdapter.setEventDates(dateList)
    }

    fun setSelectedDate(selectedDate: Date) {
        this.selectedDate = selectedDate
    }

    fun initView() {
        val pairData: Pair<ArrayList<H2CalendarModel>, ArrayList<Int> /* year */> =
            H2CalendarHelper.createCalendarData(
                minCalendar = minCalendar,
                maxCalendar = maxCalendar
            )
        calendarModelList = pairData.first
        yearList = pairData.second
        initCalendarView()
    }

    override fun onDaySelected(selectedDate: Date, isScrollToCurrentPage: Boolean) {
        this.selectedDate = Calendar.getInstance().apply {
            time = selectedDate
            toMinHourMinutes()
        }.time

        selectYearAdapter.setSelectedYear(getSelectedYear())
        pagerAdapter.setSelectedDate(selectedDate)
        text_month_day.text = H2CalendarHelper.getMonthDay(selectedDate)
        text_year.text = H2CalendarHelper.getYear(selectedDate)
        if (isScrollToCurrentPage) {
            scrollToPage(year = getSelectedYear(), calendarMonth = getSelectedMonth())
        }
    }

    override fun onYearSelected(year: Int) {
        val calendar: Calendar = Calendar.getInstance().apply {
            time = selectedDate
            set(Calendar.YEAR, year)
            toMinHourMinutes()
        }
        if (calendar.time > maxCalendar.time) {
            calendar.time = maxCalendar.time
        } else if (calendar.time < minCalendar.time) {
            calendar.time = minCalendar.time
        }
        scrollToPage(year = year, calendarMonth = calendar.get(Calendar.MONTH))
        onDaySelected(selectedDate = calendar.time)
        showCalendarView()
    }

    private fun scrollToPage(year: Int, calendarMonth: Int) {
        calendarModelList.indexOfFirst {
            it.year == year && it.calendarMonth == calendarMonth
        }.let { pagePosition ->
            setPage(pagePosition)
        }
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
                setSelectedDate(selectedDate)
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
        recycler_view_year.adapter = selectYearAdapter.apply {
            setItems(yearList)
            setSelectedYear(getSelectedYear())
        }
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
        scrollToCurrentYear()
        setYearAndDateTextColor()
    }

    private fun showCalendarView() {
        recycler_view_year.visibility = View.GONE
        view_pager.visibility = View.VISIBLE
        image_arrow_previous.visibility = View.VISIBLE
        image_arrow_next.visibility = View.VISIBLE
        scrollToCurrentMonth()
        setYearAndDateTextColor()
    }

    private fun scrollToCurrentMonth() {
        val position: Int = calendarModelList.indexOfFirst {
            it.year == getSelectedYear() && it.calendarMonth == getSelectedMonth()
        }
        setPage(position)
    }

    private fun scrollToCurrentYear() {
        val currentYear: Int = getSelectedYear()
        val position: Int = yearList.indexOf(currentYear)
        recycler_view_year.scrollToPosition(position)
    }

    private fun setYearAndDateTextColor() {
        val white = ContextCompat.getColor(context, R.color.white)
        val alphaWhite = ContextCompat.getColor(context, R.color.white_200)
        if (recycler_view_year.isShown) {
            text_year.setTextColor(white)
            text_month_day.setTextColor(alphaWhite)
        } else {
            text_year.setTextColor(alphaWhite)
            text_month_day.setTextColor(white)
        }
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

    private fun getSelectedYear(): Int = getSelectedCalendar().get(Calendar.YEAR)

    private fun getSelectedMonth(): Int = getSelectedCalendar().get(Calendar.MONTH)

    private fun getSelectedCalendar(): Calendar {
        return Calendar.getInstance().apply {
            time = selectedDate
        }
    }

    private fun getMaxPagePosition(): Int {
        return pagerAdapter.itemCount - 1
    }

}