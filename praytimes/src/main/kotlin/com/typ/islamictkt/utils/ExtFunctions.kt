package com.typ.islamictkt.utils

import com.typ.islamictkt.datetime.HMS
import com.typ.islamictkt.datetime.Timestamp
import com.typ.islamictkt.enums.CalculationMethod
import com.typ.islamictkt.lib.CalculationMethodParameters
import com.typ.islamictkt.lib.PrayerTimesCalculator
import com.typ.islamictkt.lib.PrayerTimesOffsets
import java.util.*

inline fun prayerTimesCalcConfig(config: PrayerTimesCalculator.Config.() -> Unit): PrayerTimesCalculator.Config {
    return PrayerTimesCalculator.Config().apply(config)
}

inline fun prayerTimesOffsets(offsets: PrayerTimesOffsets.() -> Unit): PrayerTimesOffsets {
    return PrayerTimesOffsets().apply(offsets)
}

inline fun calcMethodParameters(params: CalculationMethodParameters.() -> Unit): CalculationMethodParameters {
    return CalculationMethod.CUSTOM.parameters.apply(params)
}

fun Timestamp.byHMS(hms: HMS): Timestamp {
    return this.clone().apply {
        this[Calendar.HOUR_OF_DAY] = hms.hours
        this[Calendar.MINUTE] = hms.minutes
        this[Calendar.SECOND] = hms.seconds
    }
}