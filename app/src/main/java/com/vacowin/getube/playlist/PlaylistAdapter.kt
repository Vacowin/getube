package com.vacowin.getube.playlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vacowin.getube.R
import com.vacowin.getube.database.YoutubeVideo
import com.vacowin.getube.network.NwYoutubeVideo

/**
 * Created by Nguyen Cong Van on 2020-04-18.
 */
class PlaylistAdapter : RecyclerView.Adapter<PlaylistViewHolder>()  {

    lateinit var viewModel: PlaylistViewModel

    var data =  listOf<YoutubeVideo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val item = data[position]
        //holder.author.text = item.thumbnailUrl
        holder.title.text = item.title
        Glide.with(holder.thumbnail.context).load(item.thumbnailUrl).into(holder.thumbnail)
        holder.downloadBtn.setOnClickListener{
            viewModel.downloadOneAudioAsync(item.videoId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.playlist_item_view, parent, false)
        return PlaylistViewHolder(view)
    }
}

class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val title: TextView = itemView.findViewById(R.id.title)
    val author: TextView = itemView.findViewById(R.id.author)
    val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail)
    val downloadBtn: Button = itemView.findViewById(R.id.downloadBtn)
}

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)