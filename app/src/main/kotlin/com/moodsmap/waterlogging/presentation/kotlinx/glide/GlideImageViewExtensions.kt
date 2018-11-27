package com.moodsmap.waterlogging.presentation.kotlinx.glide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.moodsmap.waterlogging.R
import com.moodsmap.waterlogging.presentation.utils.SizeUtils
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.util.*

/**
 */
private val DEFAULT_DURATION_MS = 200


fun ImageView.load(url: String) {
    load(this, url)
}

fun ImageView.load(url: String,radius: Float) {
    load(this, url, R.mipmap.ic_launcher,TransformationType.ROUND, SizeUtils.dp2px(context,radius))
}


fun ImageView.load(url: Objects) {
    load(this, url, R.mipmap.ic_launcher)
}

fun ImageView.load(url: Objects, transformationType: TransformationType) {
    load(this, url, R.mipmap.ic_launcher, transformationType)
}

fun ImageView.load(url: String, transformationType: TransformationType) {
    load(this, url,  R.mipmap.ic_launcher,transformationType)
}

fun ImageView.load(url: String, default:Int,transformationType: TransformationType) {
    load(this, url, default,transformationType)
}

@JvmName("privateLoad")
private fun load(view: ImageView,
                 url: Objects,
                 default:Int,
                 transformationType: TransformationType = TransformationType.NOTHING, radius: Int=0) {
    val glideRequest: GlideRequest<Drawable> = GlideApp.with(view.context)
            .load(url)
//            .placeholder(default)
            .transition(DrawableTransitionOptions.withCrossFade(DEFAULT_DURATION_MS))
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    if (transformationType != TransformationType.NOTHING) {
        glideRequest.transform(transformationType.getTransformation(radius))
    }
    glideRequest.into(view)
}


@JvmName("privateLoad")
private fun load(view: ImageView,
                 url: String,
                 default:Int= R.mipmap.ic_launcher,
                 transformationType: TransformationType = TransformationType.NOTHING,radius: Int=0) {
    val glideRequest: GlideRequest<Drawable> = GlideApp.with(view.context)
            .load(url)
//            .placeholder(default)
            .transition(DrawableTransitionOptions.withCrossFade(DEFAULT_DURATION_MS))
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    if (transformationType != TransformationType.NOTHING) {
        glideRequest.transform(transformationType.getTransformation(radius))
    }
    glideRequest.into(view)
}

fun ImageView.clear() {
    GlideApp.with(context).clear(this)
}

enum class TransformationType {
    CIRCLE,
    ROUND,
    NOTHING;

    fun getTransformation(radius:Int): Transformation<Bitmap> = when (this) {
        CIRCLE -> CircleCrop()
        ROUND -> MultiTransformation(CenterCrop(), RoundedCornersTransformation(radius, 0, RoundedCornersTransformation.CornerType.ALL))
        else ->  MultiTransformation(RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.ALL))
    }
}