package com.example.appdoctruyen_v2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.appdoctruyen_v2.database.databasedoctruyen;
import com.example.appdoctruyen_v2.model.Truyen;
import com.example.appdoctruyen_v2.model.main.MainAdmin;


public class MainDangBai extends AppCompatActivity {

    EditText edtTieuDe,edtNoiDung,edtAnh;
    Button btnDangBai;
    databasedoctruyen databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dang_bai);
        edtTieuDe = findViewById(R.id.dbtieude);
        edtNoiDung = findViewById(R.id.dbnoidung);
        btnDangBai = findViewById(R.id.dbdangbai);
        edtAnh = findViewById(R.id.dbimg);

        databaseDocTruyen = new databasedoctruyen(this);

        btnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tentruyen = edtTieuDe.getText().toString();
                String noidung = edtNoiDung.getText().toString();
                String img = edtAnh.getText().toString();

                Truyen truyen = CreatTruyen();

                if(tentruyen.equals("") || noidung.equals("") || img.equals("")){
                    Toast.makeText(MainDangBai.this,"Yêu cầu nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseDocTruyen.AddTruyen(truyen);
                    sendNotification("༼ つ ◕_◕ ༽つ TRUYỆN MỚI!!!", "Truyện " + tentruyen + " vừa được đăng tải.");
                    // Intent intent = new Intent(MainDangBai.this, MainAdmin.class);
                    finish();
                    // startActivity(intent);
                    Toast.makeText(MainDangBai.this,"Thêm truyện thành công",Toast.LENGTH_SHORT).show();
                    Log.e("Thêm truyện : ","Thành công");
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
                .setSmallIcon(R.drawable.baseline_notifications_24) // Replace with your notification icon
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());
    }
    //
    private Truyen CreatTruyen(){
        String tentruyen = edtTieuDe.getText().toString();
        String noidung = edtNoiDung.getText().toString();
        String img = edtAnh.getText().toString();

        Intent intent = getIntent();
        int id = intent.getIntExtra("Id",0);
        return new Truyen(tentruyen,noidung,img,id);
    }
}