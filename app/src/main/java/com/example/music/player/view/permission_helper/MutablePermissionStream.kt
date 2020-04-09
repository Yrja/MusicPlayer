package com.example.music.player.view.permission_helper

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MutablePermissionsStream : PermissionsStream, PermissionListener {
    private val relay = PublishSubject.create<PermissionResult>()
    override fun getPermissionResults(): Observable<PermissionResult> = relay

    override fun onPermissionResult(result: PermissionResult) {
        relay.onNext(result)
    }
}