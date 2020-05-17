package com.vacowin.getube.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Created by Nguyen Cong Van on 2020-04-13.
 */

@Parcelize
data class YoutubePlaylist (
    val kind: String,
    //val nextPageToken: String,
    //val prevPageToken: String,
    val items: List<PlaylistItem> ): Parcelable {

}
@Parcelize
data class PlaylistItem (
    val kind: String,
    val id: String,
    val snippet: YoutubeVideo ): Parcelable {}

@Parcelize
data class YoutubeVideo (
    val title: String,
    val description: String,
    val thumbnails: VideoThumbnails,
    val resourceId: ResourceId) : Parcelable{}

@Parcelize
data class VideoThumbnails (
    val default: VideoThumbnail) : Parcelable{}

@Parcelize
data class VideoThumbnail (
    val url: String,
    val width: Int,
    val height: Int) : Parcelable{}

@Parcelize
data class ResourceId (
    val kind: String,
    val videoId: String) : Parcelable{}



