package com.example.music.player.dagger

import com.example.music.player.PlayerApplication
import dagger.Component
import dagger.android.AndroidInjectionModule


@Component(modules = [AndroidInjectionModule::class, AppModule::class])
interface AppComponent {
    fun inject(app:PlayerApplication)
}