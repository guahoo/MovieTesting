package com.test_task.movie_test.extensions

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.test_task.movie_test.R


fun ImageView.loadGlide(path: Any?) {
        Glide.with(this).load(path).into(this)
    }


fun ImageView.cornerImage(path: Any?, cornerSize: Int = 0) {
    if (path == null) return
    Glide.with(this)
        .setDefaultRequestOptions(
            RequestOptions().transform(RoundedCorners(cornerSize))
        )
        .load(path)
        .addListener(object : RequestListener<Drawable> {
            @SuppressLint("UseCompatLoadingForDrawables")
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Handler(Looper.getMainLooper()).post {

                    Glide.with(context)
                        .load(context.getDrawable(R.drawable.ic_empty_grand_image_ph))
                        .into(this@cornerImage)
                }
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        })
        .into(this)
}





