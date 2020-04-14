package com.example.music.player.view

import androidx.recyclerview.widget.DiffUtil
import com.example.music.player.model.entity.Song

class SongsDiffUtilCallback(private val oldList: List<Song>, private val newList: List<Song>) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSong = oldList[oldItemPosition]
        val newSong = newList[newItemPosition]
        return oldSong.songName == newSong.songName
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSong = oldList[oldItemPosition]
        val newSong = newList[newItemPosition]
        return oldSong.songName == newSong.songName &&
                oldSong.artistName == newSong.artistName &&
                oldSong.imageBitmap == newSong.imageBitmap &&
                oldSong.imageUrl == newSong.imageUrl
    }
}