package com.ray.frame.common.web

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
import com.ray.frame.App
import com.ray.frame.presentation.utils.NetUtils

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
    fun setWebViewSetting(webView: WebView) {
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
    }

    var callBack: WebAgentCallBack?=null
    interface WebAgentCallBack{
        fun onWebLoadProgress(progress:Int)
    }

    /**
     * 加载url内容
     */
    fun loadUrl(url:String){
        if(url.isNotEmpty())
            mWebView?.loadUrl(url)
    }

    /**
     * 加载标签内容
     */
    fun initAndLoad(webView: WebView,content: String){
        mWebView=webView
        mWebView?.loadDataWithBaseURL(null,
                getHtmlData(content), "text/html", "utf-8", null)
    }
    /**
     * 加载标签内容
     */
    fun loadDataWithBaseURL(content: String){
        mWebView?.loadDataWithBaseURL(null,
                getHtmlData(content), "text/html", "utf-8", null)
    }

    /**
     * 加载html标签
     *
     * @param bodyHTML
     * @return
     */
    private fun getHtmlData(bodyHTML: String): String {
        val head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto!important;}</style>" +
                "</head>"
        return "<html>$head<body>$bodyHTML</body></html>"
    }

}
