/*
 * This app is developed by AHMED SLEEM
 *
 * Copyright (c) 2021.  TYP INC. All Rights Reserved
 */
package com.typ.islamictkt.models

import com.typ.islamictkt.datetime.Timestamp
import com.typ.islamictkt.lib.HijriCalendar
import com.typ.islamictkt.lib.ummelqura.UmmalquraCalendar
import com.typ.islamictkt.locale.LocaleManager
import java.util.*

/**
 * Model class representing HijriDate
 */
open class HijriDate(
    val year: Int,
    /**
     * Zero-based Hijri Month index. 0 to 11
     */
    val month: Int,
    /**
     * Hijri Day of this month
     */
    val day: Int
) {

    fun getMonthName(style: Int = Calendar.LONG) = UmmalquraCalendar().getDisplayName(Calendar.MONTH, style, LocaleManager.getDefault())

    fun toGregorian(): Timestamp {
        return HijriCalendar.toGregorian(this)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (this === other) return true
        if (other !is HijriDate) return false
        return day == other.day && month == other.month && year == other.year
    }

    fun isAfter(another: HijriDate?): Boolean {
        return if (another == null) false else day + month + year - (another.day + another.month + another.year) > 0
    }

    fun isBefore(another: HijriDate?): Boolean {
        return if (another == null) false else day + month + year - (another.day + another.month + another.year) < 0
    }

    override fun hashCode(): Int {
        return Objects.hash(day, month, year)
    }

    override fun toString(): String {
        return "HijriDate(year=$year, month=$month-${getMonthName()}, day=$day)"
    }
}