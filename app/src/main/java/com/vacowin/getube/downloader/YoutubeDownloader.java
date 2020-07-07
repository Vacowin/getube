package com.vacowin.getube.downloader;


import com.alibaba.fastjson.JSONObject;
import com.vacowin.getube.downloader.cipher.CipherFunction;
import com.vacowin.getube.downloader.model.*;
import com.vacowin.getube.downloader.model.formats.Format;
import com.vacowin.getube.downloader.parser.DefaultParser;
import com.vacowin.getube.downloader.parser.Parser;

import java.io.IOException;
import java.util.List;

public class YoutubeDownloader {

    private Parser parser;

    public YoutubeDownloader() {
        this.parser = new DefaultParser();
    }

    public YoutubeDownloader(Parser parser) {
        this.parser = parser;
    }

    public void setParserRequestProperty(String key, String value) {
        parser.getExtractor().setRequestProperty(key, value);
    }

    public void setParserRetryOnFailure(int retryOnFailure) {
        parser.getExtractor().setRetryOnFailure(retryOnFailure);
    }

    public void addCipherFunctionPattern(int priority, String regex) {
        parser.getCipherFactory().addInitialFunctionPattern(priority, regex);
    }

    public void addCipherFunctionEquivalent(String regex, CipherFunction function) {
        parser.getCipherFactory().addFunctionEquivalent(regex, function);
    }

    public YoutubeVideo getVideo(String videoId) throws YoutubeException, IOException {
        String htmlUrl = "https://www.youtube.com/watch?v=" + videoId;

        JSONObject ytPlayerConfig = parser.getPlayerConfig(htmlUrl);

        VideoDetails videoDetails = parser.getVideoDetails(ytPlayerConfig);

        List<Format> formats = parser.parseFormats(ytPlayerConfig);
        return new YoutubeVideo(videoDetails, formats);
    }
}
