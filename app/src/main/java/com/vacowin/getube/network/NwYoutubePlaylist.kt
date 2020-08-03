package com.vacowin.getube.network

import android.os.Parcelable
import com.vacowin.getube.database.YoutubeVideo
import kotlinx.android.parcel.Parcelize

/**
 * Created by Nguyen Cong Van on 2020-04-13.
 */

@Parcelize
data class NwYoutubePlaylist (
    val kind: String,
    //val nextPageToken: String,
    //val prevPageToken: String,
    val items: List<NwPlaylistItem> ): Parcelable {

}
@Parcelize
data class NwPlaylistItem (
    val kind: String,
    val id: String,
    val snippet: NwYoutubeVideo ): Parcelable {}

@Parcelize
data class NwYoutubeVideo (
    val title: String,
    val description: String,
    val thumbnails: NwVideoThumbnails,
    val resourceId: NwResourceId) : Parcelable{}

@Parcelize
data class NwVideoThumbnails (
    val default: NwVideoThumbnail) : Parcelable{}

@Parcelize
data class NwVideoThumbnail (
    val url: String,
    val width: Int,
    val height: Int) : Parcelable{}

@Parcelize
data class NwResourceId (
    val kind: String,
    val videoId: String) : Parcelable{}

fun List<NwYoutubeVideo>.asDbModel(): List<YoutubeVideo> {
    return map {
        YoutubeVideo(
            title = it.title,
            description = it.description,
            videoId = it.resourceId.videoId,
            thumbnailUrl = it.thumbnails.default.url
        )
    }
}

