package com.moodsmap.waterlogging.data.api.domain.fetcher.result_listener

/**
 */
interface ResultListener {

    fun onRequestStart(){}

    fun onRequestStart(requestType: RequestType){}

    fun onRequestError(errorMessage: String?){}

    fun onRequestError(requestType: RequestType, errorMessage: String?){}
}