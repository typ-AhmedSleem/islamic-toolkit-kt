package com.typ.islamictkt.models

import com.typ.islamictkt.datetime.Timestamp
import com.typ.islamictkt.enums.PrayType
import com.typ.islamictkt.lib.PrayerTimesCalculator
import com.typ.islamictkt.location.Location
import com.typ.islamictkt.utils.byHMS

open class PrayerTimes constructor(
    val fajr: Pray,
    val sunrise: Pray,
    val dhuhr: Pray,
    val asr: Pray,
    val maghrib: Pray,
    val isha: Pray
) {

    operator fun get(index: Int) = toArray()[index]

    operator fun contains(pray: Pray) = pray in toArray()

    operator fun iterator() = toArray().iterator()

    fun toArray() = arrayOf(fajr, sunrise, dhuhr, asr, maghrib, isha)

    fun toArrayNoSunrise() = arrayOf(fajr, dhuhr, asr, maghrib, isha)

    override fun toString(): String {
        return """
            PrayTimes{
                fajr=${fajr}
                sunrise=${sunrise}
                dhuhr=${dhuhr}
                asr=${asr}
                maghrib=${maghrib}
                isha=${isha}
            }
            """.trimIndent()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PrayerTimes) return false

        if (fajr != other.fajr) return false
        if (sunrise != other.sunrise) return false
        if (dhuhr != other.dhuhr) return false
        if (asr != other.asr) return false
        if (maghrib != other.maghrib) return false
        return isha == other.isha
    }

    override fun hashCode(): Int {
        var result = fajr.hashCode()
        result = 31 * result + sunrise.hashCode()
        result = 31 * result + dhuhr.hashCode()
        result = 31 * result + asr.hashCode()
        result = 31 * result + maghrib.hashCode()
        result = 31 * result + isha.hashCode()
        return result
    }

    companion object {
        @JvmStatic
        fun getTodayPrays(location: Location, config: PrayerTimesCalculator.Config): PrayerTimes {
            val today = Timestamp.now()
            val rawPrays = PrayerTimesCalculator.newInstance(location, config).getPrayTimes(0)

            return PrayerTimes(
                fajr = Pray(PrayType.FAJR, today.byHMS(rawPrays[0])),
                sunrise = Pray(PrayType.SUNRISE, today.byHMS(rawPrays[1])),
                dhuhr = Pray(PrayType.DHUHR, today.byHMS(rawPrays[2])),
                asr = Pray(PrayType.ASR, today.byHMS(rawPrays[3])),
                maghrib = Pray(PrayType.MAGHRIB, today.byHMS(rawPrays[4])),
                isha = Pray(PrayType.ISHA, today.byHMS(rawPrays[5]))
            )
        }

    }
}
