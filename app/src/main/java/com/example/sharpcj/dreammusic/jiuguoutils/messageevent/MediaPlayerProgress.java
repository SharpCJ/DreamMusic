package com.example.sharpcj.dreammusic.jiuguoutils.messageevent;

/**
 * Created by joy on 2016/7/19.
 */
public class MediaPlayerProgress {
    private int duration;
    private int currentPosition;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public MediaPlayerProgress(int duration, int currentPosition) {
        this.duration = duration;
        this.currentPosition = currentPosition;
    }
}
