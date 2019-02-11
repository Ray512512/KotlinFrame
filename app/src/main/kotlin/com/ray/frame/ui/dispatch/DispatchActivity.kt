package com.ray.frame.ui.dispatch

import android.Manifest
import android.os.Bundle
import com.ray.frame.R
import com.ray.frame.data.domain.entity.User
import com.ray.frame.presentation.base_mvp.base.BaseActivity
import com.ray.frame.presentation.kotlinx.extensions.krealmextensions.queryFirst
import com.ray.frame.presentation.kotlinx.extensions.start
import com.ray.frame.ui.dispatch.login.LoginActivity
import com.ray.frame.ui.main.MainActivity
import pl.tajchert.nammu.Nammu
import pl.tajchert.nammu.PermissionCallback
import pl.tajchert.nammu.PermissionListener
import javax.inject.Inject

class DispatchActivity : BaseActivity<DispatchContract.View, DispatchContract.Presenter>(), DispatchContract.View {

    /**
     * 以下三个方法或字段为该mvp框架每个activity或fragment必须实现父类的抽象对象
     * 此处注释说明后其他界面不再重复注释
     * mvp 相关实现类皆在DispatchActivity 关联处注释一次  contact ， presenter
     * contact层方法注释后其他实际实现处不会再重复注释
     */

    /**
     * p层对象
     */
    @Inject
    protected lateinit var dispatchPresenter: DispatchPresenter

    /**
     * 注入dragger2
     */
    override fun injectDependencies() {
        activityComponent.inject(this)
    }

    /**
     * 初始化p层
     */
    override fun initPresenter() = dispatchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)
        User().queryFirst()?.let {
            User.instance = it
        }
        initPermission()
    }

    override fun initPermission() {
        Nammu.init(applicationContext)
        if (hasDeniedPermissions())
            requestPermission()
        else
            openHomeActivity()
    }

    private fun hasDeniedPermissions(): Boolean {
        return  NEEDED_PERMISSIONS.any { !Nammu.checkPermission(it) || Nammu.shouldShowRequestPermissionRationale(this, it) }
    }

    override fun requestPermission() {
        Nammu.askForPermission(this@DispatchActivity, NEEDED_PERMISSIONS, permissionCallback)
    }

    override fun openHomeActivity() {
        if (!User.needLogin()){
            openLoginActivity()
            return
        }
        start<MainActivity>()
        finish()
    }

    override fun openLoginActivity() {
//        supportFragmentManager.beginTransaction().add(,LoginFragment()).commit()
       start<LoginActivity>()
    }

    override fun onResume() {
        super.onResume()
        Nammu.permissionCompare(object : PermissionListener {
            override fun permissionsChanged(permissionRevoke: String) {
            }

            override fun permissionsGranted(permissionGranted: String) {
            }

            override fun permissionsRemoved(permissionRemoved: String) {
            }
        })
    }


    private val permissionCallback = object : PermissionCallback {
        override fun permissionGranted() {
            openHomeActivity()
        }

        override fun permissionRefused() {
            openHomeActivity()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private var NEEDED_PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE)
//            Manifest.permission.ACCESS_WIFI_STATE,
//            Manifest.permission.ACCESS_COARSE_LOCATION)
}
