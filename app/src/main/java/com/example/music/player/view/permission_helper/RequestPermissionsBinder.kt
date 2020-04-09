package com.example.music.player.view.permission_helper
import io.reactivex.Completable

interface RequestPermissionsBinder {
    fun getPermissionResult(permissions: List<String>): Completable
}