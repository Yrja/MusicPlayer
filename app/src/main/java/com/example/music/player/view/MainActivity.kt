package com.example.music.player.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.music.player.R
import com.example.music.player.model.entity.Song
import com.example.music.player.view.song_player.SongPlayerFragment
import com.example.music.player.view.songs.SongsFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector, NavigationRouter {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.vSongsContainer, SongsFragment.getInstance())
            .commit()
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun navigateToSongsFragment() {
        replaceFragment(SongsFragment.getInstance())
    }

    override fun navigateToPermissionFragment() {
        replaceFragment(PermissionNotGrantedFragment.getInstance())
    }

    override fun navigateToSongPlayer(song: Song) {
        replaceFragment(SongPlayerFragment.getInstance(song))
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.vSongsContainer, fragment)
            .commit()
    }
}
