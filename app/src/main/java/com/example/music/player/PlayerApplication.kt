package com.example.music.player

import android.app.Application
import com.example.music.player.dagger.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class PlayerApplication : Application(), HasAndroidInjector{

    @Inject
   lateinit var dispatcher: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .build()
            .inject(this)

    }

    override fun androidInjector(): AndroidInjector<Any> = dispatcher
}