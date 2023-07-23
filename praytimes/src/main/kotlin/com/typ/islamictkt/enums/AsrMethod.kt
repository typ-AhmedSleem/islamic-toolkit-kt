package com.typ.islamictkt.enums

// todo: Must be documented
enum class AsrMethod {

    /**
     * Shafii
     */
    SHAFII,

    /**
     * Hanafi
     */
    HANAFI;

    val shadowLength: Double
        get() = when (this) {
            SHAFII -> 1.0
            HANAFI -> 2.0
        }

}
