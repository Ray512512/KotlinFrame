package com.moodsmap.waterlogging.ui.dispatch

import android.Manifest
import android.os.Bundle
import com.moodsmap.waterlogging.R
import com.moodsmap.waterlogging.data.api.domain.cache.User
import com.moodsmap.waterlogging.presentation.base_mvp.base.BaseActivity
import com.moodsmap.waterlogging.presentation.utils.extensions.start
import com.moodsmap.waterlogging.ui.dispatch.login.LoginFragment
import com.moodsmap.waterlogging.ui.main.MainActivity
import pl.tajchert.nammu.Nammu
import pl.tajchert.nammu.PermissionCallback
import pl.tajchert.nammu.PermissionListener
import javax.inject.Inject

class DispatchActivity : BaseActivity<DispatchContract.View, DispatchContract.Presenter>(), DispatchContract.View {

    @Inject
    protected lateinit var dispatchPresenter: DispatchPresenter

    override fun injectDependencies() {
        activityComponent.inject(this)
    }

    override fun initPresenter() = dispatchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)
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
        if (!User.instance.isLogin()){
            openLoginActivity()
            return
        }
        start<MainActivity>()
        finish()
    }

    override fun openLoginActivity() {
//        supportFragmentManager.beginTransaction().add(,LoginFragment()).commit()
        goTo<LoginFragment>(keepState = true,
        withCustomAnimation = true,
        arg = Bundle.EMPTY)
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
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION)
}
