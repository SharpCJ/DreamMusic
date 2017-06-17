package com.example.sharpcj.dreammusic.module.mymusic.ui;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.base.BaseFragment;
import com.example.sharpcj.dreammusic.module.main.ui.MainActivity;
import com.example.sharpcj.dreammusic.module.main.ui.MainFragment;

/**
 * Created by joy on 2016/7/18.
 */
public class NoNetWorkFragment extends BaseFragment {

    private ImageView mivBack;

    @Override
    protected int setViewId() {
        return R.layout.fragment_nonetwork;
    }

    @Override
    protected void findViews(View view) {
        mivBack = (ImageView) view.findViewById(R.id.back2mymusic);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initEvents() {
        mivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack2MyMusic();
            }
        });

    }

    private void goBack2MyMusic() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(NoNetWorkFragment.this);
        transaction.show(((MainActivity)getActivity()).getmMainFragment());
        transaction.commit();
    }

    @Override
    protected void loadData() {

    }
}
