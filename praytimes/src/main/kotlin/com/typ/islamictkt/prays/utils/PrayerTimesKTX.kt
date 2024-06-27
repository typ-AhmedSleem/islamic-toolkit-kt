package com.typ.islamictkt.prays.utils

import com.typ.islamictkt.prays.enums.CalculationMethod
import com.typ.islamictkt.prays.lib.CalculationMethodParameters
import com.typ.islamictkt.prays.lib.PrayerTimesCalculator
import com.typ.islamictkt.prays.lib.PrayerTimesOffsets

inline fun prayerTimesCalcConfig(config: PrayerTimesCalculator.Config.() -> Unit): PrayerTimesCalculator.Config {
    return PrayerTimesCalculator.Config().apply(config)
}

inline fun prayerTimesOffsets(offsets: PrayerTimesOffsets.() -> Unit): PrayerTimesOffsets {
    return PrayerTimesOffsets().apply(offsets)
}

inline fun calcMethodParameters(params: CalculationMethodParameters.() -> Unit): CalculationMethodParameters {
    return CalculationMethod.CUSTOM.parameters.apply(params)
}

fun zeroOffsets() = PrayerTimesOffsets()

fun PrayerTimesOffsets.zeros(): PrayerTimesOffsets {
    return this.apply {
        fajr = 0
        sunrise = 0
        dhuhr = 0
        asr = 0
        maghrib = 0
        isha = 0
    }
}