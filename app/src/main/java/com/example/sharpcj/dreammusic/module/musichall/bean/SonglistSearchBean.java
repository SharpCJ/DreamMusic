package com.example.sharpcj.dreammusic.module.musichall.bean;

/**
 * Created by joy on 2016/7/22.
 */
public class SonglistSearchBean {
        private String cover_img;
        private String title;

    public SonglistSearchBean() {
    }

    public SonglistSearchBean(String cover_img, String title) {
        this.cover_img = cover_img;
        this.title = title;
    }

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
