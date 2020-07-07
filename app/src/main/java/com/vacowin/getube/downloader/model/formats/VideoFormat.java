package com.vacowin.getube.downloader.model.formats;

import com.alibaba.fastjson.JSONObject;
import com.vacowin.getube.downloader.model.quality.VideoQuality;

public class VideoFormat extends Format {

    private final int fps;
    private final String qualityLabel;
    private final Integer width;
    private final Integer height;
    private final VideoQuality videoQuality;

    public VideoFormat(JSONObject json) {
        super(json);
        fps = json.getInteger("fps");
        qualityLabel = json.getString("qualityLabel");
        if (json.containsKey("size")) {
            String[] split = json.getString("size").split("x");
            width = Integer.parseInt(split[0]);
            height = Integer.parseInt(split[1]);
        } else {
            width = json.getInteger("width");
            height = json.getInteger("height");
        }
        VideoQuality videoQuality = null;
        if (json.containsKey("quality")) {
            try {
                videoQuality = VideoQuality.valueOf(json.getString("quality"));
            } catch (IllegalArgumentException ignore) {
            }
        }
        this.videoQuality = videoQuality;
    }

    @Override
    public String type() {
        return VIDEO;
    }

    public int fps() {
        return fps;
    }

    public VideoQuality videoQuality() {
        return videoQuality != null ? videoQuality : itag.videoQuality();
    }

    public String qualityLabel() {
        return qualityLabel;
    }

    public Integer width() {
        return width;
    }

    public Integer height() {
        return height;
    }

}
