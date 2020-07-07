package com.vacowin.getube.downloader.extractor;

import com.vacowin.getube.downloader.YoutubeException;


public interface Extractor {

    void setRequestProperty(String key, String value);

    void setRetryOnFailure(int retryOnFailure);

    String extractYtPlayerConfig(String html) throws YoutubeException;

    String loadUrl(String url) throws YoutubeException;

}
