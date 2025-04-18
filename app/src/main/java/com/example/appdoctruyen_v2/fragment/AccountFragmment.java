
package com.example.appdoctruyen_v2.fragment;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.example.appdoctruyen_v2.MainActivity.email;
import static com.example.appdoctruyen_v2.MainActivity.tentaikhoan;

import android.widget.Toast;

import com.example.appdoctruyen_v2.MainDangNhap;
import com.example.appdoctruyen_v2.R;
import com.example.appdoctruyen_v2.model.main.MainThongTin;

public class AccountFragmment extends Fragment {
    private View view;
    private TextView tvtaikhoan, tvgmail;
    private Button btndangxuat;
    private Button btnThongTin, thongbaoBtn;
    private NotificationManager notificationManager;

    private SwitchCompat darkModeSwitch;

    public static final String SHOW_ACCOUNT_AFTER_THEME_CHANGE = "show_account_after_theme_change";

    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean isGranted) {
                    if (isGranted) {
                        Toast.makeText(requireContext(), "Cho phép thông báo", Toast.LENGTH_SHORT).show();
                        createAndShowNotification();
                    } else {
                        Toast.makeText(requireContext(), "Không cho phép thông báo", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    public AccountFragmment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,@Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account_fragmment, container, false);

        darkModeSwitch = view.findViewById(R.id.switcher);

        // Kiểm tra trạng thái Dark Mode hiện tại
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        darkModeSwitch.setChecked(currentNightMode == Configuration.UI_MODE_NIGHT_YES);

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Lưu trạng thái của Dark Mode
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("app_settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("dark_mode_enabled", isChecked);

            // Thêm flag để hiển thị AccountFragment sau khi thay đổi theme
            editor.putBoolean(SHOW_ACCOUNT_AFTER_THEME_CHANGE, true);
            editor.apply();


            // Áp dụng Dark Mode
            AppCompatDelegate.setDefaultNightMode(isChecked ?
                    AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

            // Khởi động lại activity để áp dụng thay đổi
            requireActivity().recreate();
        });


        btnThongTin = view.findViewById(R.id.btnthongtin);
        thongbaoBtn = view.findViewById(R.id.thongbaobtn);
        notificationManager = requireContext().getSystemService(NotificationManager.class);


        // ánh xạ
        tvtaikhoan = view.findViewById(R.id.tvtentaikhoan);
        tvgmail = view.findViewById(R.id.tvgmail);
        btndangxuat = view.findViewById(R.id.btndangxuat);
        tvtaikhoan.setText(tentaikhoan);
        tvgmail.setText(email);


        btndangxuat.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), MainDangNhap.class);
            startActivity(intent);
        });

        btnThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainThongTin.class);
                startActivity(intent);
            }
        });

        thongbaoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                        ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    activityResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
                } else {
                    createAndShowNotification();
                }
            }
        });

        return view;
    }


    private void createAndShowNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "test";
            CharSequence name = getString(R.string.app_name);
            String description = "Example Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), "test")
                .setSmallIcon(R.drawable.baseline_notifications_24) // Replace with your notification icon
                .setContentTitle("Quyền thông báo đã được cấp")
                .setContentText("༼ つ ◕_◕ ༽つQUYỀN THÔNG BÁO")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(10, builder.build());
    }
}


