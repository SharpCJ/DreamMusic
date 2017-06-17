package com.example.sharpcj.dreammusic.module.musichall.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.base.BaseFragment;
import com.example.sharpcj.dreammusic.base.ParseJsonCompleteCallback;
import com.example.sharpcj.dreammusic.module.musichall.dao.SelectionDao;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joy on 2016/7/20.
 */
public class SelectionFragment extends BaseFragment {

    private ConvenientBanner mcnSelection;
    private List<String> mlstPicUrls;

    @Override
    protected int setViewId() {
        return R.layout.fragment_selection;
    }

    @Override
    protected void findViews(View view) {
        mcnSelection = (ConvenientBanner) view.findViewById(R.id.convenientBannerSelection);
    }

    @Override
    protected void init() {
        mlstPicUrls=new ArrayList<>();
    }

    @Override
    protected void initViews() {
        mcnSelection.setCanLoop(true);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void loadData() {
        new SelectionDao().getLoopPic(new ParseJsonCompleteCallback() {
            @Override
            public void useData(Object object) {
                List<String> list= (List<String>) object;
                for (int i = 0; i < list.size(); i++) {
                    mlstPicUrls.add(list.get(i));
                }
                SelectionFragment.this.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mcnSelection.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                            @Override
                            public NetworkImageHolderView createHolder() {
                                return new NetworkImageHolderView();
                            }
                        },mlstPicUrls)   //设置需要切换的View
                                .setPointViewVisible(true)    //设置指示器是否可见
                                .setPageIndicator(new int[]{R.mipmap.pager_not_selected_night_mode, R.mipmap.pager_selected_night_mode});  //设置指示器圆点
                    }
                });
            }
        });
    }

    class NetworkImageHolderView implements Holder<String> {

        ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Picasso.with(SelectionFragment.this.getActivity()).load(data).into(imageView);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mcnSelection.stopTurning();
    }

    @Override
    public void onResume() {
        super.onResume();
        mcnSelection.startTurning(3000);
    }
}
