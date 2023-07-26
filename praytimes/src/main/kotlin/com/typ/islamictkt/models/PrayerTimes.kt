package com.typ.islamictkt.models

import com.typ.islamictkt.datetime.Timestamp
import com.typ.islamictkt.enums.PrayType
import com.typ.islamictkt.lib.PrayerTimesCalculator
import com.typ.islamictkt.location.Location
import com.typ.islamictkt.utils.byHMS
import java.util.*

open class PrayerTimes private constructor(
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

    val currentPray: Pray
        get() = getCurrentPray(this)

    val nextPray: Pray?
        get() = getNextPray(this)

    val upcomingPrays: Array<Pray>
        get() = getUpcomingPrays(this)

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
            return getPrays(location, config, Timestamp.now())
        }

        @JvmStatic
        fun getPrays(location: Location, config: PrayerTimesCalculator.Config, daysToRoll: Int): PrayerTimes {
            val timestamp: Timestamp = Timestamp.now().apply { roll(Calendar.DATE, daysToRoll) }
            return getPrays(location, config, timestamp)
        }

        @JvmStatic
        fun getPrays(location: Location, config: PrayerTimesCalculator.Config, timestamp: Timestamp): PrayerTimes {
            return PrayerTimesCalculator(location, config).getPrayTimes(timestamp).run {
                PrayerTimes(
                    fajr = Pray(PrayType.FAJR, timestamp.clone().byHMS(this[0])),
                    sunrise = Pray(PrayType.SUNRISE, timestamp.clone().byHMS(this[1])),
                    dhuhr = Pray(PrayType.DHUHR, timestamp.clone().byHMS(this[2])),
                    asr = Pray(PrayType.ASR, timestamp.clone().byHMS(this[3])),
                    maghrib = Pray(PrayType.MAGHRIB, timestamp.clone().byHMS(this[4])),
                    isha = Pray(PrayType.ISHA, timestamp.clone().byHMS(this[5]))
                )
            }
        }

        @JvmStatic
        fun getCurrentPray(prays: PrayerTimes): Pray {
            var currentPray: Pray = prays.fajr
            for (pray in prays.toArrayNoSunrise()) {
                if (pray.passed) currentPray = pray
                else break
            }
            return currentPray
        }

        @JvmStatic
        fun getNextPray(prays: PrayerTimes): Pray? {
            // Get the next pray in given prays
            if (prays.isha.passed) return null
            var nextPray = prays.fajr
            for (pray in prays) {
                if (pray.passed) continue
                nextPray = pray
                break
            }
            return nextPray
        }

        @JvmStatic
        fun getUpcomingPrays(prays: PrayerTimes): Array<Pray> {
            val upPrays = mutableListOf<Pray>()
            for (pray in prays) {
                if (pray.passed) continue
                upPrays.add(pray)
            }
            return upPrays.toTypedArray()
        }

    }
}
