package com.example.music.player.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.music.player.R
import com.example.music.player.model.entity.Song
import com.example.music.player.view.presenter.SongsPresenter
import com.example.music.player.view.presenter.SongsView
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.songs_fragment.*
import javax.inject.Inject

class SongsFragment private constructor() : Fragment(), SongsView, HasAndroidInjector {

    @Inject
    lateinit var androidFragmentInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var presenter: SongsPresenter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.songs_fragment, container, false)
    }


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        presenter.attachView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        presenter = SongsPresenterImpl(
//            SongsInteractorImpl(
//                SongsRepositoryImpl(
//                    ContentResolverDSImpl(activity!!.contentResolver)
//                )
//            )
//        )
        presenter.getSongs()
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }

    companion object {
        const val IMAGE_URI =
            "https://honestlywtf.com/wp-content/uploads/2019/05/driedflowers22.jpg"

        fun getInstance(): SongsFragment {
            return SongsFragment()
        }
    }

    override fun displaySongs(songs: List<Song>) {
        val adapter = SongAdapter(songs)
        vSongsList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        vSongsList.adapter = adapter
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }


    override fun showError(error: Throwable?) {
        Toast.makeText(activity, error?.message ?: "Uploading songs failed!", Toast.LENGTH_SHORT)
            .show()
    }

    override fun androidInjector(): AndroidInjector<Any> = androidFragmentInjector
}