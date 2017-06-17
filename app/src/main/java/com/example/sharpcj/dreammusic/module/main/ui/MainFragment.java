package com.example.sharpcj.dreammusic.module.main.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.base.BaseFragment;
import com.example.sharpcj.dreammusic.module.more.ui.MoreFragment;
import com.example.sharpcj.dreammusic.module.musichall.ui.MusicHallFragment;
import com.example.sharpcj.dreammusic.module.mymusic.ui.MyMusicFragment;
import com.example.sharpcj.dreammusic.module.search.ui.SearchFagment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2016/7/18.
 */
public class MainFragment extends BaseFragment {

    private TextView mtvTitle;//导航页标题
    private RadioGroup mrgTitle;//导航页radiogroup
    private RadioButton mrbMymusic;
    private RadioButton mrbMusichall;
    private RadioButton mrbMore;
    private RadioButton mrbSearch;
    private ViewPager mvpMain;
    private List<Fragment> mlstFragments;

    @Override
    protected int setViewId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void findViews(View view) {
        mtvTitle = (TextView) view.findViewById(R.id.tvTitle);
        mrgTitle = (RadioGroup) view.findViewById(R.id.rgTitle);
        mrbMymusic = (RadioButton) view.findViewById(R.id.rbMymusic);
        mrbMusichall = (RadioButton) view.findViewById(R.id.rbMusichall);
        mrbMore = (RadioButton) view.findViewById(R.id.rbMore);
        mrbSearch = (RadioButton) view.findViewById(R.id.rbSearch);
        mvpMain = (ViewPager) view.findViewById(R.id.vpMain);
    }

    @Override
    protected void init() {
        mrbMymusic.setChecked(true);
        initVPMain();//初始化 mvpMain
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initEvents() {
        mrgTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbMymusic:
                        mtvTitle.setText("我的音乐");
                        mvpMain.setCurrentItem(0,true);
                        break;
                    case R.id.rbMusichall:
                        mtvTitle.setText("音乐馆");
                        mvpMain.setCurrentItem(1,true);
                        break;
                    case R.id.rbMore:
                        mtvTitle.setText("更多");
                        mvpMain.setCurrentItem(2,true);
                        break;
                    case R.id.rbSearch:
                        mtvTitle.setText("搜索");
                        mvpMain.setCurrentItem(3,true);
                        break;
                }
            }
        });

        mvpMain.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 0:
                        mrbMymusic.setChecked(true);
                        break;
                    case 1:
                        mrbMusichall.setChecked(true);
                        break;
                    case 2:
                        mrbMore.setChecked(true);
                        break;
                    case 3:
                        mrbSearch.setChecked(true);
                        break;
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }

    private void initVPMain() {
        mlstFragments=new ArrayList<>();
        mlstFragments.add(new MyMusicFragment());
        mlstFragments.add(new MusicHallFragment());
        mlstFragments.add(new MoreFragment());
        mlstFragments.add(new SearchFagment());
        FragmentPagerAdapter adapter=new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mlstFragments.get(position);
            }

            @Override
            public int getCount() {
                return mlstFragments.size();
            }
        };
        mvpMain.setAdapter(adapter);
    }
}
