package com.example.sharpcj.dreammusic.module.main.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.sharpcj.dreammusic.R;
import com.example.sharpcj.dreammusic.base.Base2Activity;
import com.example.sharpcj.dreammusic.base.BaseActivity;
import com.example.sharpcj.dreammusic.common.constant.Constant;
import com.se7en.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends Base2Activity {

    private ViewPager mvpWelcome;
    private LinearLayout mllPoints;
    private ImageView mivsplash;
    private int mLastVersion;
    private int mCurrentVersion;
    private List<ImageView> mlstPoints;
    private List<LinearLayout> mlstLinearLayouts;
    private Button mbtnEnter;
    private Button mbtnSkip;

    @Override
    protected int setViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void findViews() {
        mvpWelcome = (ViewPager) findViewById(R.id.vpWelcome);
        mllPoints = (LinearLayout) findViewById(R.id.llPoints);
        mivsplash = (ImageView) findViewById(R.id.ivSplash);
        mbtnEnter = (Button) findViewById(R.id.btnEnter);
        mbtnSkip = (Button) findViewById(R.id.btnSkip);
    }

    @Override
    protected void init() {
        mCurrentVersion = SystemUtil.getSystemVersionCode();
        mLastVersion = SystemUtil.getSharedInt(Constant.VERSION_STRING, -1);
        if (mLastVersion == -1 || mCurrentVersion > mLastVersion) {
            mivsplash.setVisibility(View.INVISIBLE);//隐藏闪屏页
            mbtnEnter.setVisibility(View.INVISIBLE);//隐藏立即体验按钮
            mbtnSkip.setVisibility(View.VISIBLE);
            initPoints(); //初始化小圆点
            mllPoints.setVisibility(View.VISIBLE);//小圆点可见
            mlstPoints.get(0).setImageResource(R.mipmap.dty);
            initLinearLayouts();//初始化ViewPager页面
            setVpWelcomeAdapter();//设置设配器
            SystemUtil.setSharedInt(Constant.VERSION_STRING,mCurrentVersion);
            mvpWelcome.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    if (position == 4) {
                        mbtnEnter.setVisibility(View.VISIBLE);
                    } else {
                        mbtnEnter.setVisibility(View.INVISIBLE);
                    }
                    for (int i = 0; i < mlstPoints.size(); i++) {
                        if (i == position) {
                            mlstPoints.get(i).setImageResource(R.mipmap.dty);
                        } else {
                            mlstPoints.get(i).setImageResource(R.mipmap.dtz);
                        }
                    }
                }
            });
        } else {
           skipActivity();
        }
    }

    private void setVpWelcomeAdapter() {
        mvpWelcome.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mlstLinearLayouts.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mlstLinearLayouts.get(position));
                return mlstLinearLayouts.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mlstLinearLayouts.get(position));
            }
        });
    }

    private void initLinearLayouts() {
        mlstLinearLayouts = new ArrayList<>();
        int[] mainbg = {R.mipmap.bny, R.mipmap.dtw, R.mipmap.bnq, R.mipmap.bo0, R.mipmap.bns};
        int[] titles = {R.mipmap.bnz, R.mipmap.dtx, R.mipmap.bnr, R.mipmap.bo2, R.mipmap.bnu};
        int[] descriptions = {R.mipmap.bnx, R.mipmap.dtv, R.mipmap.bnp, R.mipmap.bo1, R.mipmap.bnt};
        for (int i = 0; i < 5; i++) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_welcomenavigation, null, false);
            ((ImageView) linearLayout.findViewById(R.id.ivMain)).setImageResource(mainbg[i]);
            ((ImageView) linearLayout.findViewById(R.id.ivTitle)).setImageResource(titles[i]);
            ((ImageView) linearLayout.findViewById(R.id.ivDes)).setImageResource(descriptions[i]);
            mlstLinearLayouts.add(linearLayout);
        }
    }

    private void initPoints() {
        mlstPoints = new ArrayList<>();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
        params.leftMargin = 10;
        params.rightMargin = 10;
        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.mipmap.dtz);
            mllPoints.addView(imageView);
            mlstPoints.add(imageView);
        }
    }

    @Override
    protected void initEvents() {
        mbtnSkip.setOnClickListener(this);
        mbtnEnter.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnSkip||v.getId()==R.id.btnEnter){
            skipActivity();
        }
    }

    private void skipActivity() {
        mvpWelcome.setVisibility(View.INVISIBLE);
        mbtnEnter.setVisibility(View.INVISIBLE);
        mbtnSkip.setVisibility(View.INVISIBLE);
        mllPoints.setVisibility(View.INVISIBLE);
        mivsplash.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                WelcomeActivity.this.finish();
            }
        },2000);
    }

    @Override
    public void onBackPressed() {

    }
}