package com.vacowin.getube

import android.app.Application
import android.content.Context

/**
 * Created by Nguyen Cong Van on 2020-04-13.
 */
class GetubeApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: GetubeApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

    }
}