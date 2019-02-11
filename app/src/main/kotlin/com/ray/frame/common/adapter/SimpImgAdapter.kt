package com.ray.frame.common.adapter

import android.app.Activity
import com.ray.frame.R
import com.ray.frame.common.adapter.base.BaseAdapterHelper
import com.ray.frame.presentation.kotlinx.extensions.onClick
import com.ray.frame.presentation.kotlinx.extensions.show
import com.ray.frame.presentation.kotlinx.glide.GlideUtils
import com.ray.frame.presentation.utils.SizeUtils
import com.ray.frame.presentation.widget.pic.LookBigPicManager

/**
 * Created by Ray on 2018/9/26.
 * 简单图片列表适配器
 */
class SimpImgAdapter constructor(var activity: Activity,var isAdd:Boolean, var data: List<String> = ArrayList(),var cllback:ImgClick?=null,var radius:Int=0) :
        RecyleAdapter<String>(activity, R.layout.simple_img_item, data){

    /**
     * 点击图片
     */
    interface ImgClick{
        fun ImgItemClick(pos:Int)
    }

    override fun convert(helper: BaseAdapterHelper, item: String, position: Int) {
        val img = helper.getImageView(R.id.simple_item_img)
        img.onClick {
            if(item.isNotEmpty())
            LookBigPicManager.getInstance().lookBigPic(activity,position,data as ArrayList<String>,img)
            cllback?.ImgItemClick(position)
        }
        if(isAdd){
            val img_delete = helper.getImageView(R.id.img_delete)
            img_delete.show(item.isNotEmpty())
            img_delete.onClick {
                mData.remove(item)
                notifyDataSetChanged()
            }
            if(item.isNotEmpty()){
                GlideUtils.loadRound(activity,item,img, SizeUtils.dp2px(context,4F))
            }else{
                img.setImageResource(R.mipmap.ic_add_pic)
            }
        }else{
            GlideUtils.loadRound(activity,item,img,radius)
        }
    }
}