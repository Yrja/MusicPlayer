package com.example.music.player.dagger

import android.content.ContentResolver
import com.example.music.player.data.ContentResolverDSImpl
import com.example.music.player.data.SongsDataSource
import com.example.music.player.data.SongsRepositoryImpl
import com.example.music.player.model.SongsRepository
import com.example.music.player.model.songs.SongsInteractor
import com.example.music.player.model.songs.SongsInteractorImpl
import com.example.music.player.view.SongsFragment
import com.example.music.player.view.presenter.SongsPresenter
import com.example.music.player.view.presenter.SongsPresenterImpl
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
class SongsModule {
    @Provides
    fun provideSongsPresenter(songsInteractor: SongsInteractor): SongsPresenter =
        SongsPresenterImpl(songsInteractor)

    @Provides
    fun provideSongsInteractor(songsRepository: SongsRepository): SongsInteractor =
        SongsInteractorImpl(songsRepository)

    @Provides
    fun provideSongsRepository(songsDataSource: SongsDataSource): SongsRepository =
        SongsRepositoryImpl(songsDataSource)

    @Provides
    fun provideDataSource(contentResolver: ContentResolver): SongsDataSource =
        ContentResolverDSImpl(contentResolver)

    @Provides
    fun provideContentResolver(fragment: SongsFragment): ContentResolver =
        fragment.activity!!.contentResolver
}