package com.sports.yue.UI.UI.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sports.yue.UI.UI.fragment.MeFragment;
import static com.sports.yue.UI.UI.Data.ImageData.IMAGE_DRAWABLES;
public class ImagePagerAdapter extends FragmentStatePagerAdapter {




    public ImagePagerAdapter(Fragment fragment) {
        // Note: Initialize with the child fragment manager.
        super(fragment.getChildFragmentManager());
    }

    @Override
    public int getCount() {
        return IMAGE_DRAWABLES.length;
    }

    @Override
    public Fragment getItem(int position) {
        return MeFragment.newInstance(IMAGE_DRAWABLES[position]);
    }
}
