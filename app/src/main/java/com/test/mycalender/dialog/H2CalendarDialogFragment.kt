package com.test.mycalender.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.test.h2.checkSerializableIsListOf
import com.test.mycalender.R
import kotlinx.android.synthetic.main.fragment_h2_calendar.*
import java.util.*

class H2CalendarDialogFragment : DialogFragment() {

    private var minDate: Date? = null
    private var maxDate: Date? = null
    private var selectedDate: Date? = null
    private var dataDateList: List<Date>? = null
    private var listener: OnH2CalendarListener? = null

    companion object {

        private const val ARGUMENT_DATA_DATE_LIST = "argument_data_date_list"
        private const val ARGUMENT_MIN_DATE = "argument_min_date"
        private const val ARGUMENT_MAX_DATE = "argument_max_date"
        private const val ARGUMENT_SELECTED_DATE = "argument_selected_date"

        fun newInstance(
            dataDateList: ArrayList<Date>,
            listener: OnH2CalendarListener,
            minDate: Date? = null,
            maxDate: Date? = null,
            selectedDate: Date? = null
        ): H2CalendarDialogFragment {
            return H2CalendarDialogFragment().apply {
                this.listener = listener
                val bundle: Bundle = bundleOf(ARGUMENT_DATA_DATE_LIST to dataDateList)
                minDate?.let { bundle.putSerializable(ARGUMENT_MIN_DATE, it) }
                maxDate?.let { bundle.putSerializable(ARGUMENT_MAX_DATE, it) }
                selectedDate?.let { bundle.putSerializable(ARGUMENT_SELECTED_DATE, it) }
                arguments = bundle
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.let {
            it.window?.setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.setCanceledOnTouchOutside(false)
        }
        return inflater.inflate(R.layout.fragment_h2_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        setCalendarView()
        setButtonView()
    }

    private fun initData() {
        arguments?.let {
            minDate = it.getSerializable(ARGUMENT_MIN_DATE) as Date?
            maxDate = it.getSerializable(ARGUMENT_MAX_DATE) as Date?
            selectedDate = it.getSerializable(ARGUMENT_SELECTED_DATE) as Date?
            dataDateList = it.getSerializable(ARGUMENT_DATA_DATE_LIST)?.checkSerializableIsListOf()
        }
    }

    private fun setCalendarView() {
        val date = selectedDate ?: getLatestDate()
        with(calendar_view) {
            dataDateList?.let { setEventDates(it as ArrayList<Date>) }
            minDate?.let { setMinCalendar(toCalendar(it)) }
            maxDate?.let { setMaxCalendar(toCalendar(it)) }
            setSelectedDate(date)
            initView()
        }
    }

    private fun setButtonView() {
        text_latest.setOnClickListener {
            calendar_view.onDaySelected(getLatestDate(), isScrollToCurrentPage = true)
        }
        text_cancel.setOnClickListener { dismiss() }
        text_confirm.setOnClickListener {
            listener?.onDateSelected(calendar_view.selectedDate)
            dismiss()
        }
    }

    private fun getLatestDate(): Date = dataDateList?.maxBy { it.time } ?: Date()

    private fun toCalendar(date: Date): Calendar {
        return Calendar.getInstance().apply { time = date }
    }

    interface OnH2CalendarListener {
        fun onDateSelected(date: Date)
    }
}