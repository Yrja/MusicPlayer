package com.example.music.player.dagger

import androidx.fragment.app.Fragment
import com.example.music.player.view.songs.SongsFragment
import dagger.Module
import dagger.Provides

@Module
class SongsFragmentCastModule {
    @Provides
    fun castSongsFragment(songsFragment: SongsFragment): Fragment = songsFragment
}