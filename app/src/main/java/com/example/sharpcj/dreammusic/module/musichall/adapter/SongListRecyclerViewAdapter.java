package com.example.sharpcj.dreammusic.module.musichall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.module.musichall.bean.SonglistSearchBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by joy on 2016/7/22.
 */
public class SongListRecyclerViewAdapter extends RecyclerView.Adapter<SongListRecyclerViewAdapter.MyHolder>{


    private List<SonglistSearchBean> mlst;
    private Context mcontext;

    public SongListRecyclerViewAdapter(List<SonglistSearchBean> mlst, Context mcontext) {
        this.mlst = mlst;
        this.mcontext = mcontext;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.layout_songlist_item, null, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        SonglistSearchBean songlistSearchBean = mlst.get(position);
        Picasso.with(mcontext).load(songlistSearchBean.getCover_img()).into(holder.imageView);
        holder.tvTitle.setText(songlistSearchBean.getTitle());
    }

    @Override
    public int getItemCount() {
        return mlst.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tvTitle;

        public MyHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ivpicture);
            tvTitle= (TextView) itemView.findViewById(R.id.tvtag);
        }
    }
}
