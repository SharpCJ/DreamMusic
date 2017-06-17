package com.example.sharpcj.dreammusic.module.musichall.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.sharpcj.dreammusic.MyApplication;
import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2016/7/17.
 */
public class MusicHallFragment extends BaseFragment {

    private TabLayout mtabLayoutMusicHall;
    private ViewPager mvpMusicHall;
    private List<Fragment> mlstFrgaments;
    private String[] titles = {"精选", "推荐歌单", "热歌榜", "新歌榜", "经典老歌榜", "网络歌曲榜"};


    @Override
    protected int setViewId() {
        return R.layout.fragment_musichall;
    }

    @Override
    protected void findViews(View view) {
        mvpMusicHall = (ViewPager) view.findViewById(R.id.vpMusicHall);
        mtabLayoutMusicHall = (TabLayout) view.findViewById(R.id.tabLayoutMusicHall);

    }

    @Override
    protected void init() {
        mlstFrgaments = new ArrayList<>();

        SelectionFragment selectionFragment = new SelectionFragment();
        SongListFragment songListFragment = new SongListFragment();

        HotMusicFragment hotMusicFragment = new HotMusicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        hotMusicFragment.setArguments(bundle);

        HotMusicFragment newMusicFragment = new HotMusicFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type", 1);
        newMusicFragment.setArguments(bundle2);

        HotMusicFragment oldMusicFragment = new HotMusicFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("type", 22);
        oldMusicFragment.setArguments(bundle3);

        HotMusicFragment netMusicFragment = new HotMusicFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putInt("type", 25);
        netMusicFragment.setArguments(bundle4);




        mlstFrgaments.add(selectionFragment);
        mlstFrgaments.add(songListFragment);
        mlstFrgaments.add(hotMusicFragment);
        mlstFrgaments.add(newMusicFragment);
        mlstFrgaments.add(oldMusicFragment);
        mlstFrgaments.add(netMusicFragment);

    }

    @Override
    protected void initViews() {
        mvpMusicHall.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mlstFrgaments.get(position);
            }

            @Override
            public int getCount() {
                return mlstFrgaments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        mtabLayoutMusicHall.setTabMode(TabLayout.MODE_SCROLLABLE);
        mtabLayoutMusicHall.setSelectedTabIndicatorColor(Color.parseColor("#31C27C"));
        mtabLayoutMusicHall.setTabTextColors(Color.parseColor("#9A9A9A"), Color.parseColor("#31C27C"));
        mtabLayoutMusicHall.setupWithViewPager(mvpMusicHall);
    }

    @Override
    protected void initEvents() {
        mvpMusicHall.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position != 0 && position != 1) {
                    MyApplication.lstNetSong.clear();
                    MyApplication.lstNetSong.addAll(((HotMusicFragment) mlstFrgaments.get(position)).getMlstNetSong());
                    ((HotMusicFragment) mlstFrgaments.get(position)).getmAdapter().notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
