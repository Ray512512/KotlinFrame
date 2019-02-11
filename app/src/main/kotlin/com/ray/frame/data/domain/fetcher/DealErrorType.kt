package com.ray.frame.data.domain.fetcher

/**
 * 网络异常后处理方式
 */
sealed class DealErrorType {
    //弹窗
    object DIALOG : DealErrorType()
    //toast
    object TOAST : DealErrorType()
    //界面显示
    object LAYOUT : DealErrorType()
}