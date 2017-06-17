package com.example.sharpcj.dreammusic.jiuguoutils;

import com.example.sharpcj.dreammusic.jiuguoutils.beans.MediaStoreSong;

import java.util.List;

/**
 * Created by joy on 2016/7/19.
 */
public interface PlayCallback {
    public void startPlay(MediaStoreSong song, List<MediaStoreSong> list,int position);
    public void stopPlay();
}
