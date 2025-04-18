package com.example.appdoctruyen_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.appdoctruyen_v2.fragment.AccountFragmment;
import com.example.appdoctruyen_v2.fragment.DangBaiFragment;
import com.example.appdoctruyen_v2.fragment.HomeFragment;
import com.example.appdoctruyen_v2.fragment.TatcatruyenFragment;
import com.example.appdoctruyen_v2.fragment.YeuThichFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    // Class fragmentClass;
    public static Fragment fragment;
    public static int check_admin;
    public static String tentaikhoan;
    public static String email;
    String title;
    Toolbar toolbar;
    Button button;

    // Hằng số cho việc hiển thị lại AccountFragment
    public static final String SHOW_ACCOUNT_AFTER_THEME_CHANGE = "show_account_after_theme_change";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // Kiểm tra và áp dụng Dark Mode trước khi setContentView
        SharedPreferences sharedPreferences = getSharedPreferences("app_settings", Context.MODE_PRIVATE);
        boolean isDarkModeEnabled = sharedPreferences.getBoolean("dark_mode_enabled", false);

        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Nhận thông tin người dùng từ Intent
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


        /*
        // Load Fragment mặc định
        Fragment fragment = new HomeFragment();
        loadFragment(fragment);

        */



        // Kiểm tra xem có cần hiển thị AccountFragment hay không
        boolean showAccountFragment = sharedPreferences.getBoolean(SHOW_ACCOUNT_AFTER_THEME_CHANGE, false);


        if (showAccountFragment) {
            // Hiển thị AccountFragment
            Fragment accountFragment = new AccountFragmment();
            loadFragment(accountFragment);
            toolbar.setTitle(getString(R.string.tai_khoan));

            // Bỏ chọn tất cả các menu item trong bottom navigation
            Menu menu = bottomNavigation.getMenu();
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setChecked(false);
            }

            // Xóa flag sau khi đã xử lý
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(SHOW_ACCOUNT_AFTER_THEME_CHANGE, false);
            editor.apply();
        } else {
            // Load Fragment mặc định (Home)
            Fragment fragment = new HomeFragment();
            loadFragment(fragment);
            toolbar.setTitle(getString(R.string.home));
        }



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

            // Cập nhật tiêu đề Toolbar
            toolbar.setTitle(getString(R.string.tai_khoan));

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