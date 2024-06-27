/*
 * This app is developed by AHMED SLEEM
 *
 * Copyright (c) 2021.  TYP INC. All Rights Reserved
 */
package com.typ.islamictkt.lib

import com.typ.islamictkt.annotations.IntRange
import com.typ.islamictkt.datetime.Timestamp
import com.typ.islamictkt.lib.ummelqura.UmmalquraCalendar
import com.typ.islamictkt.lib.ummelqura.UmmalquraGregorianConverter
import com.typ.islamictkt.models.HijriDate
import com.typ.islamictkt.utils.toHijri
import java.util.*

/**
 * Contains necessary code that works with Hijri Dates
 */
object HijriCalendar {

    fun getHijriDate(year: Int, month: Int, day: Int) = HijriDate(year, month, day)

    @JvmStatic
    val yesterday: HijriDate
        get() = Timestamp.yesterday().toHijri()

    @JvmStatic
    val today: HijriDate
        get() = Timestamp.now().toHijri()

    @JvmStatic
    val tomorrow: HijriDate
        get() = Timestamp.tomorrow().toHijri()

    @JvmStatic
    fun getHijriDates(dates: List<Date>): Array<HijriDate> {
        val hijriDates: MutableList<HijriDate> = ArrayList()
        for (date in dates) {
            hijriDates.add(toHijri(Timestamp(date)))
        }
        return hijriDates.toTypedArray()
    }

    @JvmStatic
    fun toHijri(date: Timestamp): HijriDate {
        return UmmalquraGregorianConverter.toHijri(date).run {
            HijriDate(this[0], this[1], this[2])
        }
    }

    @JvmStatic
    fun toGregorian(hijriDate: HijriDate): Timestamp {
        return UmmalquraGregorianConverter.toGregorian(hijriDate.year, hijriDate.month, hijriDate.day).run {
            Timestamp(this[0], this[1], this[2])
        }
    }

    /**
     * @param hMonth Zero-based month number
     */
    @JvmStatic
    fun lengthOfMonth(hYear: Int, @IntRange(from = 0, to = 11) hMonth: Int): Int {
        return UmmalquraCalendar.lengthOfMonth(hYear, hMonth)
    }
}