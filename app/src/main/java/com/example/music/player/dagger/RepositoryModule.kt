package com.example.music.player.dagger

import android.content.ContentResolver
import com.example.music.player.PlayerApplication
import com.example.music.player.data.ContentResolverDSImpl
import com.example.music.player.data.SongsDataSource
import com.example.music.player.data.SongsRepositoryImpl
import com.example.music.player.model.SongsRepository
import com.example.music.player.view.songs.SongManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideSongsRepository(songsDataSource: SongsDataSource): SongsRepository =
        SongsRepositoryImpl(songsDataSource)

    @Provides
    fun provideDataSource(contentResolver: ContentResolver): SongsDataSource =
        ContentResolverDSImpl(contentResolver)

    @Provides
    fun provideContentResolver(app: PlayerApplication): ContentResolver =
        app.contentResolver

    @Singleton
    @Provides
    fun provideSongManager(): SongManager = SongManager()
}