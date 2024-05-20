package com.example.touristaapp.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.viewpager.widget.ViewPager;

import com.example.touristaapp.R;
import com.example.touristaapp.utils.ImagePagerAdapter;

import java.util.List;

public class FullScreenImageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ViewPager viewPager = findViewById(R.id.view_pager);
        List<String> imageUrls = getIntent().getStringArrayListExtra("imageUrls");
        ImagePagerAdapter adapter = new ImagePagerAdapter(getSupportFragmentManager(), imageUrls);
        viewPager.setAdapter(adapter);

        ImageView imagePrevious = findViewById(R.id.image_previous);
        ImageView imageNext = findViewById(R.id.image_next);

        imagePrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            }
        });

        imageNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}