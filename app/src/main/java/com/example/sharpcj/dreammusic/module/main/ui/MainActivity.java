package com.example.sharpcj.dreammusic.module.main.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharpcj.dreammusic.MyApplication;
import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.base.BaseActivity;
import com.example.sharpcj.dreammusic.common.widget.CircleImageView;
import com.example.sharpcj.dreammusic.jiuguoutils.PlayerController;
import com.example.sharpcj.dreammusic.module.main.MusicPlayService;

public class MainActivity extends BaseActivity {

    private FrameLayout mMainFramLayout;
    private MainFragment mMainFragment;
    public CircleImageView mivSingerPhoto;
    public TextView mtvSongTitle;
    public TextView mtvLyric;
    public ImageView mivPlayOrPause;

    private LocalBroadcastManager localBroadcastManager;
    private MyUpdataUIReceiver myReceiver;

    private int currentPosition;
    private int duration;
    private String mCurrentSongTitle;
    private String mCurrentSongSinger;
    private String mCurrentSongAlbum;
    private String mCurrentAlbumUrl;


    public MainFragment getmMainFragment() {
        return mMainFragment;
    }

    @Override
    protected int setViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        mMainFramLayout = (FrameLayout) findViewById(R.id.mainFramLayout);
        mivSingerPhoto = (CircleImageView) findViewById(R.id.ivSingerPhoto);
        mtvSongTitle = (TextView) findViewById(R.id.tvSongTitle);
        mtvLyric = (TextView) findViewById(R.id.tvLyric);
        mivPlayOrPause = (ImageView) findViewById(R.id.ivPlayOrPause);
    }

    @Override
    protected void init() {

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        myReceiver=new MyUpdataUIReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.joy.music.updataprogress");
        filter.addAction("com.example.joy.music.updataui");
        localBroadcastManager.registerReceiver(myReceiver, filter);

        mivPlayOrPause.setEnabled(MyApplication.alreadyPlaying);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mMainFragment = new MainFragment();
        transaction.add(R.id.mainFramLayout, mMainFragment);
        transaction.commit();
    }

    @Override
    protected void initEvents() {
        mivPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isPlayerPlaying) {
                    //暂停掉
                    mivPlayOrPause.setImageResource(R.mipmap.landscape_player_btn_play_normal);
                    mivSingerPhoto.roatatePause();
                    MyApplication.isPlayerPlaying = false;
                } else {
                    //播放
                    mivPlayOrPause.setImageResource(R.mipmap.landscape_player_btn_pause_normal);
                    mivSingerPhoto.roatateStart();
                    MyApplication.isPlayerPlaying = true;
                }
//                localBroadcastManager.sendBroadcast(new Intent("com.example.joy.music.playorpause"));
                startService(new Intent(MainActivity.this,MusicPlayService.class));
            }
        });
        mivSingerPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                intent.putExtra("currentPosition", currentPosition);
                intent.putExtra("duration", duration);
                intent.putExtra("songTitle", mCurrentSongTitle);
                intent.putExtra("songSinger", mCurrentSongSinger);
                intent.putExtra("albumUrl", mCurrentAlbumUrl);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mivSingerPhoto.setRoatate(MyApplication.degree);
        mivPlayOrPause.setImageResource(MyApplication.isPlayerPlaying ? R.mipmap.landscape_player_btn_pause_normal : R.mipmap.landscape_player_btn_play_normal);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        localBroadcastManager.unregisterReceiver(myReceiver);
        mivSingerPhoto.resetRoatate();
        super.onDestroy();
    }

    class MyUpdataUIReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch(action){
                case "com.example.joy.music.updataprogress":
                    Bundle bundle = intent.getExtras();
                    currentPosition = bundle.getInt("currentPosition", 0);
                    duration = bundle.getInt("duration", 0);
                    mivPlayOrPause.setImageResource(R.mipmap.landscape_player_btn_pause_normal);
                    mivSingerPhoto.roatateStart();
                    break;
                case "com.example.joy.music.updataui":
                    Bundle bundle2 = intent.getExtras();
                    mCurrentSongTitle = bundle2.getString("songName", "未知");
                    mCurrentSongSinger = bundle2.getString("artistName", "未知");
                    mCurrentSongAlbum = bundle2.getString("album", "未知");
                    mCurrentAlbumUrl = bundle2.getString("albumUrl", "");
                    mtvSongTitle.setText(mCurrentSongTitle);
                    mtvLyric.setText(mCurrentSongSinger + "·" + mCurrentSongAlbum);
                    mivPlayOrPause.setEnabled(true);
                    break;
            }

        }
    }
}
