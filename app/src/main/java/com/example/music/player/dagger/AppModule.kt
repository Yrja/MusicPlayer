package com.example.music.player.dagger

import com.example.music.player.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule


@Module(includes = [AndroidSupportInjectionModule::class])
interface AppModule {
    @ContributesAndroidInjector(modules = [MainActivityFragmentsModule::class])
    fun mainActivityInjector(): MainActivity
}