package com.typ.islamictkt.lib

import com.typ.islamictkt.datetime.HMS
import com.typ.islamictkt.datetime.Timestamp
import com.typ.islamictkt.datetime.YMD
import com.typ.islamictkt.enums.AsrMethod
import com.typ.islamictkt.enums.CalculationMethod
import com.typ.islamictkt.enums.HigherLatitudeMethod
import com.typ.islamictkt.location.Location
import com.typ.islamictkt.models.PrayerTimes
import com.typ.islamictkt.utils.AstronomyUtils
import com.typ.islamictkt.utils.AstronomyUtils.calculateDayPortion
import com.typ.islamictkt.utils.AstronomyUtils.calculateMidDay
import com.typ.islamictkt.utils.AstronomyUtils.calculateNightPortion
import com.typ.islamictkt.utils.AstronomyUtils.calculateSunDeclination
import com.typ.islamictkt.utils.AstronomyUtils.fixHour
import com.typ.islamictkt.utils.MathUtils.dArcCos
import com.typ.islamictkt.utils.MathUtils.dArcCot
import com.typ.islamictkt.utils.MathUtils.dCos
import com.typ.islamictkt.utils.MathUtils.dSin
import com.typ.islamictkt.utils.MathUtils.dTan
import java.util.*
import kotlin.math.abs
import kotlin.math.floor

/**
 * Class that do all complicated calculations to get PrayerTimes
 * NOTE: You don't have to use this class directly
 * instead, access it's higher-level impl found in [PrayerTimes].
 *
 * @sample PrayerTimes.getPrays
 * @sample PrayerTimes.getNextPray
 * @sample PrayerTimes.getTodayPrays
 */
class PrayerTimesCalculator(
    val location: Location,
    val config: Config
) {

    /**
     * Compute time for the given angle
     */
    private fun computeTime(jDate: Double, angle: Double, dayPortionTime: Double): Double {
        val sunDec = calculateSunDeclination(jDate + dayPortionTime)
        val zenith = calculateMidDay(jDate, dayPortionTime)
        val Beg = -dSin(angle) - dSin(sunDec) * dSin(location.latitude)
        val Mid = dCos(sunDec) * dCos(location.latitude)
        val V = dArcCos(Beg / Mid) / 15.0
        return zenith + if (angle > 90) -V else V
    }

    /** Compute the time of Asr
     *
     * Shafii: ShadowLength = 1
     * Hanafi: ShadowLength = 2
     */
    private fun computeAsr(jDate: Double, shadowLength: Double, t: Double): Double {
        val sunDec = calculateSunDeclination(jDate + t)
        val G = -dArcCot(shadowLength + dTan(abs(location.latitude - sunDec)))
        return computeTime(jDate, G, t)
    }

    /** Compute the difference between two times */
    private fun timeDiff(time1: Double, time2: Double): Double {
        return fixHour(time2 - time1)
    }

    /** Set custom values for calculation parameters
     *
     * NOTE: Custom parameters take effect only if
     * [CalculationMethod.CUSTOM] is the method set in params.
     *
     * Fajr angle.
     * Maghrib selector (0 => Angle | 1 => Minutes)
     * Maghrib minutes | Maghrib angle.
     * Isha selector (0 => Angle | 1 => Minutes)
     * Isha minutes | Isha angle.
     *
     */

    private fun rawToTime(time: Double): HMS {
        var temp = time
        if (temp.isNaN()) return HMS(0, 0, 0) // Invalid time
        // Fix hours and minutes
        temp = fixHour(temp + 0.5 / 60.0) // add 0.5 minutes to round
        val hrs = floor(temp).toInt()
        val mins = floor((temp - hrs) * 60.0)
        return HMS(hours = hrs, minutes = mins.toInt(), seconds = 0)
    }

    /**
     * Compute prayer times at given julian date.
     */
    private fun computePrayerTimes(jDate: Double): Array<HMS> {
        val times = doubleArrayOf(5.0, 6.0, 12.0, 13.0, 18.0, 18.0, 18.0)
        val params = config.params
        // =============== Calculate PrayerTimes in each iteration ================ //
        for (i in 1..5) {
            val dayPortion = calculateDayPortion(times)

            times[0] = computeTime(jDate, 180 - params.fajrAngle, dayPortion[0]) // Fajr.

            times[1] = computeTime(jDate, 180 - 0.833, dayPortion[1]) // Sunrise.

            times[2] = calculateMidDay(jDate, dayPortion[2]) // Dhuhr.

            times[3] = computeAsr(jDate, config.asrMethod.shadowLength, dayPortion[3]) // Asr.

            times[4] = computeTime(jDate, 0.833, dayPortion[4]) // Sunset.

            times[5] = computeTime(jDate, params.maghribAngle, dayPortion[5]) // Maghrib.

            times[6] = computeTime(jDate, params.ishaAngle, dayPortion[6]) // Isha
        }

        // ============================= Adjust times ============================= //
        val timezone = if (config.useDefaultTimezone) config.defaultTimezone
        else location.timezone

        for (i in times.indices) {
            times[i] += timezone - location.longitude / 15
        }

        // Dhuhr
        times[2] += (config.dhuhrMinutes / 60f).toDouble()

        // Maghrib
        if (params.shouldApplyMaghribMinutes) {
            times[5] = times[4] + params.maghribMinutes / 60
        }

        // Isha
        if (params.shouldApplyIshaMinutes) {
            times[6] = times[5] + params.ishaMinutes / 60
        }

        if (config.higherLatMethod !== HigherLatitudeMethod.NONE) {
            // ===== Adjust Fajr, Isha and Maghrib for locations in higher latitudes ===== //

            val nightTime = timeDiff(times[4], times[1]) // Sunset to Sunrise

            // Adjust Fajr
            val fajrDiff = calculateNightPortion(config.higherLatMethod, params.fajrAngle) * nightTime
            if (times[0].isNaN() || timeDiff(times[0], times[1]) > fajrDiff) {
                times[0] = times[1] - fajrDiff
            }

            // Adjust Isha
            val ishaAngle: Double = if (!params.ishaAngle.isNaN()) params.ishaAngle
            else 18.0

            val ishaDiff = calculateNightPortion(config.higherLatMethod, ishaAngle) * nightTime
            if (times[6].isNaN() || timeDiff(times[4], times[6]) > ishaDiff) {
                times[6] = times[4] + ishaDiff
            }

            // Adjust Maghrib
            val maghribAngle: Double = if (!params.maghribAngle.isNaN()) params.maghribAngle
            else 4.0

            val maghribDiff = calculateNightPortion(config.higherLatMethod, maghribAngle) * nightTime
            if (times[5].isNaN() || timeDiff(times[4], times[5]) > maghribDiff) {
                times[5] = times[4] + maghribDiff
            }
        }

        // ================= Tune times ================= //
        times[0] += config.offsets.fajr / 60.0 // Fajr.
        times[1] += config.offsets.sunrise / 60.0 // Sunrise.
        times[2] += config.offsets.dhuhr / 60.0 // Dhuhr.
        times[3] += config.offsets.asr / 60.0 // Asr.
        times[5] += config.offsets.maghrib / 60.0 // Maghrib.
        times[6] += config.offsets.isha / 60.0 // Isha.

        // Convert raw time to HMS components
        return arrayOf(
            rawToTime(times[0]), // Fajr
            rawToTime(times[1]), // Sunrise
            rawToTime(times[2]), // Dhuhr
            rawToTime(times[3]), // Asr
            rawToTime(times[5]), // Maghrib
            rawToTime(times[6]) // Isha
        )
    }

    fun getPrayTimes(date: Timestamp): Array<HMS> {
        var jDate = AstronomyUtils.calculateJulianDate(YMD.of(date.year, date.month, date.day))

        val lonDiff = location.longitude / (15.0 * 24.0)
        jDate -= lonDiff

        return computePrayerTimes(jDate)
    }

    open class Config(
        var calcMethod: CalculationMethod = CalculationMethod.CUSTOM,
        var asrMethod: AsrMethod = AsrMethod.SHAFII,
        var higherLatMethod: HigherLatitudeMethod = HigherLatitudeMethod.NONE,
        var useDefaultTimezone: Boolean = true,
        var offsets: PrayerTimesOffsets = PrayerTimesOffsets(),
        var dhuhrMinutes: Int = 0
    ) {

        var params: CalculationMethodParameters = CalculationMethod.CUSTOM.parameters
            get() {
                return if (calcMethod == CalculationMethod.CUSTOM) field
                else calcMethod.parameters
            }
            set(value) {
                field = value
                calcMethod = CalculationMethod.CUSTOM
            }

        val defaultTimezone: Double
            get() = TimeZone.getDefault().rawOffset / 3600.0 / 1000

        val daylightSaving: Double = TimeZone.getDefault().dstSavings.toDouble()

        override fun toString(): String {
            return buildString {
                append("Config(calcMethod=")
                append(calcMethod)
                append(", asrMethod=")
                append(asrMethod)
                append(", higherLatMethod=")
                append(higherLatMethod)
                append(", dhuhrMinutes=")
                append(dhuhrMinutes)
                append(", offsets=")
                append(offsets)
                append(", useDefaultTimezone=")
                append(useDefaultTimezone)
                append(", params=")
                append(params)
                append(", defaultTimezone=")
                append(defaultTimezone)
                append(", daylightSaving=")
                append(daylightSaving)
                append(")")
            }
        }

    }

}
