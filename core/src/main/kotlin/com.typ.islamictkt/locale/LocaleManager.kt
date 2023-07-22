package com.typ.islamictkt.locale

import java.util.*

/**
 * Locale Manager
 *
 * The `LocaleManager` object provides utility functions and management for handling locales in your application.
 * The object can be used to access locale-related functionality throughout the application.
 */
object LocaleManager {

    /**
     * Get the Default Locale.
     *
     * This function returns the default locale used in the system. The default locale
     * is determined based on the language and country settings of the underlying operating system.
     *
     * @return The default `Locale` used in the system.
     *
     * @see Locale.getDefault
     */
    fun getDefault(): Locale = Locale.getDefault()

    /**
     * Locales
     *
     * The `Locales` object provides an easy way to get locales that are not in `java.util.Locale`
     * such as: Arabic
     */
    object Locales {
        // Predefined supported locales
        val ARABIC = Locale("ar")
    }

}