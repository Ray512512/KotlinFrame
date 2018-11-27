package com.moodsmap.waterlogging.data.domain.fetcher.result_listener

import com.moodsmap.waterlogging.data.domain.fetcher.DealErrorType
/**
 */
enum class RequestType constructor(var dealErrorType: DealErrorType=DealErrorType.DIALOG){

    WARNING,
    TYPE_NONE,

}