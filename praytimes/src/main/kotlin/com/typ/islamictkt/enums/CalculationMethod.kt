package com.typ.islamictkt.enums

import com.typ.islamictkt.lib.CalculationMethodParameters

/**
 * Calculation Method used during calculating PrayTimes
 */
enum class CalculationMethod {
    /**
     * Ithna Ashari
     */
    JAFARI,

    /**
     * University of Islamic Sciences, Karachi
     */
    KARACHI,

    /**
     * Islamic Society of North America (ISNA)
     */
    ISNA,

    /**
     * Muslim World League (MWL)
     */
    MWL,

    /**
     * Umm al-Qura, Makkah
     */
    MAKKAH,

    /**
     * Egyptian General Authority of Survey
     */
    EGYPT,

    /**
     * Institute of Geophysics, University of Tehran
     */
    TEHRAN,

    /**
     * Custom Setting
     */
    CUSTOM;

    /**
     * fa : fajr angle.
     * ms : (0 = angle; 1 = minutes after sunset).
     * mv : (in angle or minutes)
     * is : (0 = angle; 1 = minutes after maghrib)
     * iv : (in angle or minutes)
     */
    val parameters: CalculationMethodParameters
        get() = when (this) {
            JAFARI -> CalculationMethodParameters(
                fajrAngle = 16.0,
                maghribAngle = 4.0,
                maghribMinutes = Int.MIN_VALUE,
                ishaAngle = 14.0,
                ishaMinutes = Int.MIN_VALUE
            )

            KARACHI -> CalculationMethodParameters(
                fajrAngle = 18.0,
                maghribAngle = Double.NaN,
                maghribMinutes = 0,
                ishaAngle = 18.0,
                ishaMinutes = Int.MIN_VALUE
            )

            ISNA -> CalculationMethodParameters(
                fajrAngle = 15.0,
                maghribAngle = Double.NaN,
                maghribMinutes = 0,
                ishaAngle = 15.0,
                ishaMinutes = Int.MIN_VALUE
            )

            MWL -> CalculationMethodParameters(
                fajrAngle = 18.0,
                maghribAngle = Double.NaN,
                maghribMinutes = 0,
                ishaAngle = 17.0,
                ishaMinutes = Int.MIN_VALUE
            )

            MAKKAH -> CalculationMethodParameters(
                fajrAngle = 18.5,
                maghribAngle = Double.NaN,
                maghribMinutes = 0,
                ishaAngle = 0.0,
                ishaMinutes = 90
            )

            EGYPT -> CalculationMethodParameters(
                fajrAngle = 19.5,
                maghribAngle = Double.NaN,
                maghribMinutes = 0,
                ishaAngle = 17.5,
                ishaMinutes = Int.MIN_VALUE
            )

            TEHRAN -> CalculationMethodParameters(
                fajrAngle = 17.7,
                maghribAngle = 4.5,
                maghribMinutes = Int.MIN_VALUE,
                ishaAngle = 14.0,
                ishaMinutes = Int.MIN_VALUE
            )

            else -> CalculationMethodParameters(
                fajrAngle = 18.0,
                maghribAngle = Double.NaN,
                maghribMinutes = 0,
                ishaAngle = 17.0,
                ishaMinutes = Int.MIN_VALUE
            )
        }

}
