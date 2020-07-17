package com.vacowin.getube.playlist

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import com.vacowin.getube.downloader.YoutubeDownloader
import com.vacowin.getube.downloader.model.VideoDetails
import com.vacowin.getube.downloader.model.formats.Format
import com.vacowin.getube.network.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created by Nguyen Cong Van on 2020-04-16.
 */

enum class PlaylistApiStatus { LOADING, ERROR, DONE }

class PlaylistViewModel(application: Application) : AndroidViewModel(application){
    private val _status = MutableLiveData<PlaylistApiStatus>()
    val status: LiveData<PlaylistApiStatus>
        get() = _status

    private val _properties = MutableLiveData<List<PlaylistItem>>()
    val properties: LiveData<List<PlaylistItem>>
        get() = _properties

    private val _videos = MutableLiveData<List<YoutubeVideo>>()
    val videos: LiveData<List<YoutubeVideo>>
        get() = _videos

    private val _endList = MutableLiveData<Boolean>()
    val endList: LiveData<Boolean>
        get() = _endList


    fun getPlaylist(playlistId: String = ANDROID_LONG_PLAYLIST_ID) {
        viewModelScope.launch {
            var playlistDeferred = YoutubeApi.retrofitService.playListItems(playlistId)
            try {
                _status.value = PlaylistApiStatus.LOADING
                val playlist = playlistDeferred.await()
                Log.d("VCN", playlist.toString())
                _status.value = PlaylistApiStatus.DONE
                _properties.value = playlist.items
                _videos.value = playlist.items.map { it.snippet }
                //_endList.value = playlist.nextPageToken.isEmpty()
            }
            catch (e: Exception) {
                Log.d("VCN", "load list err " + e.message)
                _status.value = PlaylistApiStatus.ERROR
                _properties.value = ArrayList()
                _videos.value = ArrayList()
            }
        }
    }

    fun getNextPage(nextPageToken: String, playlistId: String = TEST_PLAYLIST_ID) {

    }

    fun downloadOneAudioAsync(videoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            downloadOneAudio(videoId)
        }
    }

    private fun downloadOneAudio(videoId: String) {
        val downloader = YoutubeDownloader()
        val video = downloader.getVideo(videoId)
        val details: VideoDetails = video.details()

        val audioFormats = video.audioFormats()
        if (audioFormats.size == 0) {
            //TODO warning
            return
        }

        val uri = Uri.parse(audioFormats[0].url())
        val request = DownloadManager.Request(uri)
        request.setTitle(details.title())

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_MUSIC,
            "Getube/" + details.title() + "." + audioFormats[0].extension().value()
        )

        val manager = getApplication<Application>().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }

}