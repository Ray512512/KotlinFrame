package com.moodsmap.waterlogging.ui.dispatch.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.moodsmap.waterlogging.R
import com.moodsmap.waterlogging.presentation.base_mvp.base.BaseFragment
import com.moodsmap.waterlogging.presentation.utils.extensions.onClick
import com.moodsmap.waterlogging.presentation.utils.extensions.start
import com.moodsmap.waterlogging.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : BaseFragment<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {

    override val layoutResId = R.layout.fragment_login

    @Inject
    protected lateinit var loginPresenter: LoginPresenter

    override fun injectDependencies() {
        activityComponent.inject(this)
    }

    override fun initPresenter() = loginPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login.onClick { presenter.requestLogin("","") }

    }

    override fun onLoginSucceed() {
        start<MainActivity>()
        getActivity()!!.finish()
    }

    override fun lazyFetchData() {
    }

    @SuppressLint("PrivateResource")
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
        if (enter) {
            return AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_in_bottom)
        } else {
            return AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_out_bottom)
        }
    }

}
