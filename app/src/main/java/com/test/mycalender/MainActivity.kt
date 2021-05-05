package com.test.mycalender

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.test.mycalender.dialog.H2CalendarDialogFragment
import com.test.mycalender.helper.DataHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), H2CalendarDialogFragment.OnH2CalendarListener {

    private var selectedDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_calendar).setOnClickListener {
            showDatePicker()
        }
    }

    override fun onDateSelected(date: Date) {
        val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.getDefault())
        text_selected_date.text = simpleDateFormat.format(date)
        selectedDate = date
    }

    private fun showDatePicker() {
        H2CalendarDialogFragment
            .newInstance(
                DataHelper().createFakeEvent(),
                this,
                selectedDate = selectedDate
            )
            .show(supportFragmentManager, H2CalendarDialogFragment::class.simpleName)
    }

}