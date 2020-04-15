package com.example.music.player.view.songs

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
        return oldSong == newSong
    }
}