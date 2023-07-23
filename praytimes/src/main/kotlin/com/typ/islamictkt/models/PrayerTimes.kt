package com.typ.islamictkt.models

class PrayerTimes(
    vararg prays: Pray
) {

    val fajr: Pray
    val sunrise: Pray
    val dhuhr: Pray
    val asr: Pray
    val maghrib: Pray
    val isha: Pray

    init {
        if (prays.isEmpty()) throw IllegalStateException("PrayerTimes received an empty list of prays from builder.")
        if (prays.size != 6) throw IllegalStateException("PrayerTimes only accepts a list of 6 prays. found: ${prays.size}")

        fajr = prays[0]
        sunrise = prays[1]
        dhuhr = prays[2]
        asr = prays[3]
        maghrib = prays[4]
        isha = prays[5]
    }

    operator fun get(index: Int) = toArray()[index]

    operator fun contains(pray: Pray) = pray in toArray()

    fun toArray() = arrayOf(fajr, sunrise, dhuhr, asr, maghrib, isha)

    fun toArrayNoSunrise() = arrayOf(fajr, dhuhr, asr, maghrib, isha)

    override fun toString(): String {
        return """
            PrayTimes{fajr=${fajr}
            sunrise=${sunrise}
            dhuhr=${dhuhr}
            asr=${asr}
            maghrib=${maghrib}
            isha=${isha}}
            """.trimIndent()
    }

    class Builder internal constructor() {

        private val prays = arrayOfNulls<Pray>(6)

        fun add(pray: Pray) {
            pray.also { prays[pray.type.ordinal] = it }
        }

        fun setFajr(fajr: Pray) {
            prays[0] = fajr
        }

        fun setSunrise(sunrise: Pray) {
            prays[1] = sunrise
        }

        fun setDhuhr(dhuhr: Pray) {
            prays[2] = dhuhr
        }

        fun setAsr(asr: Pray) {
            prays[3] = asr
        }

        fun setMaghrib(maghrib: Pray) {
            prays[4] = maghrib
        }

        fun setIsha(isha: Pray) {
            prays[5] = isha
        }

        fun build() = PrayerTimes(*prays.requireNoNulls())

    }

    companion object {
        @JvmStatic
        fun newBuilder() = Builder()
    }
}
