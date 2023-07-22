package com.typ.islamictkt.location

/**
 * Popular Locations Object.
 *
 * The `PopularLocations` object represents a collection of popular or frequently accessed locations in your application.
 * It provides a centralized location to manage and access a list of pre-defined popular locations.
 * This object operates as a singleton, ensuring that there is only one instance throughout the application.
 *
 * Developers can use this object to retrieve the list of popular locations and
 * use them for various location-related functionalities.
 */
object PopularLocations {

    /**
     * Egypt Object.
     *
     * The `Egypt` object represents a specific location or entity, in this case, the country of Egypt.
     *
     */
    object Egypt {

        /**
         * Cairo Location.
         *
         * The constant `CAIRO` represents the location of Cairo, Egypt.
         * It is an instance of the `Location` class with specific properties for Cairo's location information.
         *
         * @property code The country code of Cairo (EG for Egypt).
         * @property latitude The latitude coordinate of Cairo.
         * @property longitude The longitude coordinate of Cairo.
         * @property timezone The timezone offset of Cairo.
         */
        val CAIRO = Location(
            code = "EG",
            latitude = 30.12367823,
            longitude = 31.25339501,
            timezone = 2.0
        )

    }

}