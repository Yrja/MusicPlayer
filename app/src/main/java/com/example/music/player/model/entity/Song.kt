package com.example.music.player.model.entity

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song(
    val artistName: String,
    val songName: String,
    val imageUrl: String?,
    val imageBitmap: Bitmap?,
    val songContentUri: Uri
):Parcelable