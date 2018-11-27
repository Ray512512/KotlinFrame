package com.moodsmap.waterlogging.common.web

import android.annotation.SuppressLint
import android.arch.lifecycle.Lifecycle
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.moodsmap.waterlogging.App
import com.moodsmap.waterlogging.presentation.utils.NetUtils

/**
 * Created by Ray on 2017/12/29.
 */
class WebViewAgent  {

    private var mWebView:WebView?=null
    fun onLifecycleChanged(event: Lifecycle.Event) {
        when(event){
            Lifecycle.Event.ON_PAUSE->{
                mWebView?.onPause()
            }
            Lifecycle.Event.ON_RESUME->{
                mWebView?.onResume()
            }
            Lifecycle.Event.ON_DESTROY->{
                mWebView?.destroy()
            }
            else->{}
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun setWebViewSetting(webView: WebView, mUrl: String) {
        this.mWebView=webView
        mWebView?.webViewClient = WebViewClient()
        mWebView?.webChromeClient = WebChromeClient()
        val msetting = mWebView?.settings
        msetting?.javaScriptEnabled = true

        val settings = mWebView?.settings
        settings?.javaScriptEnabled = true
        settings?.loadWithOverviewMode = true
        settings?.setAppCacheEnabled(true)
        settings?.domStorageEnabled = true
        settings?.databaseEnabled = true
        if (NetUtils.checkNetWork(App.instance)) {
            settings?.cacheMode = WebSettings.LOAD_DEFAULT
        } else {
            settings?.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
        settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings?.setSupportZoom(true)
        mWebView?.webChromeClient = object :WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                    callBack?.onWebLoadProgress(newProgress)
            }
        }
        mWebView?.webViewClient = object :WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                Log.v("LoveClient1", url)
                return if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url)
                    false
                } else {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        mWebView?.context?.startActivity(intent)
                    }catch (e: ActivityNotFoundException){
                        e.printStackTrace()
                    }
                    true
                }
            }

        }
        mWebView?.loadUrl(mUrl)
    }

    var callBack: WebAgentCallBack?=null
    interface WebAgentCallBack{
        fun onWebLoadProgress(progress:Int)
    }


}
