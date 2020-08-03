package com.vacowin.getube.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Created by Nguyen Cong Van on 2020-08-03.
 */

@Dao
interface VideoDao {
    @Query("select * from youtubevideo")
    fun getVideos(): LiveData<List<YoutubeVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( videos: List<YoutubeVideo>)
}

@Database(entities = [YoutubeVideo::class], version = 1)
abstract class YoutubeDatabase: RoomDatabase() {
    abstract val videoDao: VideoDao
}

private lateinit var INSTANCE: YoutubeDatabase

fun getDatabase(context: Context): YoutubeDatabase {
    synchronized(YoutubeDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                YoutubeDatabase::class.java,
                "youtubedatabase").build()
        }
    }
    return INSTANCE
}