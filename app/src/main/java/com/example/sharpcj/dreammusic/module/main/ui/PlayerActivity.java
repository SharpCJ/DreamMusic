package com.example.sharpcj.dreammusic.module.main.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.sharpcj.dreammusic.MyApplication;
import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.base.Base2Activity;
import com.example.sharpcj.dreammusic.jiuguoutils.PlayerController;
import com.example.sharpcj.dreammusic.module.main.MusicPlayService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerActivity extends Base2Activity {

    private String mCurrentalbumUrl;

    public interface ChangeAlbumImageStateListener{
        public void change();
        public void downAlbum(String imgUrl);
    }

    private ImageView mivback;
    private TextView mtvSongTitle;
    private TextView mtvSingerTitle;
    private ImageView mivPlayOrPause;
    private ImageView mivNext;
    private ImageView mivLast;
    private int currentPosition;
    private int duration;
    private TextView mtvCurrentPosition;
    private TextView mtvDuration;
    private SeekBar mseekBar;
    private String title;
    private LocalBroadcastManager localBroadcastManager;
    private MyUpdataUIReceiver myUpdataUIReceiver;
    private ViewPager vpPlayer;
    private List<Fragment> mlstFragments;

    private ChangeAlbumImageStateListener listener;

    public void setListener(ChangeAlbumImageStateListener listener) {
        this.listener = listener;
    }

    @Override
    protected int setViewId() {
        return R.layout.activity_player;
    }

    @Override
    protected void findViews() {
        mtvSongTitle = (TextView) findViewById(R.id.tvSongTitle);
        mtvSingerTitle = (TextView) findViewById(R.id.tvSingerTitle);
        mivback = (ImageView) findViewById(R.id.ivback);

        mivPlayOrPause = (ImageView) findViewById(R.id.ivPlay);
        mivNext = (ImageView) findViewById(R.id.ivnext);
        mivLast = (ImageView) findViewById(R.id.ivlast);

        mtvCurrentPosition = (TextView) findViewById(R.id.tvCurrentPosition);
        mtvDuration = (TextView) findViewById(R.id.tvDuration);
        mseekBar = (SeekBar) findViewById(R.id.seekBar);

        vpPlayer = (ViewPager) findViewById(R.id.playerVP);

    }

    @Override
    protected void init() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        myUpdataUIReceiver=new MyUpdataUIReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.joy.music.updataprogress");
        filter.addAction("com.example.joy.music.updataui");
        localBroadcastManager.registerReceiver(myUpdataUIReceiver, filter);

        if ((!MyApplication.isPlayerPlaying) && (!MyApplication.alreadyPlaying)) {
            mivPlayOrPause.setEnabled(false);
        } else {
            mivPlayOrPause.setEnabled(true);
        }

        Intent intent = getIntent();
        duration = intent.getIntExtra("duration", 0);
        currentPosition = intent.getIntExtra("currentPosition", 0);
        String songTitle= intent.getStringExtra("songTitle");
        String singerName= intent.getStringExtra("songSinger");
        mCurrentalbumUrl = intent.getStringExtra("albumUrl");
        mtvSongTitle.setText(songTitle);
        mtvSingerTitle.setText(singerName);

        mtvCurrentPosition.setText(new SimpleDateFormat("mm:ss").format(new Date(currentPosition)) + "");
        mtvDuration.setText(new SimpleDateFormat("mm:ss").format(new Date(duration)) + "");
        mseekBar.setMax(duration);
        mseekBar.setProgress(currentPosition);
        mivPlayOrPause.setImageResource(MyApplication.isPlayerPlaying==true?R.mipmap.cz8:R.mipmap.cz6);

        mlstFragments=new ArrayList<>();
        AlbumFragment albumFragment = new AlbumFragment();
        Bundle bundle=new Bundle();
        bundle.putString("albumUrl",mCurrentalbumUrl);
        albumFragment.setArguments(bundle);
        mlstFragments.add(albumFragment);
        mlstFragments.add(new LyricFragment());

    }

    @Override
    protected void initEvents() {
        mivback.setOnClickListener(this);
        mivPlayOrPause.setOnClickListener(this);
        mivNext.setOnClickListener(this);
        mivLast.setOnClickListener(this);
        mseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int seek = seekBar.getProgress();
                Intent seekIntent = new Intent("com.mp3palyer.seek");
                Bundle bundle = new Bundle();
                bundle.putInt("seek",seek);
                seekIntent.putExtras(bundle);
                LocalBroadcastManager.getInstance(PlayerActivity.this).sendBroadcast(seekIntent);
            }
        });

        vpPlayer.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mlstFragments.get(position);
            }

            @Override
            public int getCount() {
                return mlstFragments.size();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivback:
                PlayerActivity.this.onBackPressed();
                break;
            case R.id.ivPlay:
                if (MyApplication.isPlayerPlaying) {
                    //暂停掉
                    mivPlayOrPause.setImageResource(R.mipmap.cz6);
                    MyApplication.isPlayerPlaying = false;

                } else {
                    mivPlayOrPause.setImageResource(R.mipmap.cz8);
                    MyApplication.isPlayerPlaying = true;
                }
                if(listener!=null){
                    listener.change();
                }
                startService(new Intent(PlayerActivity.this,MusicPlayService.class));
                break;
            case R.id.ivnext:  //下一曲
                if(MyApplication.isLocalPlaying) {
                    PlayerController.playNextLocalSong(PlayerActivity.this);
                }else if(MyApplication.isNetPlaying){
                    PlayerController.playBillBroadNextSong(PlayerActivity.this);
                }
                break;
            case R.id.ivlast:  //上一曲
                if (MyApplication.isLocalPlaying) {
                    PlayerController.playLastLocalSong(PlayerActivity.this);
                }else if(MyApplication.isNetPlaying){
                    PlayerController.playBillBroadLastSong(PlayerActivity.this);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        localBroadcastManager.unregisterReceiver(myUpdataUIReceiver);
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
                    mseekBar.setProgress(currentPosition);
                    mseekBar.setMax(duration);
                    mtvCurrentPosition.setText(new SimpleDateFormat("mm:ss").format(new Date(currentPosition)) + "");
                    mtvDuration.setText(new SimpleDateFormat("mm:ss").format(new Date(duration)) + "");
                    mivPlayOrPause.setImageResource(R.mipmap.cz8);
                    break;
                case "com.example.joy.music.updataui":
                    Bundle bundle2 = intent.getExtras();
                    String songName = bundle2.getString("songName", "未知");
                    String singerName = bundle2.getString("artistName", "未知");
                    mCurrentalbumUrl = bundle2.getString("albumUrl", "");
                    mtvSongTitle.setText(songName);
                    mtvSingerTitle.setText(singerName);
                    mivPlayOrPause.setEnabled(true);
                    if(!TextUtils.isEmpty(mCurrentalbumUrl)){
                        if(listener!=null){
                            listener.downAlbum(mCurrentalbumUrl);
                        }
                    }
                break;
            }

        }
    }

}
