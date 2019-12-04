package com.gzr.gzrdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.gzr.gzrdemo.Constants;
import com.gzr.gzrdemo.MainActivity;
import com.gzr.gzrdemo.R;
import com.gzr.gzrdemo.activity.WelcomeActivity;
import com.gzr.gzrdemo.util.SharePreferenceUtil;

public class GuideAdapter extends PagerAdapter {

    private int[] resources;
    private Context context;

    public GuideAdapter(Context context, int[] resources) {
        this.resources = resources;
        this.context = context;
    }

    @Override
    public int getCount() {
        return resources.length;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        if (position < 2) {
            ImageView guideView = new ImageView(context);
            guideView.setBackgroundResource(resources[position]);
            LayoutParams lp = new LayoutParams();
            lp.height = LayoutParams.MATCH_PARENT;
            lp.width = LayoutParams.MATCH_PARENT;
            lp.gravity = Gravity.CENTER;
            guideView.setLayoutParams(lp);
            ((ViewPager) container).addView(guideView);
            return guideView;
        } else {
            View view = View.inflate(context, R.layout.activity_launch_guide3, null);
            ImageButton mGuideFinish = (ImageButton) view.findViewById(R.id.guide_finish);
            mGuideFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharePreferenceUtil.put(context, Constants.LAUNCH_GUIDE, true);
                    context.startActivity(new Intent(context, MainActivity.class));
                }
            });
            ((ViewPager) container).addView(view);
            return view;
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

}
