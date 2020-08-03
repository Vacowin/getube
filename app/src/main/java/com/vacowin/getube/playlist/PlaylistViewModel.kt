package com.vacowin.getube.playlist

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.*
import com.vacowin.getube.database.YoutubeVideo
import com.vacowin.getube.database.getDatabase
import com.vacowin.getube.downloader.YoutubeDownloader
import com.vacowin.getube.downloader.model.VideoDetails
import com.vacowin.getube.network.*
import com.vacowin.getube.repository.VideosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Created by Nguyen Cong Van on 2020-04-16.
 */

enum class PlaylistApiStatus { LOADING, ERROR, DONE }

class PlaylistViewModel(application: Application) : AndroidViewModel(application){

    private val videosRepository = VideosRepository(getDatabase(application))

    val videos = videosRepository.videos

    private val _status = MutableLiveData<PlaylistApiStatus>()
    val status: LiveData<PlaylistApiStatus>
        get() = _status


    private val _properties = MutableLiveData<List<NwPlaylistItem>>()
    val properties: LiveData<List<NwPlaylistItem>>
        get() = _properties



    /*
    private val _endList = MutableLiveData<Boolean>()
    val endList: LiveData<Boolean>
        get() = _endList
    */

    fun getPlaylist(playlistId: String = ANDROID_LONG_PLAYLIST_ID) {
        viewModelScope.launch {
            try {
                _status.value = PlaylistApiStatus.LOADING
                videosRepository.refreshVideos(playlistId)
                _status.value = PlaylistApiStatus.DONE
                //_endList.value = playlist.nextPageToken.isEmpty()
            }
            catch (e: Exception) {
                Log.d("VCN", "load list err " + e.message)
                _status.value = PlaylistApiStatus.ERROR
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