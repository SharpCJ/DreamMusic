package com.example.sharpcj.dreammusic.module.search.dao;

import android.os.AsyncTask;

import com.example.sharpcj.dreammusic.base.ListViewCallBack;
import com.example.sharpcj.dreammusic.common.constant.Constant;
import com.example.sharpcj.dreammusic.common.net.HttpNet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2016/7/18.
 */
public class SearchDao {
    public static void getHotSearch(final ListViewCallBack callBack){
        new AsyncTask<Void,Void,List<String>>(){

            @Override
            protected void onPostExecute(List<String> strings) {
                if(callBack!=null) {
                    callBack.updataListView(strings);
                }
            }

            @Override
            protected List<String> doInBackground(Void... params) {
                try {
                    List<String> list=new ArrayList<>();
                    Document doc = Jsoup.connect(Constant.BAIDUMP3_HOME_URL).userAgent("Mozilla").timeout(6000).get();
                    Elements hot_searchs = doc.getElementsByClass("hot-search");
                    Elements hotsearch_lis = hot_searchs.get(0).children();
                    for (int i = 0; i < 12; i++) {
                        Element hotsearch = hotsearch_lis.get(i).child(0);
                        String keyword = hotsearch.text();
                        list.add(keyword);
                    }
                    return list;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
