package com.example.appdoctruyen_v2.model.main;

import com.example.appdoctruyen_v2.R;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainThongTin extends AppCompatActivity {

    TextView txtThongTin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thong_tin);

        txtThongTin = findViewById(R.id.textviewthongtin);

        String thongtin = "Ứng dụng được phát triển bởi Nhóm 2. \n"
                + "Với ứng dụng này, bạn có thể khám phá và đọc hàng loạt truyện hấp dẫn, lưu lại, đánh giá, chia sẻ những truyện yêu thích"
                + "Hãy bắt đầu hành trình khám phá thế giới truyện ngay hôm nay!\n";
        txtThongTin.setText(thongtin);
    }
}