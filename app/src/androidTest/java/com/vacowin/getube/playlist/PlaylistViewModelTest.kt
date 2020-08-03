package com.vacowin.getube.playlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vacowin.getube.network.NwPlaylistItem
import org.hamcrest.core.IsNot.not
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Nguyen Cong Van on 2020-04-16.
 */

@RunWith(AndroidJUnit4::class)
class PlaylistViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()



    @Test
    fun getPlaylist() {
        val viewModel = PlaylistViewModel()
        //ViewModelProviders.of(this).get(PlaylistViewModel::class.java)

        val observer = Observer<List<NwPlaylistItem>> {}
        viewModel.properties.observeForever(observer)

        try {
            //tasksViewModel.addNewTask()

            // Then the new task event is triggered
            val value = viewModel.properties
            assertThat(value.value, (not(emptyList<NwPlaylistItem>())))

        } finally {
            viewModel.properties.removeObserver(observer)
        }
    }
}