package com.example.sharpcj.dreammusic.module.musichall.dao;

import android.os.AsyncTask;

import com.example.sharpcj.dreammusic.base.ParseJsonCompleteCallback;
import com.example.sharpcj.dreammusic.common.constant.Constant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2016/7/20.
 */
public class SelectionDao {
    public void getLoopPic(final ParseJsonCompleteCallback callback) {
        final List<String> list=new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(Constant.BAIDUMP3_HOME_URL).userAgent("Mozilla").timeout(6000).get();
                    Element js_random_focus = doc.getElementById("js-random-focus");
                    for (int i = 0; i < js_random_focus.children().size(); i++) {
                        Elements imgs = js_random_focus.child(i).getElementsByTag("img");
                        String imgUrl = imgs.get(0).attr("data-wide");
                        list.add(imgUrl);
                    }
                    if(callback!=null){
                        callback.useData(list);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
