package com.example.sharpcj.dreammusic;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.example.sharpcj.dreammusic.jiuguoutils.LocalMusicUtils;
import com.example.sharpcj.dreammusic.jiuguoutils.beans.MediaStoreSong;
import com.example.sharpcj.dreammusic.module.main.ui.MainActivity;
import com.se7en.utils.SystemUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joy on 2016/7/16.
 */
public class MyApplication extends Application {
    public static List<Activity> activityList;
    public static List<MediaStoreSong> mlstMediaStoreSong;
    public static List<Object> lstNetSong;
    public static boolean isPlayerPlaying;
    public static boolean alreadyPlaying;
    public static int localPositionInlist;
    public static boolean isLocalPlaying;
    public static boolean isNetPlaying;
    public static int netPositionInlist;
    public static int degree;

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        SystemUtil.setContext(getApplicationContext());
        activityList=new ArrayList<>();
        lstNetSong=new ArrayList<>();
        //初始化
        initLstMediaStoreSong();
    }

    private void initLstMediaStoreSong() {
        mlstMediaStoreSong=new ArrayList<>();
        mlstMediaStoreSong.clear();
        mlstMediaStoreSong.addAll(LocalMusicUtils.getAllLocalSongInfo(this));
    }

    public static Context getContext(){
        return context;
    }
}
