package com.example.music.player.model.entity

import android.graphics.Bitmap

data class Song(
    val artistName: String,
    val songName: String,
    val imageUrl: String?,
    val imageBitmap: Bitmap?

)