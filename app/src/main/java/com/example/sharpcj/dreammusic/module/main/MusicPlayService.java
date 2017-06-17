package com.example.sharpcj.dreammusic.module.main;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.example.sharpcj.dreammusic.MyApplication;
import com.example.sharpcj.dreammusic.jiuguoutils.PlayerController;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by joy on 2016/7/19.
 */
public class MusicPlayService extends Service {

    private MediaPlayer mediaPlayer;
    private Timer timer;
    private boolean alreadyPlay;
    private LocalBroadcastManager localBroadcastManager;
    private Mp3CommandReceiver mp3CommandReceiver;
    private String songName;
    private String artistName;
    private String album;
    private String albumUrl;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        MyApplication.alreadyPlaying = true;
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        mp3CommandReceiver = new Mp3CommandReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.mp3palyer.seek");
        intentFilter.addAction("com.example.joy.music.playorpause");
        localBroadcastManager.registerReceiver(mp3CommandReceiver, intentFilter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                String songUrl = bundle.getString("songUrl");
                songName = bundle.getString("title");
                artistName = bundle.getString("artist");
                album = bundle.getString("album");
                albumUrl = bundle.getString("albumUrl");
                mediaPlayer.reset();
                mediaPlayer.setDataSource(this, Uri.parse(songUrl));
                mediaPlayer.prepare();
                mediaPlayer.start();
                MyApplication.isPlayerPlaying = true;
                alreadyPlay = true;
                sendUI();//发送标题等信息
                startGetProgress();
            } else {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    MyApplication.isPlayerPlaying = false;
                } else if (alreadyPlay == true) {
                    mediaPlayer.start();
                    MyApplication.isPlayerPlaying = true;
                    startGetProgress();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (MyApplication.isLocalPlaying) {
                    PlayerController.playNextLocalSong(getApplication());
                } else if (MyApplication.isNetPlaying) {
                    PlayerController.playBillBroadNextSong(getApplication());
                }
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {

            }
        });

        return START_NOT_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }

    private void sendUI() {
        Intent intent = new Intent("com.example.joy.music.updataui");
        Bundle bundle = new Bundle();
        bundle.putString("songName", songName);
        bundle.putString("artistName", artistName);
        bundle.putString("album", album);
        bundle.putString("albumUrl", albumUrl);
        intent.putExtras(bundle);
        localBroadcastManager.sendBroadcast(intent);
    }

    private void startGetProgress() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    //获取进度:音乐正在播放的话才获取进度
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        //获得进度
                        int currentPosition = mediaPlayer.getCurrentPosition();
                        int duration = mediaPlayer.getDuration();
                        Intent intent = new Intent("com.example.joy.music.updataprogress");
                        Bundle bundle = new Bundle();
                        bundle.putInt("currentPosition", currentPosition);
                        bundle.putInt("duration", duration);
                        intent.putExtras(bundle);
                        localBroadcastManager.sendBroadcast(intent);
                    } else {
                        //停止定时任务
                        timer.cancel();
                        timer = null;
                    }
                }
            }, 0, 1000);
        }
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();//一定要在程序退出之前，释放资源，否则会内存泄漏
        }
        localBroadcastManager.unregisterReceiver(mp3CommandReceiver);
        super.onDestroy();
    }


    class Mp3CommandReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case "com.mp3palyer.seek":
                    int seek = intent.getIntExtra("seek", 0);
                    if (mediaPlayer != null)
                        mediaPlayer.seekTo(seek);
                    break;
            }
        }
    }

}
