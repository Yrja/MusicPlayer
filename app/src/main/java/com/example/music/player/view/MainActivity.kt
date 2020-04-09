package com.example.music.player.view

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.music.player.R
import com.example.music.player.view.permission_helper.GetPermissionBinder
import com.example.music.player.view.permission_helper.MutablePermissionsStream
import com.example.music.player.view.permission_helper.PermissionResult
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasAndroidInjector{

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private val compositeDisposable = CompositeDisposable()
    private val permissionsStream = MutablePermissionsStream()
    private val takeAudiosBinder = GetPermissionBinder(this, permissionsStream)


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val disposable =
            takeAudiosBinder.getPermissionResult(arrayListOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                .subscribe({
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.vSongsContainer, SongsFragment.getInstance())
                        .commit()

                }, {
                    Toast.makeText(this, "No permission granted", Toast.LENGTH_SHORT).show()
                })
        compositeDisposable.add(disposable)
    }

    override fun onStop() {
        super.onStop()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
            compositeDisposable.clear()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        permissionsStream.onPermissionResult(
            PermissionResult(
                requestCode,
                permissions,
                grantResults
            )
        )
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}
