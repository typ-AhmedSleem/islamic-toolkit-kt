package com.typ.islamictkt.enums

enum class PrayType {
    FAJR,
    SUNRISE,
    DHUHR,
    ASR,
    MAGHRIB,
    ISHA;

    fun ordinalWithoutSunrise(): Int {
        return if (this == FAJR) 0 else ordinal - 1
    }
}
