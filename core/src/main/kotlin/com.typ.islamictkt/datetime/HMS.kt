/*
 * This app is developed by AHMED SLEEM
 *
 * Copyright (c) 2021.  TYP INC. All Rights Reserved
 */
package com.typ.islamictkt.datetime

/**
 * Time Representation with Hours, Minutes, and Seconds (HMS).
 *
 * The `HMS` class represents a time value using hours, minutes, and seconds.
 * It provides a convenient way to work with time components and perform time-related calculations.
 * Instances of this class can be used to represent time durations or specific points in time.
 *
 * @property hours The number of hours in the time representation.
 * @property minutes The number of minutes in the time representation.
 * @property seconds The number of seconds in the time representation.
 */
class HMS {

    /**
     * Hours Property.
     *
     * This property `hours` represents the number of hours in the time representation.
     *
     */
    val hours: Int

    /**
     * Minutes Property.
     *
     * This property `minutes` represents the number of minutes in the time representation.
     *
     */
    val minutes: Int

    /**
     * Seconds Property.
     *
     * This property `seconds` represents the number of seconds in the time representation.
     *
     */
    val seconds: Int

    /**
     * HMS (Hours, Minutes, Seconds) Constructor from Milliseconds.
     *
     * This constructor creates an `HMS` instance using the provided `millis` parameter,
     * which represents the time duration in milliseconds. The `HMS` instance will be initialized
     * with the corresponding hours, minutes, and seconds components extracted from the given milliseconds.
     *
     * @param millis The time duration in milliseconds.
     */
    constructor(millis: Long) {
        var hrs = 0
        var mins = 0
        var secs = 0

        if (millis >= 1000) {
            secs = millis.toInt() / 1000 // 7400
            mins = secs / 60 // 123
            secs -= mins * 60 // 20
            hrs = mins / 60 // 2
            mins -= hrs * 60 // 3
        }

        this.hours = hrs
        this.minutes = mins
        this.seconds = secs
    }

    /**
     * HMS (Hours, Minutes, Seconds) Constructor from Minutes.
     *
     * This constructor creates an `HMS` instance using the provided `minutes` parameter,
     * which represents the time duration in minutes. The `HMS` instance will be initialized
     * with the corresponding hours, minutes, and seconds components extracted from the given minutes.
     *
     * @param minutes The time duration in minutes.
     */
    constructor(minutes: Int) {
        var hrs = 0
        var mins = minutes
        var secs = 0

        if (mins >= 60) {
            hrs /= 60
            mins -= hrs * 60
            secs = mins * 60 - (hrs * 3600 + mins * 60)
        }

        this.hours = hrs
        this.minutes = mins
        this.seconds = secs
    }

    constructor(seconds: Int, minutes: Int, hours: Int) {
        this.seconds = seconds
        this.minutes = minutes
        this.hours = hours
    }
}
