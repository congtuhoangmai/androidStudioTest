package com.example.appdoctruyen_v2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.appdoctruyen_v2.database.databasedoctruyen;
import com.example.appdoctruyen_v2.model.Truyen;
import com.example.appdoctruyen_v2.model.main.MainAdmin;

public class MainCapNhat extends AppCompatActivity {

    databasedoctruyen databaseDocTruyen;
    // TextView txtNoidung;
    EditText edtTieuDe,edtNoiDung,edtAnh;
    Button btnCapNhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cap_nhat);
        edtTieuDe = findViewById(R.id.dbtieude);
        edtNoiDung = findViewById(R.id.dbnoidung);
        btnCapNhat = findViewById(R.id.buttonCapNhat);
        edtAnh = findViewById(R.id.dbimg);


        Intent intent = getIntent();
        String tenTruyen = intent.getStringExtra("tentruyen");
        String noidung = intent.getStringExtra("noidung");
        String img = intent.getStringExtra("imgtruyen");

        edtTieuDe.setText(tenTruyen);
        edtNoiDung.setText(noidung);
        edtAnh.setText(img);

        // Cuộn textview
        edtNoiDung.setScroller(new Scroller(this));
        edtNoiDung.setMaxLines(Integer.MAX_VALUE);
        edtNoiDung.setSingleLine(false);
        edtNoiDung.setVerticalScrollBarEnabled(true);
        edtNoiDung.setMovementMethod(new ScrollingMovementMethod());


        databaseDocTruyen = new databasedoctruyen(this);

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentruyen = edtTieuDe.getText().toString();
                String noidung = edtNoiDung.getText().toString();
                String img = edtAnh.getText().toString();
                Truyen truyen = CreatTruyen();

                if (tentruyen.equals("") || noidung.equals("") || img.equals("")) {
                    Toast.makeText(MainCapNhat.this,"Yêu cầu nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseDocTruyen.AddTruyen(truyen);
                    sendNotification("༼ つ ◕_◕ ༽つ CHƯƠNG MỚI!!!", "Truyện " + tentruyen + " vừa được cập nhật.");
                    Intent intent = new Intent(MainCapNhat.this, MainAdmin.class);
                    finish();
                    startActivity(intent);
                    Toast.makeText(MainCapNhat.this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    Log.e("Cập nhật truyện : ","Thành công");
                }
            }
        });
    }

    private void sendNotification(String title, String content) {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        String channelId = "truyen_notifications";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Truyện Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Thông báo về truyện");
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(2, builder.build());
    }

    private Truyen CreatTruyen(){
        String tentruyen = edtTieuDe.getText().toString();
        String noidung = edtNoiDung.getText().toString();
        String img = edtAnh.getText().toString();

        Intent intent = getIntent();
        int id = intent.getIntExtra("Id",0);
        Truyen truyen = new Truyen(tentruyen,noidung,img,id);
        return truyen;
    }
}