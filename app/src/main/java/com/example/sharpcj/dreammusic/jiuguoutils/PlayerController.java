package com.example.sharpcj.dreammusic.jiuguoutils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.sharpcj.dreammusic.MyApplication;
import com.example.sharpcj.dreammusic.base.ParseJsonCompleteCallback;
import com.example.sharpcj.dreammusic.common.constant.Constant;
import com.example.sharpcj.dreammusic.jiuguoutils.beans.BillBoardSearchResult;
import com.example.sharpcj.dreammusic.jiuguoutils.beans.MediaStoreSong;
import com.example.sharpcj.dreammusic.jiuguoutils.beans.SongInfoBySongId;
import com.example.sharpcj.dreammusic.module.main.MusicPlayService;
import com.se7en.utils.SystemUtil;

import java.util.List;
import java.util.Random;

/**
 * Created by joy on 2016/7/19.
 */
public class PlayerController {
    public static void notifyServicePlayLocalSong(Context context, MediaStoreSong song) {
        Intent intent = new Intent(context, MusicPlayService.class);
        Bundle bundle = new Bundle();
        bundle.putString("songUrl", song.getUrl());
        bundle.putString("title", song.getTitle());
        bundle.putString("album", song.getAlbum());
        bundle.putString("artist", song.getArtist());
        intent.putExtras(bundle);
        context.startService(intent);
        MyApplication.isPlayerPlaying = true;
        MyApplication.isLocalPlaying = true;
        MyApplication.isNetPlaying = false;
    }

    /**
     * 播放本地某首歌
     *
     * @param context
     * @param position
     */
    public static void playLocalSong(Context context, int position) {
        MediaStoreSong song = MyApplication.mlstMediaStoreSong.get(position);
        notifyServicePlayLocalSong(context, song);
    }

    /**
     * 通知服务播放本地列表下一首
     *
     * @param context
     */
    public static void playNextLocalSong(Context context) {
        int playMode = SystemUtil.getSharedInt(Constant.PLAY_MODE, 0);
        if (playMode == 0) {
            if (MyApplication.localPositionInlist == MyApplication.mlstMediaStoreSong.size() - 1) {
                MyApplication.localPositionInlist = 0;
            } else {
                MyApplication.localPositionInlist++;
            }
        } else if (playMode == 2) {
            Random rd = new Random();
            MyApplication.localPositionInlist = rd.nextInt(MyApplication.mlstMediaStoreSong.size());
        }
        playLocalSong(context, MyApplication.localPositionInlist);
    }

    /**
     * 通知服务播放列表上一首
     *
     * @param context
     */
    public static void playLastLocalSong(Context context) {
        int playMode = SystemUtil.getSharedInt(Constant.PLAY_MODE, 0);
        if (playMode == 0) {
            if (MyApplication.localPositionInlist == 0) {
                MyApplication.localPositionInlist = MyApplication.mlstMediaStoreSong.size() - 1;
            } else {
                MyApplication.localPositionInlist--;
            }
        } else if (playMode == 2) {
            Random rd = new Random();
            MyApplication.localPositionInlist = rd.nextInt(MyApplication.mlstMediaStoreSong.size());
        }
        playLocalSong(context, MyApplication.localPositionInlist);
    }


    /**
     * 播放某首网络歌曲
     *
     * @param context
     * @param song
     */
    public static void notifyServicePlayNetSong(Context context,
                                                SongInfoBySongId.DataBean.SongListBean song) {
        Intent intent = new Intent(context, MusicPlayService.class);
        Bundle bundle = new Bundle();
        bundle.putString("songUrl", song.getSongLink());
        bundle.putString("title", song.getSongName());
        bundle.putString("album", song.getAlbumName());
        bundle.putString("artist", song.getArtistName());
        bundle.putString("albumUrl", song.getSongPicRadio());
        intent.putExtras(bundle);
        context.startService(intent);
        MyApplication.isPlayerPlaying = true;
        MyApplication.isLocalPlaying = false;
        MyApplication.isNetPlaying = true;
    }

    /**
     * 播放排行榜上的某首歌
     *
     * @param context
     * @param position
     */
    public static void playBillBroadSong(final Context context,int position) {
        String song_id = ((BillBoardSearchResult.SongListBean) (MyApplication.lstNetSong.get(position))).getSong_id();
        BaiduMusicUtils.getSongInfoBySongId(song_id, new ParseJsonCompleteCallback() {
            @Override
            public void useData(Object object) {
                if (object != null) {
                    SongInfoBySongId.DataBean.SongListBean song = (SongInfoBySongId.DataBean.SongListBean) object;
                    notifyServicePlayNetSong(context, song);
                }
            }
        });
    }

    /**
     * 播放排行榜上的上一曲某首歌
     */
    public static void playBillBroadLastSong(Context context) {
        int playMode = SystemUtil.getSharedInt(Constant.PLAY_MODE, 0);
        if (playMode == 0) {
            if (MyApplication.netPositionInlist == 0) {
                MyApplication.netPositionInlist = MyApplication.lstNetSong.size() - 1;
            } else {
                MyApplication.netPositionInlist--;
            }
        } else if (playMode == 2) {
            Random rd = new Random();
            MyApplication.netPositionInlist = rd.nextInt(MyApplication.lstNetSong.size());
        }
        playBillBroadSong(context, MyApplication.netPositionInlist);
    }

    /**
     * 播放排行榜上的下一曲某首歌
     */
    public static void playBillBroadNextSong(Context context) {
        int playMode = SystemUtil.getSharedInt(Constant.PLAY_MODE, 0);
        if (playMode == 0) {
            if (MyApplication.netPositionInlist == MyApplication.lstNetSong.size() - 1) {
                MyApplication.netPositionInlist = 0;
            } else {
                MyApplication.netPositionInlist++;
            }
        } else if (playMode == 2) {
            Random rd = new Random();
            MyApplication.netPositionInlist = rd.nextInt(MyApplication.lstNetSong.size());
        }
        playBillBroadSong(context,MyApplication.netPositionInlist);
    }
}
