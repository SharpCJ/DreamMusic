package com.example.sharpcj.dreammusic.module.musichall.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharpcj.dreammusic.MyApplication;
import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.base.MusicCallBack;
import com.example.sharpcj.dreammusic.common.widget.MyTextViewForRank;
import com.example.sharpcj.dreammusic.jiuguoutils.PlayerController;
import com.example.sharpcj.dreammusic.jiuguoutils.beans.BillBoardSearchResult;
import com.example.sharpcj.dreammusic.jiuguoutils.beans.MySongBean;
import com.example.sharpcj.dreammusic.module.main.ui.MainActivity;
import com.lidroid.xutils.BitmapUtils;

import java.util.List;

/**
 * Created by joy on 2016/7/20.
 */
public class HotRecyclerViewAdapter extends RecyclerView.Adapter<HotRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private BitmapUtils bitmapUtils;
    private MusicCallBack listener;
    private List<Object> mList;

    public HotRecyclerViewAdapter(Context mContext, List<Object> list, BitmapUtils bitmapUtils, MusicCallBack listener) {
        this.mContext = mContext;
        this.bitmapUtils = bitmapUtils;
        this.listener = listener;
        this.mList = list;
    }

    /**
     * 这个方法在item第一次创建的时候调用，复用的时候不会调用
     * viewType：用于判断当前item的类型,如果不使用多布局，这个参数没什么作用
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_hotmusic_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                if (listener != null) {
                    listener.clickItem(position, 0);
                }
            }
        });

        myViewHolder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                if (listener != null) {
                    listener.clickItem(position, 1);
                }
            }
        });

        return myViewHolder;
    }

    /**
     * 每一个item出来的时候都会调用,在这个方法里设置每个item中控件的内容
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BillBoardSearchResult.SongListBean songListBean = (BillBoardSearchResult.SongListBean) mList.get(position);
//        BillBoardSearchResult.SongListBean songListBean = (BillBoardSearchResult.SongListBean) MyApplication.lstNetSong.get(position);
        holder.tvSongName.setText(songListBean.getTitle());
        holder.tvSinger.setText(songListBean.getArtist_name());
        String rank = songListBean.getRank();
        int rankNum = Integer.parseInt(rank);
        int color;
        if (rankNum == 0) {
            color = Color.parseColor("#FF0042");
        } else if (rankNum == 1) {
            color = Color.parseColor("#FF5400");
        } else if (rankNum == 2) {
            color = Color.parseColor("#FFA200");
        } else {
            color = Color.BLACK;
        }
        holder.textViewForRank.setColorAndNum(color, rankNum);
        holder.ivMenu.setImageResource(R.mipmap.bt_recsong_more);
        bitmapUtils.display(holder.ivSongIcon, songListBean.getPic_small());

        holder.itemView.setTag(position);
        holder.ivMenu.setTag(position);

    }

    /**
     * 返回item的数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mList.size();
//        return MyApplication.lstNetSong.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivSongIcon;
        MyTextViewForRank textViewForRank;
        TextView tvSongName;
        TextView tvSinger;
        ImageView ivMenu;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivSongIcon = (ImageView) itemView.findViewById(R.id.ivSongIcon);
            tvSongName = (TextView) itemView.findViewById(R.id.tvSongName);
            textViewForRank = (MyTextViewForRank) itemView.findViewById(R.id.textViewRank);
            tvSinger = (TextView) itemView.findViewById(R.id.tvSinger);
            ivMenu = (ImageView) itemView.findViewById(R.id.ivMenu);
        }
    }
}
