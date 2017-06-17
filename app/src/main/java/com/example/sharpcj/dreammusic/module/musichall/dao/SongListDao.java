package com.example.sharpcj.dreammusic.module.musichall.dao;

import com.example.sharpcj.dreammusic.base.ParseJsonCompleteCallback;
import com.example.sharpcj.dreammusic.common.constant.Constant;
import com.example.sharpcj.dreammusic.jiuguoutils.BaiduMusicUtils;
import com.example.sharpcj.dreammusic.module.musichall.bean.SonglistSearchBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2016/7/21.
 */
public class SongListDao {
    public void getSongList(final ParseJsonCompleteCallback callback) {
        final List<SonglistSearchBean> list=new ArrayList<>();
        new Thread() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(Constant.BAIDUMP3_SONG_LIST_URL).userAgent("Mozilla").timeout(6000).get();
                    Elements titles = doc.getElementsByClass("title-link");
                    Elements pics = doc.getElementsByClass("cover-img");

                    for (int i = 0; i < titles.size(); i++) {
                        String title=titles.get(i).text();
                        String strUrl=pics.get(i).attr("src");
                        list.add(new SonglistSearchBean(strUrl,title));
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
