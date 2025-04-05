package com.example.appdoctruyen_v2;

import com.example.appdoctruyen_v2.database.databasedoctruyen;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainNoiDungTruyen extends AppCompatActivity {

    TextView txtTenTruyen,txtNoidung;
    Button btnDanhGia, btnYeuThich, btnChiaSe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_noi_dung_truyen);

        AnhXa();
        DanhGia();
        ChiaSe();
        YeuThich();


        // Lấy nội dung và tên truyện để hiển thị
        Intent intent = getIntent();
        String tenTruyen = intent.getStringExtra("tentruyen");
        String noidung = intent.getStringExtra("noidung");

        txtTenTruyen.setText(tenTruyen);
        txtNoidung.setText(noidung);

        //Cuộn textview
        txtNoidung.setMovementMethod(new ScrollingMovementMethod());

    }

    private void DanhGia() {
        btnDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainNoiDungTruyen.this,MainDanhGia.class);
                intent.putExtra("tentruyen",txtTenTruyen.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void ChiaSe() {
        btnChiaSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTruyen = txtTenTruyen.getText().toString();
    
                // Retrieve the image URL from the database
                databasedoctruyen database = new databasedoctruyen(MainNoiDungTruyen.this);
                String imageUrl = getImageUrlFromDatabase(database, tenTruyen);
    
                if (imageUrl == null || imageUrl.isEmpty()) {
                    Toast.makeText(MainNoiDungTruyen.this, "Không tìm thấy ảnh cho truyện này!", Toast.LENGTH_SHORT).show();
                    return;
                }
    
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Chia sẻ truyện: " + tenTruyen);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Mình đang đọc truyện ༼ つ ◕_◕ ༽つ " + tenTruyen + "\n\n" + imageUrl);
                startActivity(Intent.createChooser(shareIntent, "Chia sẻ qua"));
            }
        });
    }
    
    private String getImageUrlFromDatabase(databasedoctruyen database, String tenTruyen) {
        SQLiteDatabase db = database.getReadableDatabase();
        String imageUrl = null;
    
        Cursor cursor = db.rawQuery("SELECT anh FROM truyen WHERE tieude = ?", new String[]{tenTruyen});
        if (cursor != null && cursor.moveToFirst()) {
            imageUrl = cursor.getString(0);
            cursor.close();
        }
        db.close();
        return imageUrl;
    }
    private void YeuThich() {
        btnYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainNoiDungTruyen.this,"Đã thêm vào danh sách yêu thích",Toast.LENGTH_SHORT).show();
                Log.e("Yêu thích: ","Đã thêm vào danh sách yêu thích");
            }
        });
    }


    private void AnhXa() {
        txtTenTruyen = findViewById(R.id.TenTruyen);
        txtNoidung = findViewById(R.id.NoiDung);
        btnDanhGia = findViewById(R.id.buttonDanhgia);
        btnYeuThich = findViewById(R.id.buttonYeuthich);
        btnChiaSe = findViewById(R.id.buttonChiase);
    }
}