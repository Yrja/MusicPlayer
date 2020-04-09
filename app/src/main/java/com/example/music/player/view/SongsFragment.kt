package com.example.music.player.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.music.player.R
import com.example.music.player.data.ContentResolverDSImpl
import com.example.music.player.data.SongsRepositoryImpl
import com.example.music.player.model.entity.Song
import com.example.music.player.model.songs.SongsInteractorImpl
import com.example.music.player.view.presenter.SongsPresenter
import com.example.music.player.view.presenter.SongsPresenterImpl
import com.example.music.player.view.presenter.SongsView
import kotlinx.android.synthetic.main.songs_fragment.*

class SongsFragment private constructor() : Fragment(), SongsView {

    private lateinit var presenter: SongsPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.songs_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SongsPresenterImpl(
            SongsInteractorImpl(
                SongsRepositoryImpl(
                    ContentResolverDSImpl(activity!!.contentResolver)
                )
            )
        )
        presenter.getSongs()
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }

    companion object {
        const val IMAGE_URI =
            "https://honestlywtf.com/wp-content/uploads/2019/05/driedflowers22.jpg"

        fun getInstance(): SongsFragment {
            return SongsFragment()
        }
    }

    override fun displaySongs(songs: List<Song>) {
        val adapter = SongAdapter(songs)
        vSongsList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        vSongsList.adapter = adapter
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }


    override fun showError(error: Throwable?) {
        Toast.makeText(activity, error?.message ?: "Uploading songs failed!", Toast.LENGTH_SHORT)
            .show()
    }
}