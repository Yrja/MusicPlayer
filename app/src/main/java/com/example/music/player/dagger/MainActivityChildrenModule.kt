package com.example.music.player.dagger

import com.example.music.player.view.PermissionNotGrantedFragment
import com.example.music.player.view.SongsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityChildrenModule {
    @PermissionScope
    @ContributesAndroidInjector(modules = [SongsModule::class, PermissionModule::class, SongsFragmentCastModule::class])
    fun provideSongsFragment(): SongsFragment

    @PermissionScope
    @ContributesAndroidInjector(modules = [PermissionModule::class, PermissionFragmentCastModule::class])
    fun providePermissionNotGrantedFragment(): PermissionNotGrantedFragment
}