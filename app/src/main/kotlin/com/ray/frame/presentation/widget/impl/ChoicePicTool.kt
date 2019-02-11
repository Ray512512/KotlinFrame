package com.ray.frame.presentation.widget.impl

import android.Manifest
import android.app.Activity
import com.luck.picture.lib.permissions.RxPermissions
import com.ray.frame.R
import com.ray.frame.presentation.utils.PermissionPageUtils
import com.ray.frame.view.dialog.MaterialDialog
import me.nereo.multi_image_selector.MultiImageSelector

/**
 * Created by Ray on 2018/7/23.
 */
object ChoicePicTool{
    @JvmField  val FILE_HEAD="file://"
    @JvmField  val CHOICE_PIC=1 //选图
    @JvmField  val TAKE_PHOTO=2 //拍照

    @JvmStatic fun choicePic(activity: Activity,showCarmra:Boolean=false){
        MultiImageSelector.create().single().onlyCamera(false).showCamera(showCarmra).start(activity, CHOICE_PIC)
    }

    @JvmStatic fun choiceMutiPic(activity: Activity,list:ArrayList<String>,size:Int){
        MultiImageSelector.create().count(size).origin(list).onlyCamera(false).showCamera(false).start(activity, CHOICE_PIC)
    }

    @JvmStatic fun choiceMutiPic(activity: Activity,size:Int){
        MultiImageSelector.create().count(size).onlyCamera(false).showCamera(false).start(activity, CHOICE_PIC)
    }

    @JvmStatic fun takePhoto(activity: Activity){
        RxPermissions(activity).request(Manifest.permission.CAMERA).subscribe { granted ->
            if (granted) {
                MultiImageSelector.create().single().onlyCamera(true).start(activity, TAKE_PHOTO)
            } else {
                MaterialDialog(activity).title(activity.getString(R.string.system_dialog_title)).message(activity.getString(R.string.photo_need_permission))
                        .addPositiveButton(activity.getString(R.string.btn_go),action = {
                            PermissionPageUtils(activity).jumpPermissionPage()
                            dismiss() }).show()
            }
        }
    }
}