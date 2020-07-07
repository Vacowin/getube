package com.vacowin.getube.downloader.model.formats;


import com.alibaba.fastjson.JSONObject;
import com.vacowin.getube.downloader.model.quality.AudioQuality;

public class AudioFormat extends Format {

    private final Integer averageBitrate;
    private final Integer audioSampleRate;
    private final AudioQuality audioQuality;

    public AudioFormat(JSONObject json) {
        super(json);
        audioSampleRate = json.getInteger("audioSampleRate");
        averageBitrate = json.getInteger("averageBitrate");

        AudioQuality audioQuality = null;
        if (json.containsKey("audioQuality")) {
            String[] split = json.getString("audioQuality").split("_");
            String quality = split[split.length - 1].toLowerCase();
            try {
                audioQuality = AudioQuality.valueOf(quality);
            } catch (IllegalArgumentException ignore) {
            }
        }
        this.audioQuality = audioQuality;
    }

    @Override
    public String type() {
        return AUDIO;
    }

    public Integer averageBitrate() {
        return averageBitrate;
    }

    public AudioQuality audioQuality() {
        return audioQuality != null ? audioQuality : itag.audioQuality();
    }

    public Integer audioSampleRate() {
        return audioSampleRate;
    }
}
