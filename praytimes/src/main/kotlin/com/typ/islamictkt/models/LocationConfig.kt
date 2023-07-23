package com.typ.islamictkt.models

import com.typ.islamictkt.enums.AsrMethod
import com.typ.islamictkt.enums.CalculationMethod
import com.typ.islamictkt.enums.HigherLatitudeMethod

data class LocationConfig(
    val calculationMethod: CalculationMethod,

    val asrMethod: AsrMethod,

    val higherLatMethod: HigherLatitudeMethod
)