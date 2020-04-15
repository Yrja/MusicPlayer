package com.example.music.player.view.permission_helper

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import io.reactivex.Completable

class GetPermissionBinder(
    private val onPermissionResultStream: PermissionListener,
    private val fragment: Fragment
) : RequestPermissionsBinder {

    override fun requestPermission(permissions: List<String>): Completable {
        val requiredPermissions = mutableListOf<String>()
        requiredPermissions.addAll(permissions)
        if (requiredPermissions.isNotEmpty()) {
            val notGrantedPermissions =
                requiredPermissions
                    .filter { element -> fragment.activity?.isPermissionGranted(element) == false}
                    .toTypedArray()
            if (notGrantedPermissions.isNotEmpty()) {
                fragment.requestPermissions(
                    notGrantedPermissions,
                    READ_EXTERNAL_STORAGE_REQUEST_CODE
                )
                return onPermissionResultStream.getPermissionResults()
                    .filter { it.requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE }
                    .doOnNext {
                        checkIfAllPermissionsGranted(it)
                    }
                    .firstOrError()
                    .ignoreElement()
            }
        }
        return Completable.complete()
    }

    private fun checkIfAllPermissionsGranted(permissionResult: PermissionResult) {
        if (permissionResult.grantResults.isNotEmpty()) {
            val existNonGrantedPermissionFlag = permissionResult.grantResults
                .contains(PackageManager.PERMISSION_DENIED)
            if (existNonGrantedPermissionFlag) {
                throw Exception()
            }
        } else{
            throw Exception()
        }
    }

    companion object {
        private const val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1
    }
}