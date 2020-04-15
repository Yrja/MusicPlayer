package com.example.music.player.view.song_player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.music.player.R
import com.example.music.player.model.entity.Song
import com.example.music.player.view.presenter.BaseFragment
import com.example.music.player.view.presenter.BaseView
import kotlinx.android.synthetic.main.song_player_fragment_layout.*

class SongPlayerFragment private constructor() : BaseFragment(), BaseView {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.song_player_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val song: Song? = arguments?.getParcelable(SONG_PARCELABLE_KEY)
        uri.text = song?.songContentUri.toString()
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showError(error: Throwable?) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val SONG_PARCELABLE_KEY = "SONG_PARCELABLE_KEY"

        fun getInstance(song: Song): SongPlayerFragment {
            val bundle = Bundle()
            bundle.putParcelable(SONG_PARCELABLE_KEY, song)
            val fragment = SongPlayerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}