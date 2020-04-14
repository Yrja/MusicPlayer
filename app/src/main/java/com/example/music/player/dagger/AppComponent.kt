package com.example.music.player.dagger

import com.example.music.player.PlayerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, RepositoryModule::class])
interface AppComponent {
    fun inject(app: PlayerApplication)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: PlayerApplication): Builder
    }
}