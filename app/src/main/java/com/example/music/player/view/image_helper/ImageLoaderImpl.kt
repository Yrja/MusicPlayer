package com.example.music.player.view.image_helper

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageLoaderImpl : ImageLoader {

    override fun uploadImage(imageBitmap: Bitmap?, imagePlaceholder: Int, view: ImageView) {
        Glide.with(view)
            .load(imageBitmap ?: imagePlaceholder)
            .centerCrop()
            .into(view)
    }

    override fun uploadImage(imageUri: String?, imagePlaceholder: Int, view: ImageView) {
        Glide.with(view)
            .load(imageUri ?: imagePlaceholder)
            .centerCrop()
            .into(view)
    }

    override fun uploadImage(imageResource: Int, view: ImageView) {
        Glide.with(view)
            .load(imageResource)
            .centerCrop()
            .into(view)
    }
}