package com.moodsmap.waterlogging.data.domain.fetcher

/**
 * 网络异常后处理方式
 */
sealed class DealErrorType {
    object DIALOG : DealErrorType()
    object TOAST : DealErrorType()
    object LAYOUT : DealErrorType()
}