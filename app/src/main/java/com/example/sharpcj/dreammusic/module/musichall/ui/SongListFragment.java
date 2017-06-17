package com.example.sharpcj.dreammusic.module.musichall.ui;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.base.BaseFragment;
import com.example.sharpcj.dreammusic.base.ParseJsonCompleteCallback;
import com.example.sharpcj.dreammusic.module.musichall.adapter.SongListRecyclerViewAdapter;
import com.example.sharpcj.dreammusic.module.musichall.bean.SonglistSearchBean;
import com.example.sharpcj.dreammusic.module.musichall.dao.SongListDao;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2016/7/20.
 */
public class SongListFragment extends BaseFragment {

    private RecyclerView rvSongList;
    private SongListRecyclerViewAdapter mAdapter;
    private List<SonglistSearchBean> mlstSonglist;

    public SongListRecyclerViewAdapter getmAdapter() {
        return mAdapter;
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_song_list;
    }

    @Override
    protected void findViews(View view) {
        rvSongList = (RecyclerView) view.findViewById(R.id.rv_songlist);
    }

    @Override
    protected void init() {
        mlstSonglist=new ArrayList<>();
    }

    @Override
    protected void initViews() {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        rvSongList.setLayoutManager(gridLayoutManager);
        mAdapter=new SongListRecyclerViewAdapter(mlstSonglist,getActivity());
        rvSongList.setAdapter(mAdapter);
        rvSongList.addItemDecoration(new MyDecoration(10));
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void loadData() {
        getSongList();
    }

    private void getSongList(){
        new SongListDao().getSongList(new ParseJsonCompleteCallback() {
            @Override
            public void useData(Object object) {
                List<SonglistSearchBean> list= (List<SonglistSearchBean>) object;
                mlstSonglist.clear();
                mlstSonglist.addAll(list);
                SongListFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    class MyDecoration extends RecyclerView.ItemDecoration {
        private int miDecoration;

        public MyDecoration(int iDecoration) {
            miDecoration = iDecoration;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            outRect.bottom = miDecoration*2;
            outRect.right = miDecoration/2;
            outRect.left = miDecoration/2;
            outRect.top = miDecoration;
        }
    }
}
