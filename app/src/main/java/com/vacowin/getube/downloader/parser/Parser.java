package com.vacowin.getube.downloader.parser;

import com.alibaba.fastjson.JSONObject;
import com.vacowin.getube.downloader.YoutubeException;
import com.vacowin.getube.downloader.cipher.CipherFactory;
import com.vacowin.getube.downloader.extractor.Extractor;
import com.vacowin.getube.downloader.model.VideoDetails;
import com.vacowin.getube.downloader.model.formats.Format;

import java.io.IOException;
import java.util.List;

public interface Parser {

    Extractor getExtractor();

    CipherFactory getCipherFactory();

    JSONObject getPlayerConfig(String htmlUrl) throws IOException, YoutubeException;

    VideoDetails getVideoDetails(JSONObject config);

    String getJsUrl(JSONObject config) throws YoutubeException;

    List<Format> parseFormats(JSONObject json) throws YoutubeException;

}
