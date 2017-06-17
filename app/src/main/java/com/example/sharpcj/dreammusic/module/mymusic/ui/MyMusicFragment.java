package com.example.sharpcj.dreammusic.module.mymusic.ui;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sharpcj.dreammusic.MyApplication;
import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.base.BaseFragment;
import com.example.sharpcj.dreammusic.jiuguoutils.LocalMusicUtils;
import com.example.sharpcj.dreammusic.module.localmusic.LocalMusicFragment;
import com.example.sharpcj.dreammusic.module.main.ui.MainActivity;

/**
 * Created by joy on 2016/7/17.
 */
public class MyMusicFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout mllNoNetTips;
    private ImageView mivNoNetCancel;
    private RelativeLayout mrlLocal;
    private FragmentTransaction mtransaction;
    private LocalMusicFragment mlocalMusicFragment = new LocalMusicFragment();
    private TextView mtvlocalnum;


    @Override
    protected int setViewId() {
        return R.layout.fragment_mymusic;
    }

    @Override
    protected void findViews(View view) {
        mllNoNetTips = (RelativeLayout) view.findViewById(R.id.nonettips);
        mivNoNetCancel = (ImageView) view.findViewById(R.id.ivNoNetCancel);
        mrlLocal = (RelativeLayout) view.findViewById(R.id.rl_local);
        mtvlocalnum = (TextView) view.findViewById(R.id.tvlocalsongnum);
    }

    @Override
    protected void init() {
        mlocalMusicFragment = new LocalMusicFragment();
    }

    @Override
    protected void initViews() {
        mtvlocalnum.setText(MyApplication.mlstMediaStoreSong.size()+"");
    }

    @Override
    protected void initEvents() {
        mllNoNetTips.setOnClickListener(this);
        mivNoNetCancel.setOnClickListener(this);
        mrlLocal.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nonettips:
                mtransaction = getActivity().getSupportFragmentManager().beginTransaction();
                NoNetWorkFragment noNetWorkFragment = new NoNetWorkFragment();
                mtransaction.add(R.id.mainFramLayout, noNetWorkFragment);
                mtransaction.show(noNetWorkFragment);
                mtransaction.hide(((MainActivity) getActivity()).getmMainFragment());
                mtransaction.commit();
                break;
            case R.id.ivNoNetCancel:
                break;
            case R.id.rl_local:
                mtransaction = getActivity().getSupportFragmentManager().beginTransaction();
                mtransaction.add(R.id.mainFramLayout, mlocalMusicFragment);
                mtransaction.show(mlocalMusicFragment);
                mtransaction.hide(((MainActivity) getActivity()).getmMainFragment());
                mtransaction.commit();
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
