package com.typ.islamictkt.lib

/**
 * fa : fajr angle.
 * ms : maghrib selector (0 = angle; 1 = minutes after sunset).
 * mv : maghrib parameter (in angle or minutes)
 * is : isha selector (0 = angle; 1 = minutes after maghrib)
 * iv : isha parameter (in angle or minutes)
 */
class CalculationMethodParameters(
    fajrAngle: Double,
    maghribAngle: Double,
    maghribMinutes: Int,
    ishaAngle: Double,
    ishaMinutes: Int
) {

    // todo: There's some extra work to be done in the setter of each field.

    var fajrAngle: Double = fajrAngle
        set(value) {
            field = value
        }

    var maghribAngle: Double = maghribAngle
        set(value) {
            field = value
        }

    var maghribMinutes: Int = maghribMinutes
        set(value) {
            field = value
        }
    var ishaAngle: Double = ishaAngle
        set(value) {
            field = value
        }

    var ishaMinutes: Int = ishaMinutes
        set(value) {
            field = value
        }

}