package com.vacowin.getube.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Nguyen Cong Van on 2020-07-30.
 */

@Entity
data class YoutubeVideo constructor(
    @PrimaryKey
    val videoId: String,
    val title: String,
    val description: String,
    val thumbnailUrl: String)
