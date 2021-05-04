package com.test.mycalender.helper

import java.util.*

class DataHelper {

    fun createFakeEvent(): ArrayList<Date> {
        val dateList: ArrayList<Date> = arrayListOf()

        dateList.add(Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.MAY)
            set(Calendar.DATE, 3)
        }.time)
        dateList.add(Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.MAY)
            set(Calendar.DATE, 2)
        }.time)
        dateList.add(Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.MAY)
            set(Calendar.DATE, 1)
        }.time)

        dateList.add(Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.APRIL)
            set(Calendar.DATE, 26)
        }.time)
        dateList.add(Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.APRIL)
            set(Calendar.DATE, 25)
        }.time)
        dateList.add(Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.APRIL)
            set(Calendar.DATE, 24)
        }.time)

        dateList.add(Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.APRIL)
            set(Calendar.DATE, 12)
        }.time)
        dateList.add(Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.APRIL)
            set(Calendar.DATE, 13)
        }.time)
        dateList.add(Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.APRIL)
            set(Calendar.DATE, 14)
        }.time)
        dateList.add(Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.APRIL)
            set(Calendar.DATE, 15)
        }.time)
        dateList.add(Calendar.getInstance().apply {
            set(Calendar.MONTH, Calendar.APRIL)
            set(Calendar.DATE, 16)
        }.time)

        return dateList
    }

}