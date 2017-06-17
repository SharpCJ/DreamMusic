package com.example.sharpcj.dreammusic.base;

/**
 * 数据解析完成之后的回调
 * Created by joy on 2016/7/18.
 */
public interface ParseJsonCompleteCallback {
    /**
     * 使用解析到的数据
     * @param object
     */
    public void useData(Object object);
}
