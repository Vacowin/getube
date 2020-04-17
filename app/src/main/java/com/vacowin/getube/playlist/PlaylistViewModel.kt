package com.vacowin.getube.playlist

import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vacowin.getube.network.PlaylistItem
import com.vacowin.getube.network.YoutubeApi
import com.vacowin.getube.network.YoutubePlaylist
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

    init {
        getPlaylist()
    }

    @VisibleForTesting
    private fun getPlaylist() {
        viewModelScope.launch {
            var playlistDeferred = YoutubeApi.retrofitService.playListItems()
            try {
                _status.value = PlaylistApiStatus.LOADING
                val playlist = playlistDeferred.await()
                _status.value = PlaylistApiStatus.DONE
                _properties.value = playlist.items
            }
            catch (e: Exception) {
                _status.value = PlaylistApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

}