package com.example.sharpcj.dreammusic.module.localmusic;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sharpcj.dreammusic.MyApplication;
import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.base.BaseFragment;
import com.example.sharpcj.dreammusic.jiuguoutils.PlayCallback;
import com.example.sharpcj.dreammusic.jiuguoutils.PlayerController;
import com.example.sharpcj.dreammusic.jiuguoutils.beans.MediaStoreSong;
import com.example.sharpcj.dreammusic.common.adapter.CommonAdapter;
import com.example.sharpcj.dreammusic.common.adapter.ViewHolder;
import com.example.sharpcj.dreammusic.module.main.MusicPlayService;
import com.example.sharpcj.dreammusic.module.main.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2016/7/18.
 */
public class LocalSongFragment extends BaseFragment {

    private EditText metSearchLocal;
    private ListView mlvLocalSong;
    private CommonAdapter madapter;

    @Override
    protected int setViewId() {
        return R.layout.fragment_localsongs;
    }

    @Override
    protected void findViews(View view) {
        metSearchLocal = (EditText) view.findViewById(R.id.et_searchlocal);
        mlvLocalSong = (ListView) view.findViewById(R.id.lvlocalSong);
    }

    @Override
    protected void init(){
    }

    @Override
    protected void initViews() {
        madapter = new CommonAdapter<MediaStoreSong>(getActivity(), MyApplication.mlstMediaStoreSong,
                R.layout.layout_fragment_localsong_lvitem) {
            @Override
            public void convert(ViewHolder helper, int position, MediaStoreSong item) {
                String title = item.getTitle();
                String artist = item.getArtist();
                String album = item.getAlbum();
                helper.setText(R.id.tvsongtile, title);
                if (artist == null && album == null) {
                    helper.setImageResource(R.id.iv_artistIcon, R.mipmap.unknown_artist_icon);
                    helper.setText(R.id.tvartistandalbum, "");
                } else {
                    helper.setImageResource(R.id.iv_artistIcon, R.mipmap.known_artist_icon);
                    if (artist == null) {
                        artist = "";
                    }
                    if (album == null) {
                        album = "";
                    }
                    helper.setText(R.id.tvartistandalbum, artist + "·" + album);
                }
            }
        };
        mlvLocalSong.setAdapter(madapter);
    }

    @Override
    protected void initEvents() {
        mlvLocalSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlayerController.playLocalSong(getActivity(),position);
            }
        });
    }


    @Override
    protected void loadData() {

    }
}
