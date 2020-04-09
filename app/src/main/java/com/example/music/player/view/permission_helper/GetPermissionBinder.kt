package com.example.music.player.view.permission_helper

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import io.reactivex.Completable

class GetPermissionBinder(
    private val activity: Activity,
    private val onPermissionResultStream: PermissionListener
) : RequestPermissionsBinder {


    override fun getPermissionResult(permissions: List<String>): Completable {
        val requiredPermissions = mutableListOf<String>()
        requiredPermissions.addAll(permissions)

        if (requiredPermissions.isNotEmpty()) {
            val notGrantedPermissions =
                requiredPermissions
                    .filter { element -> !activity.isPermissionGranted(element) }
                    .toTypedArray()
         return   if (notGrantedPermissions.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    activity,
                    notGrantedPermissions,
                    READ_EXTERNAL_STORAGE_REQUEST_CODE
                )
                 onPermissionResultStream.getPermissionResults()
                    .filter { it.requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE }
                    .filter { it.grantResults.isNotEmpty() && it.grantResults[0] == PackageManager.PERMISSION_GRANTED }
                    .filter {
                        ContextCompat.checkSelfPermission(
                            activity,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    }
                    .firstOrError()
                    .ignoreElement()
            } else{
                 Completable.complete()
            }
        }
    return Completable.complete()
}
    companion object {
        private const val READ_EXTERNAL_STORAGE_REQUEST_CODE = 1
    }

}