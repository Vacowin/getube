package com.vacowin.getube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.vacowin.getube.network.YoutubeApi
import com.vacowin.getube.network.YoutubePlaylist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var viewModelJob = Job();
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coroutineScope.launch {
            var playlistDeferred = YoutubeApi.retrofitService.playListItems()
            try {
                val playlist = playlistDeferred.await();
                AlertDialog.Builder(this@MainActivity).setMessage("Yeeeee\n" + playlist).create().show()
            }
            catch (e: Exception) {
                AlertDialog.Builder(this@MainActivity).setMessage("Error " + e.message).create().show()
            }
        }
        /*
        YoutubeApi.retrofitService.playListItems().enqueue(object: Callback<YoutubePlaylist> {
            override fun onFailure(call: Call<YoutubePlaylist>, t: Throwable) {
                AlertDialog.Builder(this@MainActivity).setMessage("Error " + t.message).create().show()
            }

            override fun onResponse(call: Call<YoutubePlaylist>, response: Response<YoutubePlaylist>) {
                AlertDialog.Builder(this@MainActivity).setMessage("Success " + response.body()).create().show()
            }
        });
         */
    }
}
