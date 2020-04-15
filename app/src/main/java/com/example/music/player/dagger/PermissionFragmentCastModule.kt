package com.example.music.player.dagger

import androidx.fragment.app.Fragment
import com.example.music.player.view.PermissionNotGrantedFragment
import dagger.Module
import dagger.Provides

@Module
class PermissionFragmentCastModule {
    @Provides
    fun castPermissionFragment(permissionFragment: PermissionNotGrantedFragment): Fragment =
        permissionFragment
}