package com.example.music.player.dagger

import com.example.music.player.view.SongsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityFragmentsModule{
    @ContributesAndroidInjector(modules = [SongsModule::class])
    fun songsFragmentInjector(): SongsFragment
}