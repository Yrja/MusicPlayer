package com.example.music.player.view.permission_helper

import io.reactivex.Observable

/**
 * use this class when you need wait for some permission
 */
interface PermissionListener {
    /**
     * @return observable of applied decisions for requested permission
     */
    fun getPermissionResults(): Observable<PermissionResult>
}