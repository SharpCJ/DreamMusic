package com.example.sharpcj.dreammusic.module.main.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sharpcj.dreammusic.MyApplication;
import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.base.BaseFragment;
import com.example.sharpcj.dreammusic.common.widget.CircleImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by joy on 2016/7/22.
 */
public class AlbumFragment extends BaseFragment {

    private CircleImageView mAlbumImageView;


    @Override
    protected int setViewId() {
        return R.layout.fragment_album;
    }

    @Override
    protected void findViews(View view) {
        mAlbumImageView = (CircleImageView) view.findViewById(R.id.albumCircleImage);
    }

    @Override
    protected void init() {

        Bundle arguments = getArguments();
        String albumUrl = arguments.getString("albumUrl","");
        if(!TextUtils.isEmpty(albumUrl)) {
            Picasso.with(getActivity()).load(albumUrl).into(mAlbumImageView);
        }

        if (MyApplication.isPlayerPlaying) {
            mAlbumImageView.roatateStart();
        } else {
            mAlbumImageView.roatatePause();
        }

        ((PlayerActivity)getActivity()).setListener(new PlayerActivity.ChangeAlbumImageStateListener() {
            @Override
            public void change() {
                if (MyApplication.isPlayerPlaying) {
                    mAlbumImageView.roatateStart();
                } else {
                    mAlbumImageView.roatatePause();
                }
            }

            @Override
            public void downAlbum(String imgUrl) {
                Picasso.with(getActivity()).load(imgUrl).into(mAlbumImageView);
            }
        });
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onDestroy() {
        MyApplication.degree=mAlbumImageView.getRoatate();
        mAlbumImageView.resetRoatate();
        super.onDestroy();
    }
}
