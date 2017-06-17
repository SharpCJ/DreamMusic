package com.example.sharpcj.dreammusic.module.localmusic;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.base.BaseFragment;
import com.example.sharpcj.dreammusic.jiuguoutils.PlayCallback;
import com.example.sharpcj.dreammusic.jiuguoutils.beans.MediaStoreSong;
import com.example.sharpcj.dreammusic.module.main.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2016/7/18.
 */
public class LocalMusicFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mivBack;
    private TabLayout mTabLayout;
    private ViewPager mvpLocal;
    private List<Fragment> mlstFragment;
    private String[] titles;
    private PagerAdapter vpLocalAdapter;

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (vpLocalAdapter != null){
            vpLocalAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_localmusic;
    }

    @Override
    protected void findViews(View view) {
        mivBack = (ImageView) view.findViewById(R.id.back2mymusic);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mvpLocal = (ViewPager) view.findViewById(R.id.vpLocal);
    }

    @Override
    protected void init() {
        initVpLocal();
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mvpLocal);
        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#31C27C"));
        mTabLayout.setTabTextColors(Color.parseColor("#9A9A9A"),Color.parseColor("#31C27C"));
    }

    private void initVpLocal() {
        titles=new String[]{"歌曲","歌手","专辑"};
        mlstFragment=new ArrayList<>();
        LocalSongFragment localSongFragment = new LocalSongFragment();
        mlstFragment.add(localSongFragment);
        mlstFragment.add(new LocalSingerFragment());
        mlstFragment.add(new LocalAlbumFragment());
        vpLocalAdapter = new FragmentPagerAdapter(getChildFragmentManager()){

            @Override
            public int getCount() {
                return mlstFragment.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mlstFragment.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        };
        mvpLocal.setAdapter(vpLocalAdapter);
    }

    @Override
    protected void initViews() {
        mivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction mtransaction = getActivity().getSupportFragmentManager().beginTransaction();
                mtransaction.remove(LocalMusicFragment.this);
                mtransaction.show(((MainActivity) getActivity()).getmMainFragment());
                mtransaction.commit();
            }
        });
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {

    }
}
