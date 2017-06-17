package com.example.sharpcj.dreammusic.module.musichall.ui;

import android.view.View;
import android.widget.Toast;

import com.example.sharpcj.dreammusic.MyApplication;
import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.base.BaseFragment;
import com.example.sharpcj.dreammusic.base.MusicCallBack;
import com.example.sharpcj.dreammusic.base.ParseJsonCompleteCallback;
import com.example.sharpcj.dreammusic.jiuguoutils.BaiduMusicUtils;
import com.example.sharpcj.dreammusic.jiuguoutils.PlayerController;
import com.example.sharpcj.dreammusic.jiuguoutils.beans.BillBoardSearchResult;
import com.example.sharpcj.dreammusic.module.musichall.adapter.HotRecyclerViewAdapter;
import com.lidroid.xutils.BitmapUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2016/7/20.
 */
public class HotMusicFragment extends BaseFragment {

    private PullLoadMoreRecyclerView mhotRecyclerView;
    private HotRecyclerViewAdapter mAdapter;
    private BitmapUtils bitmapUtils;
    private int mType;
    public List<Object> mlstNetSong;

    public List<Object> getMlstNetSong() {
        return mlstNetSong;
    }

    public HotRecyclerViewAdapter getmAdapter() {
        return mAdapter;
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_hot_music;
    }

    @Override
    protected void findViews(View view) {
        mhotRecyclerView = (PullLoadMoreRecyclerView) view.findViewById(R.id.hotRecyclerView);
    }

    @Override
    protected void init() {
        mlstNetSong = new ArrayList<>();
        mType = getArguments().getInt("type");
        bitmapUtils = new BitmapUtils(getActivity());
        mAdapter = new HotRecyclerViewAdapter(getActivity(),mlstNetSong,bitmapUtils, new MusicCallBack() {
            @Override
            public void clickItem(int position, int viewFlag) {
                switch (viewFlag) {
                    case 0:
                        //播放歌曲
                        MyApplication.netPositionInlist = position;
                        PlayerController.playBillBroadSong(HotMusicFragment.this.getActivity(), position);
                        break;
                    case 1:
                        //弹出菜单
                        break;
                }
            }
        });
    }


    @Override
    protected void initViews() {
        mhotRecyclerView.setLinearLayout();
        mhotRecyclerView.setAdapter(mAdapter);
        mhotRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                getHotBillboardSong(0, true);
                MyApplication.lstNetSong.clear();
                MyApplication.lstNetSong.addAll(mlstNetSong);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoadMore() {
                if (mType == 1 && mlstNetSong.size() == 100) {
                    Toast.makeText(HotMusicFragment.this.getActivity(), "已加载完全部歌曲", Toast.LENGTH_SHORT).show();
                    mhotRecyclerView.setPullLoadMoreCompleted();
                } else if (mType == 2 && mlstNetSong.size() == 500) {
                    Toast.makeText(HotMusicFragment.this.getActivity(), "已加载完全部歌曲", Toast.LENGTH_SHORT).show();
                    mhotRecyclerView.setPullLoadMoreCompleted();
                } else {
                    getHotBillboardSong(mlstNetSong.size(), false);
                    MyApplication.lstNetSong.addAll(mlstNetSong);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void loadData() {
        getHotBillboardSong(0, true);
        mAdapter.notifyDataSetChanged();
    }

    protected void getHotBillboardSong(int offset, final boolean isRefresh) {
        BaiduMusicUtils.getMusicFromBillBroad(mType, 20, offset, new ParseJsonCompleteCallback() {
            @Override
            public void useData(Object object) {
                if (object != null) {
                    List<BillBoardSearchResult.SongListBean> list = (List<BillBoardSearchResult.SongListBean>) object;
                    if (isRefresh) {
                        mlstNetSong.clear();
                    }
                    mlstNetSong.addAll(list);
                    mhotRecyclerView.setPullLoadMoreCompleted();
                }
            }
        });
    }
}
