package com.moodsmap.waterlogging.presentation.widget.impl

import android.Manifest
import android.app.Activity
import com.luck.picture.lib.permissions.RxPermissions
import com.moodsmap.waterlogging.R
import com.moodsmap.waterlogging.presentation.utils.PermissionPageUtils
import com.moodsmap.waterlogging.view.dialog.MaterialDialog
import me.nereo.multi_image_selector.MultiImageSelector

/**
 * Created by Ray on 2018/7/23.
 */
object ChoicePicTool{
    @JvmField  val FILE_HEAD="file://"
    @JvmField  val CHOICE_PIC=1 //选图
    @JvmField  val TAKE_PHOTO=2 //拍照

    @JvmStatic fun choicePic(activity: Activity){
        MultiImageSelector.create().single().onlyCamera(false).showCamera(false).start(activity, CHOICE_PIC)
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