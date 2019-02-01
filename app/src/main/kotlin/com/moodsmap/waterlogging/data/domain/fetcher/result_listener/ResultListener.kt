package com.moodsmap.waterlogging.data.domain.fetcher.result_listener

/**
 */
interface ResultListener {
    /**
     * 请求开始
     */
    fun onRequestStart(){}

    /**
     * 请求开始 带type
     */
    fun onRequestStart(requestType: RequestType){}

    /**
     * 请求过程异常 异常内容 或 code
     */
    fun onRequestError(errorMessage: String?){}

    /**
     * 请求过程异常 type区分
     */
    fun onRequestError(requestType: RequestType, errorMessage: String?){}

    /**
     * 请求过程异常 异常
     */
    fun onRequestError(requestType: RequestType, error: Throwable){}
}