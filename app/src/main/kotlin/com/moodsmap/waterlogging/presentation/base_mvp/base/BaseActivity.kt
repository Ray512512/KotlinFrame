package com.moodsmap.waterlogging.presentation.base_mvp.base

import android.app.Dialog
import android.content.Context
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
import android.widget.TextView
import com.moodsmap.waterlogging.App
import com.moodsmap.waterlogging.R
import com.moodsmap.waterlogging.di.component.ActivityComponent
import com.moodsmap.waterlogging.di.module.ActivityModule
import com.moodsmap.waterlogging.presentation.navigation.Navigator
import com.moodsmap.waterlogging.presentation.rxutil.RxBus
import com.moodsmap.waterlogging.presentation.utils.LayoutAnim
import com.moodsmap.waterlogging.presentation.utils.S
import com.moodsmap.waterlogging.presentation.utils.extensions.emptyString
import com.moodsmap.waterlogging.presentation.utils.extensions.getStatusBarHeight
import com.moodsmap.waterlogging.presentation.utils.extensions.unSafeLazy
import com.moodsmap.waterlogging.presentation.widget.common.LoadingDialog
import com.moodsmap.waterlogging.presentation.widget.common.MaterialDialog
import io.armcha.arch.BaseMVPActivity
import javax.inject.Inject

abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>>
    : BaseMVPActivity<V, P>(), Navigator.FragmentChangeListener {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var inflater: LayoutInflater

    @Inject
    lateinit var layoutAnim: LayoutAnim

    @Inject
    lateinit var rxBus: RxBus

    private var dialog: Dialog? = null
    private var loadingDialog: LoadingDialog? = null

    val activityComponent: ActivityComponent by unSafeLazy {
        getAppComponent().plus(ActivityModule(this))
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        navigator.fragmentChangeListener = this
        rxBus.register(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        layoutAnim.start()
    }

    @CallSuper
    override fun onDestroy() {
        dialog?.dismiss()
        stopLoadingDialog()
        rxBus.unregister(this)
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

    fun showLoadingDialog() {
        if (loadingDialog == null)
            loadingDialog = LoadingDialog(this)
        loadingDialog?.show()
    }

    fun showLoadingDialog(message: String) {
        if (loadingDialog == null)
            loadingDialog = LoadingDialog(this)
        loadingDialog?.show()
        loadingDialog?.let { (it.findViewById(R.id.tv_msg) as TextView).text = message }
    }

    fun stopLoadingDialog() {
        loadingDialog?.let { if (it.isShowing) it.dismiss() }
    }

}
