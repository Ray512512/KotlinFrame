package com.moodsmap.waterlogging.presentation.base_mvp.base

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.moodsmap.waterlogging.presentation.rxutil.RxBus
import com.moodsmap.waterlogging.App
import com.moodsmap.waterlogging.R
import com.moodsmap.waterlogging.data.domain.fetcher.DealErrorType
import com.moodsmap.waterlogging.di.component.ActivityComponent
import com.moodsmap.waterlogging.di.module.ActivityModule
import com.moodsmap.waterlogging.presentation.navigation.BackStrategy
import com.moodsmap.waterlogging.presentation.navigation.Navigator
import com.moodsmap.waterlogging.presentation.utils.*
import com.moodsmap.waterlogging.presentation.kotlinx.extensions.*
import com.moodsmap.waterlogging.presentation.widget.impl.LayoutAnim
import com.moodsmap.waterlogging.view.dialog.LoadingDialog
import com.moodsmap.waterlogging.view.dialog.MaterialDialog
import com.moodsmap.waterlogging.view.viewhelper.VaryViewHelper
import io.armcha.arch.BaseMVPActivity
import util.UpdateAppUtils.EXITACTION
import javax.inject.Inject

abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>>
    : BaseMVPActivity<V, P>(), Navigator.FragmentChangeListener, VaryViewHelper.NetWorkErrorListener {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var inflater: LayoutInflater

    @Inject
    lateinit var layoutAnim: LayoutAnim

    private var dialog: Dialog? = null
    private var loadingDialog: LoadingDialog? = null

    val activityComponent: ActivityComponent by unSafeLazy {
        getAppComponent().plus(ActivityModule(this))
    }
    protected val tvTitle:TextView by unSafeLazy {
        findViewById<TextView>(R.id.tv_title)
    }

    protected val imgBack: ImageView by unSafeLazy {
        findViewById<ImageView>(R.id.img_back)
    }

    protected val imgRightBtn: ImageView by unSafeLazy {
        findViewById<ImageView>(R.id.img_action)
    }

    protected var mVaryViewHelper: VaryViewHelper? = null


    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        StatusBarUtils.setStatusBarBg(this)
        transparentStatusBar()
        injectDependencies()
        navigator.fragmentChangeListener = this
        RxBus.register(this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        registExitReceiver()
        super.onCreate(savedInstanceState)
    }

    fun normalTitle(s:Int,b:Boolean=true){
        tvTitle.text=get(s)
        if(b)
            imgBack.onClick { finish() }
    }

    fun normalTitle(s:String,b:Boolean=true){
        tvTitle.text=s
        if(b)
            imgBack.onClick { finish() }
    }

    fun onlyTitle(s:Int){
        findViewById<TextView>(R.id.tv_title).text=get(s)
        findViewById<ImageView>(R.id.img_back).visibility=View.GONE
    }
    override fun onResume() {
        super.onResume()
        layoutAnim.start()
    }

    @CallSuper
    override fun onDestroy() {
        dialog?.dismiss()
        stopLoadingDialog()
        RxBus.unregister(this)
        layoutAnim.clear()
        mVaryViewHelper?.releaseVaryView()
        unregisterReceiver(exitReceiver)
        super.onDestroy()
    }

    @CallSuper
    override fun onBackPressed() {
        if (navigator.hasBackStack())
            navigator.goBack()
        else
            super.onBackPressed()
    }

    protected abstract fun injectDependencies()

    private fun getAppComponent() = App.instance.applicationComponent

    inline protected fun <reified T : Fragment> goTo(keepState: Boolean = true,
                                                     withCustomAnimation: Boolean = false,
                                                     arg: Bundle = Bundle.EMPTY) {
        navigator.goTo<T>(keepState = keepState,
                withCustomAnimation = withCustomAnimation,
                arg = arg)
    }


    inline fun <reified T : Fragment> goTo(arg: Bundle) {
        navigator.goTo<T>(keepState = false, withCustomAnimation = false, arg = arg)
    }

    inline fun <reified T : Fragment> goTo(keepState: Boolean = true,
                                           withCustomAnimation: Boolean = false,
                                           arg: Bundle = Bundle.EMPTY,backStrategy: BackStrategy = BackStrategy.DESTROY) {
        navigator.goTo<T>(keepState = keepState, withCustomAnimation = withCustomAnimation, arg = arg,backStrategy=backStrategy)
    }

    fun showDialog(title: String, message: String, buttonText: String = "Close") {
        dialog = MaterialDialog(this).apply {
            message(message)
                    .title(title)
                    .addPositiveButton(buttonText) {
                        hide()
                    }
                    .show()
        }
    }

    fun showErrorDialog(message: String?, buttonText: String = "Close") {
        showDialog(getString(S.error_title), message ?: emptyString, buttonText)
    }

    /**
     * 检测系统版本并使状态栏全透明
     */
    protected fun transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            //            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); // 新增滑动返回，舍弃过渡动效

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * 初始化Toolbar的功能
     */
    protected fun initializeToolbar() {
        transparentStatusBar()
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val params = toolbar.layoutParams as AppBarLayout.LayoutParams
        params.topMargin = getStatusBarHeight()
    }

    protected fun initializeToolbar(title: String) {
        transparentStatusBar()
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = title
        }

        val params = toolbar.layoutParams as AppBarLayout.LayoutParams
        params.topMargin = getStatusBarHeight()
    }

    fun getContext(): Context = this
    fun showDialog(message: String,type: MaterialDialog.DialogType=MaterialDialog.DialogType.ERROR) {
        if(message.isEmpty())return
        checkDialog()
        dialog = MaterialDialog(this).apply {
            type(type)
            message(message)
                    .show()
        }
    }
    fun showDialog(message: String,btnText:String,action: MaterialDialog.() -> Unit) {
        if(message.isEmpty())return
        checkDialog()
        dialog = MaterialDialog(this).apply {
            message(message)
            addPositiveButton(btnText,action)
            show()
        }
    }

    fun showDialog(message: String,btnText:String,action: MaterialDialog.() -> Unit,btnText2:String,action2: MaterialDialog.() -> Unit) {
        showDialog("",message,btnText,action,btnText2,action2)
    }
    fun showDialog(title:String="",message: String,btnText:String,action: MaterialDialog.() -> Unit,btnText2:String,action2: MaterialDialog.() -> Unit) {
        if(message.isEmpty())return
        checkDialog()
        dialog = MaterialDialog(this).apply {
            title(title)
            message(message)
            addPositiveButton(btnText,action)
            addNegativeButton(btnText2,action2)
            show()
        }
    }

    private fun checkDialog(){
        dialog?.let {
            if(it.isShowing){
                it.dismiss()
            }
        }
    }
    fun showLoadingDialog(canCancle:Boolean=true) {
//        showLoading()
        if (loadingDialog == null)
            loadingDialog = LoadingDialog(this)
        loadingDialog?.show()
        loadingDialog?.setCancelable(canCancle)
    }

    fun showLoadingDialog(message: String,canCancle:Boolean=true) {
        showLoadingDialog(canCancle)
        loadingDialog?.let { (it.findViewById(R.id.tv_msg) as TextView).text = message }
    }

    fun showLoadingDialog(message: Int,canCancle:Boolean=true) {
        showLoadingDialog(canCancle)
        loadingDialog?.let { (it.findViewById(R.id.tv_msg) as TextView).text = getString(message) }
    }

    fun stopLoadingDialog() {
        loadingDialog?.let { if (it.isShowing) it.dismiss() }
    }

    //展示服务器错误页
    fun showErrorView(dealErrorType: DealErrorType) {
        stopLoadingDialog()
        mVaryViewHelper?.showErrorView()
    }

    //展示空白页
    fun showEmptyView() {
        stopLoadingDialog()
        mVaryViewHelper?.showEmptyView()
    }

    //展示数据页
    fun showDataView() {
        stopLoadingDialog()
        mVaryViewHelper?.showDataView()
    }

    private val exitReceiver = ExitReceiver()
    private fun registExitReceiver(){
        val filter = IntentFilter()
        filter.addAction(EXITACTION)
        registerReceiver(exitReceiver, filter)
    }
    inner class ExitReceiver : BroadcastReceiver() {
        @Override
        override fun onReceive(context:Context, intent: Intent) {
            finish()
        }
    }

    protected fun initHelperView() {
        initHelperView(ViewUtils.getRootView(this))
    }

    //设置覆盖加载页面 网络错误页面
    protected fun initHelperView(bindView: View) {
        mVaryViewHelper = VaryViewHelper.Builder()
                .setDataView(bindView)//放数据的父布局，逻辑处理在该Activity中处理
                .setLoadingView(LayoutInflater.from(this).inflate(R.layout.view_loading, null))//加载页，无实际逻辑处理
                .setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty, null))//空页面，无实际逻辑处理
                .setErrorView(LayoutInflater.from(this).inflate(R.layout.view_error, null))//错误页面
                .setNetWorkErrorView(LayoutInflater.from(this).inflate(R.layout.view_neterror, null))//网络错误页
                .setRefreshListener(this)//错误页点击刷新实现
                .build()
    }

    override fun reTry() {

    }

    override fun onSettingNetWork() {
        IntentUtils.openWifi(this)
    }

    //展示网络错误页
    fun showNetWorkErrorView(dealErrorType: DealErrorType) {
        stopLoadingDialog()
        when(dealErrorType){
            DealErrorType.DIALOG->{
                showDialog(get(R.string.net_error),get(R.string.setting),{
                    IntentUtils.openWifi(this@BaseActivity)
                },get(R.string.cancel),{
                    dismiss()
                })
            }
            DealErrorType.LAYOUT->{
                mVaryViewHelper?.showNetWorkErrorView()
            }
            DealErrorType.TOAST->{
                showToast(get(R.string.net_error))
            }
        }
    }

}
