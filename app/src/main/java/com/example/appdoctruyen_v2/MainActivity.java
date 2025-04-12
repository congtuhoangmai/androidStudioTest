package com.example.appdoctruyen_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.appdoctruyen_v2.adapter.ViewPagerAdapter;
import com.example.appdoctruyen_v2.database.databasedoctruyen;
import com.example.appdoctruyen_v2.model.Truyen;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    // Class fragmentClass;
    public static Fragment fragment;
    public static int check_admin;
    public static String tentaikhoan;
    public static String email;
    String title;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intentpq = getIntent();
        check_admin = intentpq.getIntExtra("phanq",0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");


        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Bottom Navigation
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(navListener);

        // Load Fragment mặc định
        Fragment fragment = new HomeFragment();
        loadFragment(fragment);

    }


    // Xử lý sự kiện chọn Bottom Navigation
    private final BottomNavigationView.OnItemSelectedListener navListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);

                    int itemId = item.getItemId();

                    if (itemId == R.id.nav_home) {
                        if (currentFragment instanceof HomeFragment) return true;
                        selectedFragment = new HomeFragment();
                        title = getString(R.string.home);
                    } else if (itemId == R.id.nav_all) {
                        if (currentFragment instanceof TatcatruyenFragment) return true;
                        selectedFragment = new TatcatruyenFragment();
                        title = getString(R.string.all_story);
                    } else if (itemId == R.id.nav_favorite) {
                        if (currentFragment instanceof YeuThichFragment) return true;
                        selectedFragment = new YeuThichFragment();
                        title = getString(R.string.favorite);
                    } else if (itemId == R.id.nav_upload) {
                        if (currentFragment instanceof DangBaiFragment) return true;
                        selectedFragment = new DangBaiFragment();
                        title = getString(R.string.post);
                    }

                    // Chuyển đổi sang Fragment được chọn ở trên
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, selectedFragment).commit();
                        toolbar.setTitle(title);
                    }
                    return true;
                }
            };


    // Hiển thị nút tìm kiếm trên Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return true;
    }

    // Xử lý sự kiện nhấn vào nút tìm kiếm
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            // Xử lý tìm kiếm tại đây (hiển thị màn hình tìm kiếm hoặc hiển thị Toast)
            Intent intent = new Intent(this, MainTimKiem.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.action_account) {
            // Chuyển sang Fragment tài khoản
            Fragment accountFragment = new AccountFragmment();
            loadFragment(accountFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Tải Fragment lên giao diện người dùng
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}