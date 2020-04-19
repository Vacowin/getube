package com.vacowin.getube.playlist

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vacowin.getube.R
import com.vacowin.getube.databinding.PlaylistFragmentBinding
import com.vacowin.getube.network.YoutubeVideo
import java.util.regex.Matcher
import java.util.regex.Pattern

class PlaylistFragment : Fragment() {

    private val viewModel: PlaylistViewModel by lazy {
        ViewModelProvider(this@PlaylistFragment).get(PlaylistViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<PlaylistFragmentBinding>(inflater,
           R.layout.playlist_fragment,container,false)

        val adapter = PlaylistAdapter()
        binding.playlist.adapter = adapter

        viewModel.videos.observe(viewLifecycleOwner, Observer<List<YoutubeVideo>>{ videos ->
            adapter.data = videos
            //AlertDialog.Builder(activity).setMessage("VIDEOS in FRAGMENT \n" + videos).create().show()
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        if (activity?.intent?.type == "text/plain") {
            val bundle = activity?.intent?.extras
            val link = bundle?.get(Intent.EXTRA_TEXT) as String
            loadPlaylist(link)
        }
        else {
            viewModel.getPlaylist()
        }
    }

    private fun loadPlaylist(youtubeUri: String) {
        val patYouTubePageLink =
            Pattern.compile("(http|https)://(www\\.|m.|)youtube\\.com/playlist\\?list=(.+?)( |\\z|&)")
        val mat: Matcher = patYouTubePageLink.matcher(youtubeUri)
        if (mat.find()) {
            val playlistId = mat.group(3)
            viewModel.getPlaylist(playlistId)
            //AlertDialog.Builder(activity).setMessage("PLAYLIST_ID \n" + playlistId).create().show()
        }
    }
}
