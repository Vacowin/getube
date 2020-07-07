package com.vacowin.getube.downloader

/**
 * Created by Nguyen Cong Van on 2020-07-02.
 */
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.vacowin.getube.downloader.model.VideoDetails
import com.vacowin.getube.downloader.model.YoutubeVideo
import com.vacowin.getube.downloader.model.formats.Format
import org.junit.Test


class YoutubeApiServiceTest {

    @Test
    fun getYoutubeVideoDetails() {
        val downloader = YoutubeDownloader()
        //downloader.setParserRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");

        // parsing data
        val videoId = "xSXY2ClEdjQ" // for url https://www.youtube.com/watch?v=abc12345
        val video: YoutubeVideo = downloader.getVideo(videoId)
        val details: VideoDetails = video.details()
        println(details.description())
    }

    @Test
    fun downloadVideo() {
        val downloader = YoutubeDownloader()
        val videoId = "xSXY2ClEdjQ" // for url https://www.youtube.com/watch?v=abc12345
        val video: YoutubeVideo = downloader.getVideo(videoId)
        val details: VideoDetails = video.details()
        val formatByItag: Format = video.findFormatByItag(136)

        val uri = Uri.parse("https://www.youtube.com/watch?v=" + videoId)
        val request = DownloadManager.Request(uri)
        request.setTitle(details.title())

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            details.title() + "." + formatByItag.extension().value()
        )

        //val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        //manager.enqueue(request)
    }
}