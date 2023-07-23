package com.typ.islamictkt.utils

import com.typ.islamictkt.datetime.HMS
import com.typ.islamictkt.datetime.Timestamp
import com.typ.islamictkt.lib.PrayerTimesCalculator
import com.typ.islamictkt.lib.PrayerTimesOffsets
import java.util.*

inline fun newConfig(config: PrayerTimesCalculator.Config.() -> Unit): PrayerTimesCalculator.Config {
    return PrayerTimesCalculator.Config().apply(config)
}

inline fun newOffsets(offsets: PrayerTimesOffsets.() -> Unit): PrayerTimesOffsets {
    return PrayerTimesOffsets().apply(offsets)
}

fun Timestamp.byHMS(hms: HMS): Timestamp {
    return this.clone().apply {
        this[Calendar.HOUR_OF_DAY] = hms.hours
        this[Calendar.MINUTE] = hms.minutes
        this[Calendar.SECOND] = hms.seconds
    }
}