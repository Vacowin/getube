package com.vacowin.getube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.vacowin.getube.network.PlaylistItem
import com.vacowin.getube.network.YoutubeApi
import com.vacowin.getube.network.YoutubePlaylist
import com.vacowin.getube.network.YoutubeVideo
import com.vacowin.getube.playlist.PlaylistViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    /*
    private val viewModel: PlaylistViewModel by lazy {
        ViewModelProvider(this@MainActivity).get(PlaylistViewModel::class.java)
    }
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        viewModel.videos.observe(this, Observer<List<YoutubeVideo>>{ videos ->
            AlertDialog.Builder(this@MainActivity).setMessage("VIDEOS \n" + videos).create().show()
        })
         */
    }
}
