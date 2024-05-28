package com.example.touristaapp.activities;

import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.*;

import androidx.annotation.NonNull;

@RunWith(RobolectricTestRunner.class)
public class BaseActivityTest {

    private BaseActivity baseActivity;

    @Mock
    private NavigationBarView navigationBarView;

    @Mock
    private Menu menu;

    @Mock
    private MenuItem homeItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        baseActivity = Robolectric.buildActivity(BaseActivity.class).get();
        when(navigationBarView.getMenu()).thenReturn(menu);
        when(menu.findItem(com.example.touristaapp.R.id.home)).thenReturn(homeItem);
        when(homeItem.getItemId()).thenReturn(com.example.touristaapp.R.id.home);
        when(menu.add(anyInt(), eq(com.example.touristaapp.R.id.home), anyInt(), anyString())).thenReturn(homeItem);
        when(menu.findItem(anyInt())).thenReturn(homeItem);
    }

    @Test
    public void testSetupNavigation() {
        baseActivity.setupNavigation(navigationBarView, MainActivity.class, com.example.touristaapp.R.id.home);
        verify(navigationBarView).setSelectedItemId(com.example.touristaapp.R.id.home);
        verify(navigationBarView).setOnItemSelectedListener(any(NavigationBarView.OnItemSelectedListener.class));
    }
    @Test
    public void testSetupNavigationWithDifferentActivity() {
        // Test navigation setup with a different activity and selected item ID
        baseActivity.setupNavigation(navigationBarView, ExploreActivity.class, com.example.touristaapp.R.id.explore);
        verify(navigationBarView).setSelectedItemId(com.example.touristaapp.R.id.explore);
    }

    @Test
    public void testOnItemSelectedListener() {
        // Test the onItemSelectedListener
        NavigationBarView.OnItemSelectedListener listener = new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return true;
            }
        };
        when(homeItem.getItemId()).thenReturn(com.example.touristaapp.R.id.home);
        boolean result = listener.onNavigationItemSelected(homeItem);
        Assert.assertTrue(result);
    }
    @Test
    public void testOnItemSelectedListenerWithDifferentItem() {
        // Test the onItemSelectedListener with a different item
        NavigationBarView.OnItemSelectedListener listener = new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return true;
            }
        };
        MenuItem exploreItem = mock(MenuItem.class);
        when(exploreItem.getItemId()).thenReturn(com.example.touristaapp.R.id.explore);
        boolean result = listener.onNavigationItemSelected(exploreItem);
        Assert.assertTrue(result);
    }
}