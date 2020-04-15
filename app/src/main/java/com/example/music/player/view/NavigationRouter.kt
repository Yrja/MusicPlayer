package com.example.music.player.view

import com.example.music.player.model.entity.Song

interface NavigationRouter {
    fun navigateToSongsFragment()
    fun navigateToPermissionFragment()
    fun navigateToSongPlayer(song: Song)
}