package com.test.mycalender

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.test.mycalender.dialog.H2CalendarDialogFragment
import com.test.mycalender.helper.DataHelper
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_calendar).setOnClickListener {
            H2CalendarDialogFragment
                .newInstance(DataHelper().createFakeEvent(), Calendar.getInstance().apply {
                    set(Calendar.YEAR, 2020)
                    set(Calendar.MONTH, Calendar.NOVEMBER)
                }.time)
                .show(supportFragmentManager, H2CalendarDialogFragment::class.simpleName)
        }
    }

}