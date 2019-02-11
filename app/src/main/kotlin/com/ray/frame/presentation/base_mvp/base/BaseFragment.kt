package com.ray.frame.presentation.base_mvp.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ray.frame.presentation.rxutil.RxBus
import com.ray.frame.R
import com.ray.frame.data.domain.fetcher.DealErrorType
import com.ray.frame.di.component.ActivityComponent
import com.ray.frame.presentation.navigation.BackStrategy
import com.ray.frame.presentation.navigation.Navigator
import com.ray.frame.presentation.utils.IntentUtils
import com.ray.frame.presentation.utils.ViewUtils
import com.ray.frame.presentation.kotlinx.extensions.emptyString
import com.ray.frame.presentation.kotlinx.extensions.get
import com.ray.frame.presentation.kotlinx.extensions.onClick
import com.ray.frame.presentation.kotlinx.extensions.showToast
import com.ray.frame.presentation.widget.impl.LayoutAnim
import com.ray.frame.view.dialog.LoadingDialog
import com.ray.frame.view.dialog.MaterialDialog
import com.ray.frame.view.viewhelper.VaryViewHelper
import io.armcha.arch.BaseMVPFragment
import javax.inject.Inject

/**
 */
abstract class BaseFragment<V : BaseContract.View, P : BaseContract.Presenter<V>> : BaseMVPFragment<V, P>() , VaryViewHelper.NetWorkErrorListener{
    protected val TAG = javaClass.canonicalName

    @Inject
    lateinit var navigator: Navigator

    private var isViewPrepared: Boolean = false // 标识fragment视图已经初始化完毕
    protected var hasFetchData: Boolean = false // 标识已经触发过懒加载数据

    protected lateinit var activityComponent: ActivityComponent
    protected lateinit var activity: BaseActivity<*, *>

    private var loadingDialog: LoadingDialog? = null
    @Inject
    lateinit var layoutAnim: LayoutAnim
    protected var mVaryViewHelper: VaryViewHelper? = null

    private var isFunUserVisibleHint=false;
    @SuppressLint("MissingSuperCall")
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.activity = context
            activityComponent = activity.activityComponent
            injectDependencies()
            RxBus.register(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isFunUserVisibleHint=true
        lazyFetchDataIfPrepared()
    }

    override fun onResume() {
        super.onResume()
        if(!isFunUserVisibleHint)
        layoutAnim.start()
    }


    inline fun <reified T : Fragment> goTo(arg: Bundle) {
        navigator.goTo<T>(keepState = true, withCustomAnimation = false, arg = arg)
    }

    inline fun <reified T : Fragment> goTo(keepState: Boolean = true,
                                           withCustomAnimation: Boolean = false,
                                           arg: Bundle = Bundle.EMPTY) {
        navigator.goTo<T>(keepState = keepState, withCustomAnimation = withCustomAnimation, arg = arg)
    }
    inline fun <reified T : Fragment> goTo(keepState: Boolean = true,
                                           withCustomAnimation: Boolean = false,
                                           arg: Bundle = Bundle.EMPTY,backStrategy: BackStrategy = BackStrategy.DESTROY) {
        navigator.goTo<T>(keepState = keepState, withCustomAnimation = withCustomAnimation, arg = arg,backStrategy=backStrategy)
    }

    protected abstract fun injectDependencies()

    protected abstract val layoutResId: Int

    open protected fun lazyFetchData(){}

    private fun lazyFetchDataIfPrepared() {
        if (userVisibleHint && !hasFetchData && isViewPrepared) {
            layoutAnim.start()
            hasFetchData = true
            lazyFetchData()
        }
    }

    open fun getTitle(): String = emptyString

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepared = true
//        lazyFetchDataIfPrepared()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopLoadingDialog()
        hasFetchData = false
        isViewPrepared = false
        RxBus.unregister(this)
        layoutAnim.clear()
        mVaryViewHelper?.releaseVaryView()
    }


    protected fun initHelperView() {
        initHelperView(ViewUtils.getRootView(activity))
    }

    //设置覆盖加载页面 网络错误页面
    protected fun initHelperView(bindView: View) {
        mVaryViewHelper = VaryViewHelper.Builder()
                .setDataView(bindView)//放数据的父布局，逻辑处理在该Activity中处理
                .setLoadingView(LayoutInflater.from(activity).inflate(R.layout.view_loading, null))//加载页，无实际逻辑处理
                .setEmptyView(LayoutInflater.from(activity).inflate(R.layout.view_empty, null))//空页面，无实际逻辑处理
                .setErrorView(LayoutInflater.from(activity).inflate(R.layout.view_error, null))//错误页面
                .setNetWorkErrorView(LayoutInflater.from(activity).inflate(R.layout.view_neterror, null))//网络错误页
                .setRefreshListener(this)//错误页点击刷新实现
                .build()
    }

    //展示网络错误页
    fun showNetWorkErrorView(dealErrorType: DealErrorType) {
        stopLoadingDialog()
        when(dealErrorType){
            DealErrorType.DIALOG->{
                activity.showDialog(get(R.string.net_error),get(R.string.setting),{
                    IntentUtils.openWifi(activity)
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

    //展示服务器错误页
    fun showErrorView(dealErrorType: DealErrorType) {
        stopLoadingDialog()
        mVaryViewHelper?.showErrorView()
    }

    open fun showErrorView(str:String){
        showDialog(str)
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

    //打开设置网络
    override fun onSettingNetWork() {
        IntentUtils.openWifi(activity)
    }

    //刷新覆盖
    override fun reTry() {

    }


    fun showDialog(message: String, type: MaterialDialog.DialogType=MaterialDialog.DialogType.ERROR) {
        activity.showDialog(message, type)
    }

    fun showDialog(message: String,btnText:String,action: MaterialDialog.() -> Unit) {
        activity.showDialog(message, btnText,action)
    }

    fun showDialog(title:String="",message: String,btnText:String,action: MaterialDialog.() -> Unit, btnText2:String,action2: MaterialDialog.() -> Unit) {
        activity.showDialog(title,message, btnText,action,btnText2,action2)
    }

    fun showLoadingDialog(canCancle:Boolean=true) {
        if (loadingDialog == null)
            loadingDialog = LoadingDialog(context)
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

}