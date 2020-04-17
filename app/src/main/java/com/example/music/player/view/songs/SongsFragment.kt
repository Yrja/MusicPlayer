package com.example.music.player.view.songs

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.music.player.R
import com.example.music.player.model.entity.Song
import com.example.music.player.view.NavigationRouter
import com.example.music.player.view.image_helper.ImageLoader
import com.example.music.player.view.permission_helper.*
import com.example.music.player.view.presenter.BaseFragment
import com.example.music.player.view.presenter.songs.SongsPresenter
import com.example.music.player.view.presenter.songs.SongsView
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.songs_fragment.*
import javax.inject.Inject

class SongsFragment private constructor() : SongsView, BaseFragment() {
    @Inject
    lateinit var presenter: SongsPresenter

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var permissionsListener: PermissionListener

    @Inject
    lateinit var permissionsStream: PermissionsStream

    @Inject
    lateinit var requestPermissionsBinder: RequestPermissionsBinder
    private lateinit var songsAdapter: SongAdapter
    private val compositeDisposable = CompositeDisposable()

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

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkWorkingConditions()
        songsAdapter =
            SongAdapter(imageLoader) {
                if (activity is NavigationRouter)
                    (activity as NavigationRouter).navigateToSongPlayer(it)
            }
        vSongsList.apply {
            layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = songsAdapter
        }
    }

    private fun checkWorkingConditions() {
        val disposable =
            requestPermissionsBinder.requestPermission(arrayListOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                .subscribe({
                    presenter.getSongs()
                }, {
                    if (activity is NavigationRouter)
                        (activity as NavigationRouter).navigateToPermissionFragment()
                })
        compositeDisposable.add(disposable)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsStream.onPermissionResult(
            PermissionResult(
                requestCode,
                permissions,
                grantResults
            )
        )
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
        compositeDisposable.clear()
    }

    override fun displaySongs(songs: List<Song>) {
        songsAdapter.songsList = songs
    }

    override fun showLoading() {
        vProgressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        vProgressBar.visibility = View.GONE
    }

    override fun showError(error: Throwable?) {
        Toast.makeText(
                activity,
                error?.localizedMessage ?: getString(R.string.uploading_songs_msg_error),
                Toast.LENGTH_SHORT
            )
            .show()
    }

    companion object {
        fun getInstance(): SongsFragment {
            return SongsFragment()
        }
    }
}