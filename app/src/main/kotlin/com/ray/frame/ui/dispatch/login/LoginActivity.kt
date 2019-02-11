package com.ray.frame.ui.dispatch.login

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.method.DigitsKeyListener
import com.ray.frame.R
import com.ray.frame.data.AppConst
import com.ray.frame.presentation.base_mvp.base.BaseActivity
import com.ray.frame.presentation.kotlinx.extensions.onClick
import com.ray.frame.presentation.simple.SimpleTextWatcher
import com.ray.frame.presentation.utils.SPUtils
import com.ray.frame.view.edittext.MyEditText
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject


class LoginActivity : BaseActivity<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {

    @Inject
    lateinit var loginPresenter: LoginPresenter
    override fun injectDependencies() {
        activityComponent.inject(this)
    }

    override fun initPresenter()=loginPresenter

    companion object {
        const val ACCOUT_MIN_LENGTH=6
        const val PSW_MIN_LENGTH=8
    }
    private var isAccoutEnable=false
    private var isPswEnable=false
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)
        super.onCreate(savedInstanceState)
        initView()
    }

    /**
     * 账号长度6-20
     * 密码长度8-20
     */
    private fun initView(){
        login_accout.inputType = InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
        login_accout.keyListener = DigitsKeyListener.getInstance(MyEditText.INVITE_CODE_INPUT)
        login_accout.addTextChangedListener(object : SimpleTextWatcher(){
            override fun afterTextChanged(s: Editable) {
                super.afterTextChanged(s)
                isAccoutEnable=login_accout.text.toString().length>= ACCOUT_MIN_LENGTH
                login_btn.isEnabled=isAccoutEnable&&isPswEnable
            }
        })

        login_psw.addTextChangedListener(object :SimpleTextWatcher(){
            override fun afterTextChanged(s: Editable) {
                super.afterTextChanged(s)
                isPswEnable=login_psw.text.toString().length>= PSW_MIN_LENGTH
                login_btn.isEnabled=isAccoutEnable&&isPswEnable
            }
        })
        login_accout.setText(SPUtils.get(AppConst.SPKey.LAST_LOGIN_ACCOUNT,"").toString())
        login_psw.setText(SPUtils.get(AppConst.SPKey.LAST_LOGIN_PSW,"").toString())
        login_accout.setSelection(login_accout.text.length)
        login_btn.onClick {
            submit()
        }
    }

    private fun submit(){
        val accout=login_accout.text.toString()
        val psw=login_psw.text.toString()
        presenter.requestLoginByPsw(accout,psw)
    }

    override fun onLoginSucceed(info: Any) {

    }

    override fun onLoginFailed(msg: String) {
    }


}
