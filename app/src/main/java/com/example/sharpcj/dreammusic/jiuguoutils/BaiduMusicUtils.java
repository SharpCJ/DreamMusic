package com.example.sharpcj.dreammusic.jiuguoutils;

import android.util.Log;

import com.example.sharpcj.dreammusic.jiuguoutils.beans.BillBoardSearchResult;
import com.example.sharpcj.dreammusic.jiuguoutils.beans.KeyWordSearchResult;
import com.example.sharpcj.dreammusic.jiuguoutils.beans.SingerInfo;
import com.example.sharpcj.dreammusic.base.NetCallBack;
import com.example.sharpcj.dreammusic.base.ParseJsonCompleteCallback;
import com.example.sharpcj.dreammusic.common.constant.Constant;
import com.example.sharpcj.dreammusic.common.net.HttpNet;
import com.example.sharpcj.dreammusic.jiuguoutils.beans.SongInfoBySongId;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joy on 2016/7/18.
 */
public class BaiduMusicUtils {
    /**
     * 根据关键字搜索
     * @param keyword
     * @param callback
     */
    public static void getMusicByKeyWorlds(String keyword, final ParseJsonCompleteCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("from", "webapp_music");
        map.put("method", "baidu.ting.search.catalogSug");
        map.put("format", "json");
        map.put("callback", "");
        try {
            String key = URLEncoder.encode(keyword, "UTF-8");
            map.put("query",key);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpNet.doHttpRequest("POST", Constant.BAIDUMP3_REQUEST_URL, map, new NetCallBack() {
            @Override
            public void success(String strResult) {
                Gson gson = new Gson();
                KeyWordSearchResult keyWordSearchResult = gson.fromJson(strResult, KeyWordSearchResult.class);
                if (callback != null) {
                    callback.useData(keyWordSearchResult.getSong());
                }
            }

            @Override
            public void fail(String strMsg) {
                if(callback!=null) {
                    callback.useData(null);
                }
            }
        });
    }

    /**
     * 搜索歌曲榜单 type ://1、新歌榜，2、热歌榜，11、摇滚榜，12、爵士，16、流行
     * 21、欧美金曲榜，22、经典老歌榜，23、情歌对唱榜，24、影视金曲榜，25、网络歌曲榜
     * @param type
     * @param size
     * @param callback
     */
    public static void getMusicFromBillBroad(int type,int size,int offset,final ParseJsonCompleteCallback callback) {
        Map<String, String> map = new HashMap<>();

        map.put("method","baidu.ting.billboard.billList");
        map.put("format", "json");
        map.put("callback","");
        map.put("type",type+"");
        map.put("size",size+"");
        map.put("offset",offset+"");

        HttpNet.doHttpRequest("POST", Constant.BAIDUMP3_REQUEST_URL, map, new NetCallBack() {
            @Override
            public void success(String strResult) {
                Gson gson = new Gson();
                BillBoardSearchResult billBoardSearchResult = gson.fromJson(strResult,BillBoardSearchResult.class);
                if (callback != null) {
                    callback.useData(billBoardSearchResult.getSong_list());
                }
            }

            @Override
            public void fail(String strMsg) {
                if(callback!=null) {
                    callback.useData(null);
                }
            }
        });
    }

    /**
     * 通过歌曲Id获取歌词
     * @param songId
     * @param num
     * @param callback
     */
    public static void getLyricBySongId(String songId,int num,final ParseJsonCompleteCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("method","baidu.ting.song.lry");
        map.put("format", "json");
        map.put("callback","");
        map.put("id",songId);
        map.put("num",num+"");
        HttpNet.doHttpRequest("GET", Constant.BAIDUMP3_REQUEST_URL, map, new NetCallBack() {
            @Override
            public void success(String strResult) {
                if(callback!=null) {
                    callback.useData(strResult);
                }
            }

            @Override
            public void fail(String strMsg) {
                if(callback!=null) {
                    callback.useData(null);
                }
            }
        });
    }

    /**
     * 根据歌手id获取歌手信息
     * @param tinguid 歌手id
     * @param callback
     */
    public static void getSingerInfoBySingerId(String tinguid,final ParseJsonCompleteCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("method","baidu.ting.artist.getInfo");
        map.put("format", "json");
        map.put("callback","");
        map.put("tinguid",tinguid);
        HttpNet.doHttpRequest("GET", Constant.BAIDUMP3_REQUEST_URL, map, new NetCallBack() {
            @Override
            public void success(String strResult) {
                Gson gson=new Gson();
                SingerInfo singer = gson.fromJson(strResult, SingerInfo.class);
                if(callback!=null) {
                    callback.useData(singer);
                }
            }

            @Override
            public void fail(String strMsg) {
                if(callback!=null) {
                    callback.useData(null);
                }
            }
        });
    }

    public static void getSongInfoBySongId(final String songId, final ParseJsonCompleteCallback callback){
        Map<String, String> map = new HashMap<>();
        map.put("songIds",songId);
        map.put("format", "json");
        map.put("callback","");
        HttpNet.doHttpRequest("POST", "http://music.baidu.com/data/music/links", map, new NetCallBack() {
            @Override
            public void success(String strResult) {
                Log.d("TAG",strResult);
                Gson gson=new Gson();
                SongInfoBySongId songInfoBySongId = gson.fromJson(strResult, SongInfoBySongId.class);
                SongInfoBySongId.DataBean.SongListBean songListBean = songInfoBySongId.getData().getSongList().get(0);
                if(callback!=null){
                    callback.useData(songListBean);
                }
            }

            @Override
            public void fail(String strMsg) {
                if(callback!=null) {
                    callback.useData(null);
                }
            }
        });
    }

}
