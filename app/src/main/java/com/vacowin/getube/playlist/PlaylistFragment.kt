package com.vacowin.getube.playlist

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vacowin.getube.databinding.PlaylistFragmentBinding

import com.vacowin.getube.R
import com.vacowin.getube.network.YoutubeVideo

class PlaylistFragment : Fragment() {

    private val viewModel: PlaylistViewModel by lazy {
        ViewModelProvider(this@PlaylistFragment).get(PlaylistViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return inflater.inflate(R.layout.playlist_fragment, container, false)
        val binding = DataBindingUtil.inflate<PlaylistFragmentBinding>(inflater,
           R.layout.playlist_fragment,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.videos.observe(viewLifecycleOwner, Observer<List<YoutubeVideo>>{ videos ->
            AlertDialog.Builder(activity).setMessage("VIDEOS in FRAGMENT \n" + videos).create().show()
        })
    }

}
