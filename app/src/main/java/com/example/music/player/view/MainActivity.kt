package com.example.music.player.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.music.player.R
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
        supportFragmentManager.beginTransaction()
            .replace(R.id.vSongsContainer, SongsFragment.getInstance())
            .commit()
    }

    override fun navigateToPermissionFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.vSongsContainer, PermissionNotGrantedFragment.getInstance())
            .commit()
    }
}
