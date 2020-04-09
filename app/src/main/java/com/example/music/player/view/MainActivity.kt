package com.example.music.player.view

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.music.player.R
import com.example.music.player.data.ContentResolverDSImpl
import com.example.music.player.data.SongsRepositoryImpl
import com.example.music.player.model.entity.Song
import com.example.music.player.model.songs.SongsInteractorImpl
import com.example.music.player.view.permission_helper.GetPermissionBinder
import com.example.music.player.view.permission_helper.MutablePermissionsStream
import com.example.music.player.view.permission_helper.PermissionResult
import com.example.music.player.view.presenter.SongsPresenter
import com.example.music.player.view.presenter.SongsPresenterImpl
import com.example.music.player.view.presenter.SongsView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity(), SongsView{

    private lateinit var presenter: SongsPresenter
    private val permissionsStream = MutablePermissionsStream()
    private val takeAudiosBinder =
        GetPermissionBinder(this, permissionsStream)
    private val compositeDisposable = CompositeDisposable()

    companion object {
        const val IMAGE_URI =
            "https://honestlywtf.com/wp-content/uploads/2019/05/driedflowers22.jpg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = SongsPresenterImpl(SongsInteractorImpl(SongsRepositoryImpl(ContentResolverDSImpl(contentResolver))))
       val disposable = takeAudiosBinder.getPermissionResult(arrayListOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            .subscribe({
                presenter.getSongs()
            },{
                Toast.makeText(this, "No permission granted", Toast.LENGTH_SHORT).show()
            })
        compositeDisposable.add(disposable)
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
        if (!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
            compositeDisposable.clear()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        permissionsStream.onPermissionResult(
            PermissionResult(
                requestCode,
                permissions,
                grantResults
            )
        )
    }

    override fun displaySongs(songs: List<Song>) {
        val adapter = SongAdapter(songs)
        vSongsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        vSongsList.adapter = adapter    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(error: Throwable?) {
            Toast.makeText(this,error?.message?:"Uploading songs failed!",Toast.LENGTH_SHORT).show()
    }


}
