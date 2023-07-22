/*
 * This app is developed by AHMED SLEEM
 *
 * Copyright (c) 2021.  TYP INC. All Rights Reserved
 */
package com.typ.islamictkt.datetime

import java.util.concurrent.TimeUnit
import kotlin.math.max

class YMD internal constructor(
    years: Int,
    months: Int,
    days: Int
) {

    val years: Int
    val months: Int
    val days: Int

    init {
        this.years = max(years, 0)
        this.months = max(months, 0)
        this.days = max(days, 0)
    }

    override fun toString(): String {
        return "Period{" +
                "years=" + years +
                ", months=" + months +
                ", days=" + days +
                '}'
    }

    companion object {
        fun of(years: Int, months: Int, days: Int): YMD {
            return YMD(years, months, days)
        }

        fun ofYears(years: Int): YMD {
            return YMD(years, 0, 0)
        }

        fun ofMonths(months: Int): YMD {
            return YMD(0, months, 0)
        }

        fun ofDays(days: Int): YMD {
            return YMD(0, 0, days)
        }

        fun from(millis: Long): YMD {
            if (millis <= 0) return YMD(0, 0, -1)
            val years: Int
            var months: Int
            var days = TimeUnit.MILLISECONDS.toDays(millis).toInt()
            // Get months count
            months = days / 30
            days -= months * 30
            // Get years count
            years = months / 12
            months -= years * 12
            // Return the period
            return YMD(years, months, days)
        }

        fun between(start: Long, end: Long): YMD {
            return from(end - start)
        }
    }
}
