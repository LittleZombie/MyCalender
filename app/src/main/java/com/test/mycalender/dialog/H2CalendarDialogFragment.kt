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
import kotlin.collections.ArrayList

class H2CalendarDialogFragment : DialogFragment() {

    companion object {

        private const val ARGUMENT_DATA_DATE_LIST = "argument_data_date_list"
        private const val ARGUMENT_MIN_DATE = "argument_min_date"

        fun newInstance(dataDateList: ArrayList<Date>, minDate: Date): H2CalendarDialogFragment {
            return H2CalendarDialogFragment().apply {
                arguments = bundleOf(
                    ARGUMENT_DATA_DATE_LIST to dataDateList,
                    ARGUMENT_MIN_DATE to minDate
                )
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
//        arguments?.let { bundle ->
//            val d =
//                bundle.getSerializable(ARGUMENT_DATA_DATE_LIST)?.checkSerializableIsListOf<Date>()
//                    ?: arrayListOf()
//            val minDate: Date = bundle.getSerializable(ARGUMENT_MIN_DATE) as Date
//        }

        arguments?.getSerializable(ARGUMENT_DATA_DATE_LIST)?.checkSerializableIsListOf<Date>()
            ?.let {
                calendar_view.setEventDates(it as ArrayList<Date>)
            }
    }


}