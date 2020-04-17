package com.vacowin.getube.network

import com.vacowin.getube.playlist.PlaylistApiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception

/**
 * Created by Nguyen Cong Van on 2020-04-16.
 */
class YoutubeApiServiceTest {

    var testDispatcher = TestCoroutineDispatcher()
    var testScope = TestCoroutineScope(testDispatcher)

    //@get:Rule
    //val testCoroutineRule = TestCoroutineRule()
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun getPlaylist() {
        testScope.runBlockingTest {
            var playlistDeferred = YoutubeApi.retrofitService.playListItems()
            try {
                val playlist = playlistDeferred.await()
                println("AAAA "+playlist)
            }
            catch (e: Exception) {

            }
        }
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }
}