package com.test.mycalender.item

data class H2CalendarModel(
    val year: Int,
    val calendarMonth: Int,
    val days: List<H2CalendarListItem>)