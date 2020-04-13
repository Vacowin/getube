package com.vacowin.getube.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vacowin.getube.GetubeApplication
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Nguyen Cong Van on 2020-04-13.
 */

private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
private const val API_KEY = "AIzaSyCro450_XdkUQtX16VJsPmyEnnp0ez9LNw"
private const val TEST_PLAYLIST_ID = "PLVkRi-91ersa5GrYgyUtWWJTvNaskBVaf"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private var client = OkHttpClient.Builder()
    .addInterceptor(ChuckInterceptor(GetubeApplication.applicationContext()))
    .build()

private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface YoutubeApiService {
    /*
    @Headers(
        "Authorization: Bearer $API_KEY"
    )*/
    @GET("playlistItems")
    fun playListItems(@Query("part") part: String = "snippet",
                      @Query("key") key: String = API_KEY,
                      @Query("playlistId") id: String = TEST_PLAYLIST_ID): Deferred<YoutubePlaylist>
}

object YoutubeApi {
    val retrofitService : YoutubeApiService by lazy { retrofit.create(YoutubeApiService::class.java) }
}
