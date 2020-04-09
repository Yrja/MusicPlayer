package com.example.music.player.data

import android.content.ContentResolver
import android.content.ContentUris
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import androidx.annotation.RequiresApi
import com.example.music.player.model.entity.Song
import io.reactivex.Single
import java.io.FileNotFoundException
import javax.inject.Inject

class ContentResolverDSImpl @Inject constructor(var contentResolver: ContentResolver) : SongsDataSource {
    override fun getSongs(): Single<List<Song>> {
        return Single.fromCallable {
            val songsList = ArrayList<Song>()
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
                        songThumbnail = requireAndroidQAPI(currentSongAlbumName)
                    } else {
                        songArtPath = apiLowerThanAndroidQ(currentSongAlbumId)
                    }

                    songsList.add(
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
            songsList
        }
    }

    private fun apiLowerThanAndroidQ(currentSongAlbumId: Long): String? {
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
    fun requireAndroidQAPI(songAlbumName: String): Bitmap? {
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