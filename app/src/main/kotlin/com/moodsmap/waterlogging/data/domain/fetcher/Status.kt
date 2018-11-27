package com.moodsmap.waterlogging.data.domain.fetcher

/**
 */
sealed class Status {

    object LOADING : Status()
    object ERROR : Status()
    object SUCCESS : Status()
    object EMPTY_SUCCESS : Status()
    object IDLE : Status()
}