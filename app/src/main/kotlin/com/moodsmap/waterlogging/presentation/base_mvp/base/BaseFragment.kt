package com.moodsmap.waterlogging.presentation.base_mvp.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.moodsmap.waterlogging.R
import com.moodsmap.waterlogging.di.component.ActivityComponent
import com.moodsmap.waterlogging.presentation.navigation.Navigator
import com.moodsmap.waterlogging.presentation.rxutil.RxBus
import com.moodsmap.waterlogging.presentation.utils.LayoutAnim
import com.moodsmap.waterlogging.presentation.utils.extensions.emptyString
import com.moodsmap.waterlogging.presentation.widget.common.LoadingDialog
import io.armcha.arch.BaseMVPFragment
import javax.inject.Inject

/**
 */
abstract class BaseFragment<V : BaseContract.View, P : BaseContract.Presenter<V>> : BaseMVPFragment<V, P>() {

    @Inject
    lateinit var navigator: Navigator

    private var isViewPrepared: Boolean = false // 标识fragment视图已经初始化完毕
    private var hasFetchData: Boolean = false // 标识已经触发过懒加载数据

    protected lateinit var activityComponent: ActivityComponent
    protected lateinit var activity: BaseActivity<*, *>

    private var loadingDialog: LoadingDialog? = null
    @Inject
    lateinit var layoutAnim: LayoutAnim
    @Inject
    lateinit var rxBus: RxBus
    @SuppressLint("MissingSuperCall")
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            this.activity = context
            activityComponent = activity.activityComponent
            injectDependencies()
            rxBus.register(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            layoutAnim.start()
        }
    }

    inline fun <reified T : Fragment> goTo(keepState: Boolean = true,
                                           withCustomAnimation: Boolean = false,
                                           arg: Bundle = Bundle.EMPTY) {
        navigator.goTo<T>(keepState = keepState, withCustomAnimation = withCustomAnimation, arg = arg)
    }

    protected abstract fun injectDependencies()

    protected abstract val layoutResId: Int

    protected abstract fun lazyFetchData()

    private fun lazyFetchDataIfPrepared() {
        if (userVisibleHint && !hasFetchData && isViewPrepared) {
            hasFetchData = true
            lazyFetchData()
        }
    }


    open fun getTitle(): String = emptyString

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepared = true
        lazyFetchDataIfPrepared()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopLoadingDialog()
        hasFetchData = false
        isViewPrepared = false
        rxBus.unregister(this)
    }

    fun showDialog(title: String, message: String, buttonText: String = "Close") {
        activity.showDialog(title, message, buttonText)
    }

    fun showErrorDialog(message: String?, buttonText: String = "Close") {
        activity.showDialog(getString(R.string.error_title), message ?: emptyString, buttonText)
    }

    fun showLoadingDialog() {
        if (loadingDialog == null)
            loadingDialog = LoadingDialog(context)
        loadingDialog?.show()
    }

    fun showLoadingDialog(message: String) {
        if (loadingDialog == null)
            loadingDialog = LoadingDialog(context)
        loadingDialog?.show()
        loadingDialog?.let { (it.findViewById(R.id.tv_msg) as TextView).text = message }
    }

    fun stopLoadingDialog() {
        loadingDialog?.let { if (it.isShowing) it.dismiss() }
    }
}