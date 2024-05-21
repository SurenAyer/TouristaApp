package com.example.touristaapp.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.touristaapp.fragments.ImageFragment;

import java.util.List;

public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    private List<String> imageUrls;

    public ImagePagerAdapter(FragmentManager fm, List<String> imageUrls) {
        super(fm);
        this.imageUrls = imageUrls;
    }

    @Override
    public Fragment getItem(int position) {
        return new ImageFragment(imageUrls.get(position));
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }
}
