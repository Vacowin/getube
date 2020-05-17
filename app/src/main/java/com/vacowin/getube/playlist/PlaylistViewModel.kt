package com.vacowin.getube.playlist

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
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

class PlaylistViewModel : ViewModel(){
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

    /*
    init {
        getPlaylist()
    }
     */

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
}