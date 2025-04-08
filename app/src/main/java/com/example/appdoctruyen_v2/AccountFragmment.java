package com.example.appdoctruyen_v2;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import android.content.Context;
import android.widget.Toast;

import com.example.appdoctruyen_v2.model.main.MainThongTin;

public class AccountFragmment extends Fragment {
    private View view;
    private TextView tvtaikhoan, tvgmail;
    private Button btndangxuat;
    private Button btnThongTin, thongbaoBtn;
    private NotificationManager notificationManager;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account_fragmment, container, false);
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
