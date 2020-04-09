package com.example.music.player.view.permission_helper

interface PermissionsStream {
    /**
     * notify stream about new permission result
     * @param result - result of permission request
     */
    fun onPermissionResult(result: PermissionResult)
}