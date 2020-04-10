package com.example.music.player.dagger

import com.example.music.player.view.MainActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule


@Module
interface AppModule {
    @ContributesAndroidInjector(modules = [AndroidSupportInjectionModule::class, MainActivityChildrenModule::class])
    fun contributeActivityAndroidInjector(): MainActivity
}