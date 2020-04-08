package com.example.music.player.view

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Size
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.music.player.R
import com.example.music.player.data.ContentResolverDSImpl
import com.example.music.player.data.SongsRepositoryImpl
import com.example.music.player.model.entity.Song
import com.example.music.player.model.songs.SongsInteractorImpl
import com.example.music.player.view.presenter.SongsPresenter
import com.example.music.player.view.presenter.SongsPresenterImpl
import com.example.music.player.view.presenter.SongsView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileNotFoundException


class MainActivity : Activity(), SongsView{

    private lateinit var presenter: SongsPresenter


    companion object {
        private const val READ_EXTERNAL_STORAGE_REQUEST = 1
        const val IMAGE_URI =
            "https://honestlywtf.com/wp-content/uploads/2019/05/driedflowers22.jpg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = SongsPresenterImpl(SongsInteractorImpl(SongsRepositoryImpl(ContentResolverDSImpl(contentResolver))))
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_EXTERNAL_STORAGE_REQUEST)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_EXTERNAL_STORAGE_REQUEST
                )
            }
        } else {
            presenter.getSongs()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                    presenter.getSongs()
                }
            } else {
                Toast.makeText(this, "No permission granted", Toast.LENGTH_SHORT).show()
                finish()
            }
            return
        }
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
