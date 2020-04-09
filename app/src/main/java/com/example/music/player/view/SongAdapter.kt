package com.example.music.player.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.music.player.R
import com.example.music.player.model.entity.Song
import com.example.music.player.view.SongsFragment.Companion.IMAGE_URI

class SongAdapter(private val songsList: List<Song>) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return SongViewHolder(view, parent.context)
    }

    override fun getItemCount(): Int {
        return songsList.size
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): Song {
        return songsList[position]
    }

    class SongViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val songName = itemView.findViewById<TextView>(R.id.vSongName)
        private val songArtist = itemView.findViewById<TextView>(R.id.vSongArtist)
        private val songImage = itemView.findViewById<ImageView>(R.id.vSongImage)

        fun bind(song: Song) {
            songName.text = song.songName
            songArtist.text = song.artistName
            Glide.with(context)
                .load(song.imageUrl ?: song.imageBitmap ?: IMAGE_URI)
                .centerCrop()
                .into(songImage)


        }

    }

}