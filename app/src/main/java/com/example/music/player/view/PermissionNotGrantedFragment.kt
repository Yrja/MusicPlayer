package com.example.music.player.view

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.music.player.R
import com.example.music.player.view.permission_helper.PermissionListener
import com.example.music.player.view.permission_helper.PermissionResult
import com.example.music.player.view.permission_helper.PermissionsStream
import com.example.music.player.view.permission_helper.RequestPermissionsBinder
import com.example.music.player.view.presenter.BaseFragment
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.permission_not_granted_layout.*
import javax.inject.Inject

class PermissionNotGrantedFragment private constructor() : BaseFragment(), View.OnClickListener {
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var permissionsListener: PermissionListener

    @Inject
    lateinit var permissionsStream: PermissionsStream

    @Inject
    lateinit var requestPermissionsBinder: RequestPermissionsBinder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.permission_not_granted_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vDeclinePermission.setOnClickListener(this)
        vGrantPermission.setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            vDeclinePermission.id -> {
                activity?.finish()
            }
            vGrantPermission.id -> {
                checkWorkingConditions()
            }
        }
    }

    private fun checkWorkingConditions() {
        val disposable =
            requestPermissionsBinder.requestPermission(arrayListOf(Manifest.permission.READ_EXTERNAL_STORAGE))
                .subscribe({
                    if (activity is NavigationRouter) {
                        (activity as NavigationRouter).navigateToSongsFragment()
                    }
                }, {
                    activity?.apply {
                        finish()
                    }
                })
        compositeDisposable.add(disposable)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsStream.onPermissionResult(
            PermissionResult(
                requestCode,
                permissions,
                grantResults
            )
        )
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    companion object {
        fun getInstance(): PermissionNotGrantedFragment {
            return PermissionNotGrantedFragment()
        }
    }
}