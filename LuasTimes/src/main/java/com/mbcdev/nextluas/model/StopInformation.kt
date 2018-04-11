package com.mbcdev.nextluas.model

data class StopInformation(val displayName: String, val stopNumber: Int) {

    override fun toString(): String {
        return displayName
    }
}