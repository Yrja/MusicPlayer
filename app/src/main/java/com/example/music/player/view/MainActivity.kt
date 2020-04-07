package com.example.music.player.view

import android.Manifest
import android.content.ContentResolver
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
import com.example.music.player.model.entity.Song
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileNotFoundException


class MainActivity : AppCompatActivity() {

    private val list = ArrayList<Song>()


    companion object {
        private const val READ_EXTERNAL_STORAGE_REQUEST = 1
        const val IMAGE_URI =
            "https://honestlywtf.com/wp-content/uploads/2019/05/driedflowers22.jpg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_EXTERNAL_STORAGE_REQUEST
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_EXTERNAL_STORAGE_REQUEST
                )
            }
        } else {
            doStuff()
        }
    }

    private fun getSongs() {
        val contentResolver = contentResolver
        val songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val songCursor = contentResolver.query(songUri, null, null, null, null)

        if (songCursor != null && songCursor.moveToFirst()) {
            val songTitle: Int = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val songArtist: Int = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val albumId: Int = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
            val albumName: Int = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)

            do {
                val currentSongAlbumId: Long = songCursor.getLong(albumId)
                val currentTitle: String = songCursor.getString(songTitle)
                val currentArtist: String = songCursor.getString(songArtist)
                val currentSongAlbumName: String = songCursor.getString(albumName)
                var songArtPath: String? = null
                var songThumbnail: Bitmap? = null
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    songThumbnail = require(currentSongAlbumName)
                } else {
                    songArtPath = requireAndroidQ(currentSongAlbumId)
                }

                list.add(
                    Song(
                        currentArtist,
                        currentTitle,
                        songArtPath,
                        songThumbnail
                    )
                )

            } while (songCursor.moveToNext())
            songCursor.close()
        }
    }

    private fun doStuff() {
        getSongs()
        val adapter = SongAdapter(list)
        vSongsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        vSongsList.adapter = adapter
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
                    doStuff()
                }
            } else {
                Toast.makeText(this, "No permission granted", Toast.LENGTH_SHORT).show()
                finish()
            }
            return
        }
    }

    private fun requireAndroidQ(currentSongAlbumId: Long): String? {
        val albumCursor = contentResolver.query(
            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM_ART
            ),
            MediaStore.Audio.Albums._ID + "=?",
            arrayOf(currentSongAlbumId.toString()),
            null
        )

        val path = if (albumCursor != null && albumCursor.moveToFirst()) {
            albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))
        } else {
            null
        }
        albumCursor?.close()
        return path
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun require(songAlbumName: String): Bitmap? {
        val albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
        var bitmap: Bitmap? = null
        val albumCursor: Cursor? = contentResolver.query(
            albumUri,
            null,
            null,
            null,
            null
        )
        albumCursor?.use { cursor ->
            val albumId: Int = cursor.getColumnIndex(MediaStore.Audio.Albums._ID)
            val albumName: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(albumId)
                val currentAlbumName: String = cursor.getString(albumName)
                if (currentAlbumName == songAlbumName) {
                    val contentUri: Uri = ContentUris.withAppendedId(albumUri, id)
                    try {
                        bitmap = contentResolver.loadThumbnail(contentUri, Size(640, 640), null)
                    } catch (ex: FileNotFoundException) {
                        ex.printStackTrace()
                    }
                    break
                }
            }
        }
        albumCursor?.close()
        return bitmap
    }

}
