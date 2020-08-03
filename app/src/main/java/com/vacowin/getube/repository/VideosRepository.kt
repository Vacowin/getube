package com.vacowin.getube.repository

import androidx.lifecycle.LiveData
import com.vacowin.getube.database.YoutubeDatabase
import com.vacowin.getube.database.YoutubeVideo
import com.vacowin.getube.network.NwYoutubeVideo
import com.vacowin.getube.network.YoutubeApi
import com.vacowin.getube.network.asDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Nguyen Cong Van on 2020-08-03.
 */
class VideosRepository(private val database: YoutubeDatabase) {

    val videos: LiveData<List<YoutubeVideo>> = database.videoDao.getVideos()

    suspend fun refreshVideos(playlistId: String) {
        withContext(Dispatchers.IO) {
            val playlist = YoutubeApi.retrofitService.playListItems(playlistId).await()
            val videos = playlist.items.map { it.snippet }
            database.videoDao.insertAll(videos.asDbModel())
        }
    }
}