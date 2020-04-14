package com.example.music.player.view.image_helper

import android.graphics.Bitmap
import android.widget.ImageView

interface ImageLoader {

    fun uploadImage(imageBitmap: Bitmap?, imagePlaceholder: Int, view: ImageView)
    fun uploadImage(imageUri: String?, imagePlaceholder: Int, view: ImageView)
}