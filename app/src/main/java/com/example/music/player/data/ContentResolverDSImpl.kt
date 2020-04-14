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

class ContentResolverDSImpl constructor(var contentResolver: ContentResolver) : SongsDataSource {
    override fun getSongs(): Single<List<Song>> {
        return Single.fromCallable {
            val songs = ArrayList<Song>()
            val externalMediaStorageContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val songsCursor =
                contentResolver.query(externalMediaStorageContentUri, null, null, null, null)
            if (songsCursor != null && songsCursor.moveToFirst()) {
                val songTitleIndex: Int = songsCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
                val songArtistIndex: Int = songsCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
                val albumIdIndex: Int = songsCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)
                val albumNameIndex: Int = songsCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)
                do {
                    songs.add(
                        getSong(
                            songTitleIndex,
                            songArtistIndex,
                            albumIdIndex,
                            albumNameIndex,
                            songsCursor
                        )
                    )

                } while (songsCursor.moveToNext())
                songsCursor.close()
            }
            songs
        }
    }

    private fun getSong(
        songTitleIndex: Int,
        songArtistIndex: Int,
        albumIdIndex: Int,
        albumNameIndex: Int,
        songsCursor: Cursor
    ): Song {
        val currentSongAlbumId: Long = songsCursor.getLong(albumIdIndex)
        val currentSongTitle: String = songsCursor.getString(songTitleIndex)
        val currentSongArtist: String = songsCursor.getString(songArtistIndex)
        val currentSongAlbumName: String = songsCursor.getString(albumNameIndex)
        var songArtPath: String? = null
        var songThumbnail: Bitmap? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            songThumbnail = getAlbumArtApiQ(currentSongAlbumName)
        } else {
            songArtPath = getAlbumArtPathApiLowerQ(currentSongAlbumId)
        }
        return Song(
            currentSongArtist,
            currentSongTitle,
            songArtPath,
            songThumbnail
        )
    }

    private fun getAlbumArtPathApiLowerQ(currentSongAlbumId: Long): String? {
        val albumsCursor = contentResolver.query(
            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM_ART
            ),
            MediaStore.Audio.Albums._ID + "=?",
            arrayOf(currentSongAlbumId.toString()),
            null
        )

        val path = if (albumsCursor != null && albumsCursor.moveToFirst()) {
            albumsCursor.getString(albumsCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))
        } else {
            null
        }
        albumsCursor?.close()
        return path
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getAlbumArtApiQ(songAlbumName: String): Bitmap? {
        val externalAlbumsStorageContentUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
        var bitmap: Bitmap? = null
        val albumsCursor: Cursor? = contentResolver.query(
            externalAlbumsStorageContentUri,
            null,
            null,
            null,
            null
        )
        albumsCursor?.use { cursor ->
            val albumIdIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Albums._ID)
            val albumNameIndex: Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(albumIdIndex)
                val currentAlbumName: String = cursor.getString(albumNameIndex)
                if (currentAlbumName == songAlbumName) {
                    val contentUri: Uri =
                        ContentUris.withAppendedId(externalAlbumsStorageContentUri, id)
                    try {
                        bitmap = contentResolver.loadThumbnail(contentUri, Size(640, 640), null)
                    } catch (ex: FileNotFoundException) {
                    }
                    break
                }
            }
        }
        albumsCursor?.close()
        return bitmap
    }
}