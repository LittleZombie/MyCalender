package com.test.mycalender.item

import java.util.*
import kotlin.collections.ArrayList

open class H2CalendarListItem(val type: Int) {
    object Type {
        const val DAY_ITEM = 1
        const val EMPTY_ITEM = 2
    }
}

class H2CalendarDayItem(val day: Int, val date: Date): H2CalendarListItem(Type.DAY_ITEM) {
}

class H2CalendarEmptyItem: H2CalendarListItem(Type.EMPTY_ITEM)