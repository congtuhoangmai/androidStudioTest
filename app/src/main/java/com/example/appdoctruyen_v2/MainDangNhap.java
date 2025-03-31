package com.example.appdoctruyen_v2;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen_v2.database.databasedoctruyen;

public class MainDangNhap extends AppCompatActivity {

    // Khai báo các biến.
    EditText edtTaiKhoan, edtMatKhau;
    Button btnDangNhap, btnDangKy;

    // Khai báo database.
    databasedoctruyen databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dang_nhap);

        // Ánh xạ các thành phần UI từ activity_main_dang_nhap.xml.
        AnhXa();
        databaseDocTruyen = new databasedoctruyen(this);

        // Click vào nút đăng ký thì chuyển sang MainDangKy.java.
        ClickDangKy();

        // Click vào nút đăng nhập.
        ClickDangNhap();

    }

    // Phương thức để ánh xạ các phần tử trong activity_main_dang_nhap.xml qua đây
    private void AnhXa() {
        edtTaiKhoan = findViewById(R.id.edtUsername);
        edtMatKhau = findViewById(R.id.edtPasswod);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);
    }

    // Method ấn nút đăng nhập
    private void ClickDangNhap() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Lấy giá trị từ các input và chuyển thành String.
                String tentaikhoan = edtTaiKhoan.getText().toString();
                String matkhau = edtMatKhau.getText().toString();

                // Lấy dữ liệu từ database và lưu kết quả vào đối tượng Cursor (con trỏ để duyệt qua dữ liệu).
                Cursor cursor = databaseDocTruyen.getData();

                // Duyệt qua từng dòng dữ liệu trong Cursor.
                while (cursor.moveToNext()) {
                    String datatentaikhoan = cursor.getString(1); // Lấy cột thứ 2 là tên tk (index 1).
                    String datamatkhau = cursor.getString(2); // Tương tự trên.


                    // Nếu tài khoản và mật khẩu khớp với data từ database.
                    if (datatentaikhoan.equals(tentaikhoan) && datamatkhau.equals(matkhau)) {

                        // Lấy thêm thông tin từ database.
                        int phanquyen = cursor.getInt(4); // Quyền hạn người dùng.
                        int idd = cursor.getInt(0); // ID người dùng.
                        String tentk = cursor.getString(1);
                        String email = cursor.getString(3);

                        // Tạo intent chuyển đến MainActivity.
                        // Gửi các dữ liệu qua MainActivity sau đó chạy MainActivity.
                        Intent intent = new Intent(MainDangNhap.this,MainActivity.class);
                        intent.putExtra("phanq", phanquyen);
                        intent.putExtra("idd", idd);
                        intent.putExtra("email", email);
                        intent.putExtra("tentaikhoan", tentk);
                        startActivity(intent);
                        Log.e("Đăng nhập : ","Thành công");
                    }
                    else{
                        Log.e("Đăng nhập : ","Không Thành công");
                    }
                }


                // Đặt cursor về vị trí đầu tiên.
                // Đóng cursor để giải phóng tài nguyên.
                cursor.moveToFirst();
                cursor.close();
            }
        });
    }

    // Method đăng ký
    private void ClickDangKy() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainDangNhap.this,MainDangKy.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_dang_nhap_landscape);
            AnhXa();
            ClickDangKy();
            ClickDangNhap();

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main_dang_nhap);
            AnhXa();
            ClickDangKy();
            ClickDangNhap();
        }
    }
}