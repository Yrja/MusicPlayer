package com.example.music.player.view.songs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.music.player.R
import com.example.music.player.model.entity.Song
import com.example.music.player.view.image_helper.ImageLoader
import kotlinx.android.synthetic.main.song_item.view.*

class SongAdapter(private val imageLoader: ImageLoader, private val listener: (song: Song) -> Unit) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    var songsList: List<Song> = ArrayList()
        set(value) {
            val songsDiffUtilCallback =
                SongsDiffUtilCallback(
                    songsList,
                    value
                )
            val songsDiffResult = DiffUtil.calculateDiff(songsDiffUtilCallback)
            field = value
            songsDiffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return SongViewHolder(
            view,
            imageLoader
        )
    }

    override fun getItemCount(): Int {
        return songsList.size
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            listener.invoke(item)
        }
        holder.bind(item)
    }

    private fun getItem(position: Int): Song {
        return songsList[position]
    }

    class SongViewHolder(itemView: View, private val imageLoader: ImageLoader) :
        RecyclerView.ViewHolder(itemView) {

        fun bind(song: Song) {
            itemView.apply {
                vSongName.text = song.songName
                vSongArtist.text = song.artistName
                imageLoader.apply {
                    song.imageUrl?.let {
                        uploadImage(song.imageUrl, R.drawable.music_placeholder, vSongImage)
                    } ?: uploadImage(song.imageBitmap, R.drawable.music_placeholder, vSongImage)
                }
            }
        }
    }
}