package com.example.music.player.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.music.player.R
import com.example.music.player.model.entity.Song
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val list = ArrayList<Song>()


    companion object {
        private const val READ_EXTERNAL_STORAGE_REQUEST = 1
        private const val IMAGE_URI = "https://honestlywtf.com/wp-content/uploads/2019/05/driedflowers22.jpg"
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

            do {
                val currentTitle: String = songCursor.getString(songTitle)
                val currentArtist: String = songCursor.getString(songArtist)
                list.add(
                    Song(
                        currentArtist,
                        currentTitle,
                        IMAGE_URI
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
}
