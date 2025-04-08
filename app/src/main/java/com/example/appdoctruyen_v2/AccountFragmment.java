package com.example.appdoctruyen_v2;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

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

import com.example.appdoctruyen_v2.model.main.MainThongTin;


public class AccountFragmment extends Fragment {
    private View view;
    private TextView tvtaikhoan,tvgmail;
    private Button btndangxuat, btnthongtin, postNotification;


    public AccountFragmment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account_fragmment, container, false);

        //ánh xạ
        tvtaikhoan = view.findViewById(R.id.tvtentaikhoan);
        tvgmail = view.findViewById(R.id.tvgmail);
        btndangxuat = view.findViewById(R.id.btndangxuat);
        tvtaikhoan.setText(tentaikhoan);
        tvgmail.setText(email);

        btndangxuat.setOnClickListener(view ->{
            Intent intent = new Intent(getContext(),MainDangNhap.class);
            startActivity(intent);
        });

        btnthongtin.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), MainThongTin.class);
            startActivity(intent);
        });

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), "test")
//                .setSmallIcon(R.drawable.baseline_notifications_24)
//                .setContentTitle(getString(R.string.app_name))
//                .setContentText("Example Notification")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        postNotification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
//                        ActivityCompat.checkSelfPermission(requireContext(),
//                                Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//                    activityResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
//                } else {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        CharSequence name = getString(R.string.app_name);
//                        String description = "Example Notification";
//                        int importance = NotificationManager.IMPORTANCE_DEFAULT;
//                        NotificationChannel channel = new NotificationChannel("test", name, importance);
//                        channel.setDescription(description);
//                        notificationManager.createNotificationChannel(channel);
//                    }
//                    notificationManager.notify(10, builder.build());
//                }
//            }
//        });
        return view;
    }
}