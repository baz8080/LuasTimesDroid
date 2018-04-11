package com.mbcdev.nextluas.model

import java.text.SimpleDateFormat
import java.util.*

data class StopTimes(val inbound: List<StopTime>, val outbound: List<StopTime>, val lastUpdatedAt: String) {

    constructor(inbound: List<StopTime>, outbound: List<StopTime>): this(inbound, outbound, LAST_UPDATED_FORMAT.format(Date()))

    companion object {
        val LAST_UPDATED_FORMAT = SimpleDateFormat("HH:mm:ss", Locale.US)
    }
}