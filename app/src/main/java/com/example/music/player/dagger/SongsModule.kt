package com.example.music.player.dagger

import com.example.music.player.model.SongsRepository
import com.example.music.player.model.songs.SongsInteractor
import com.example.music.player.model.songs.SongsInteractorImpl
import com.example.music.player.view.image_helper.ImageLoader
import com.example.music.player.view.image_helper.ImageLoaderImpl
import com.example.music.player.view.presenter.songs.SongsPresenter
import com.example.music.player.view.presenter.songs.SongsPresenterImpl
import com.example.music.player.view.songs.SongManager
import dagger.Module
import dagger.Provides

@Module
class SongsModule {
    @Provides
    fun provideSongsPresenter(
        songsInteractor: SongsInteractor,
        songManager: SongManager
    ): SongsPresenter =
        SongsPresenterImpl(
            songsInteractor, songManager
        )

    @Provides
    fun provideSongsInteractor(songsRepository: SongsRepository): SongsInteractor =
        SongsInteractorImpl(songsRepository)

    @Provides
    fun provideImageLoader(): ImageLoader = ImageLoaderImpl()
}