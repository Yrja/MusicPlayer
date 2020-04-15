package com.example.music.player.dagger

import androidx.fragment.app.Fragment
import com.example.music.player.view.permission_helper.*
import dagger.Module
import dagger.Provides

@Module
class PermissionModule {
    @PermissionScope
    @Provides
    fun provideMutablePermissionStream(): MutablePermissionsStream = MutablePermissionsStream()

    @Provides
    fun providePermissionStream(mutablePermissionStream: MutablePermissionsStream): PermissionsStream =
        mutablePermissionStream

    @Provides
    fun providePermissionListener(mutablePermissionStream: MutablePermissionsStream): PermissionListener =
        mutablePermissionStream

    @PermissionScope
    @Provides
    fun providePermissionBinder(
        permissionListener: PermissionListener,
        fragment: Fragment
    ): RequestPermissionsBinder = GetPermissionBinder(permissionListener, fragment)
}