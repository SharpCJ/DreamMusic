package com.example.sharpcj.dreammusic.module.search.ui;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.base.BaseFragment;
import com.example.sharpcj.dreammusic.base.ListViewCallBack;
import com.example.sharpcj.dreammusic.module.search.dao.SearchDao;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2016/7/17.
 */
public class SearchFagment extends BaseFragment {

    private TagFlowLayout mFlowLayout;
    private List<String> mlstHotSearch;
    private TagAdapter mTagAdapter;

    @Override
    protected int setViewId() {
        return R.layout.fragment_search;
    }

    @Override
    protected void findViews(View view) {
        mFlowLayout = (TagFlowLayout) view.findViewById(R.id.id_flowlayout);
    }

    @Override
    protected void init() {
        initLstHot();
        mTagAdapter=new TagAdapter<String>(mlstHotSearch) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.fragment_search_flowlayout_tv,
                        mFlowLayout, false);
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(8,10,8,10);
                tv.setLayoutParams(params);
                tv.setPadding(10,8,10,8);
                tv.setText(s);
                tv.setTextSize(16);
                tv.setTextColor(Color.rgb(0,0,0));
                return tv;
            }
        };
    }

    @Override
    protected void initViews() {
        mFlowLayout.setAdapter(mTagAdapter);
        mTagAdapter.setSelectedList(0);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void loadData() {

    }

    private void initLstHot() {
        mlstHotSearch = new ArrayList<>();
        SearchDao.getHotSearch(new ListViewCallBack() {
            @Override
            public void updataListView(Object object) {
                if(object!=null){
                    List<String> datas= (List<String>) object;
                    mlstHotSearch.clear();
                    mlstHotSearch.addAll(datas);
                    mTagAdapter.notifyDataChanged();
                }
            }
        });
    }


}
