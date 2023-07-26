package com.typ.islamictkt.lib

class PrayerTimesOffsets(
    var fajr: Int = 0,
    var sunrise: Int = 0,
    var dhuhr: Int = 0,
    var asr: Int = 0,
    var maghrib: Int = 0,
    var isha: Int = 0
) {
    fun toArray() = arrayOf(fajr, sunrise, dhuhr, asr, maghrib, isha)

    operator fun iterator() = toArray().iterator()
}