package com.example.music.player.presenter

interface BaseView {
    fun showLoading()

    fun hideLoading()

    fun showError(error: Throwable?)
}