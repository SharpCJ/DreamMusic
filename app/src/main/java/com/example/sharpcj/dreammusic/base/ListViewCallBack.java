package com.example.sharpcj.dreammusic.base;

import java.util.Objects;

/**
 * 在线程中刷新listview用到的回调
 * Created by joy on 2016/6/27.
 */
public interface ListViewCallBack {
    /**
     * 更新listview的条目
     * @param object listView更新到的数据的集合
     */
    public void updataListView(Object object);
}
