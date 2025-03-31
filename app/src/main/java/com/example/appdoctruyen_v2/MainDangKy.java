package com.example.appdoctruyen_v2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appdoctruyen_v2.database.databasedoctruyen;
import com.example.appdoctruyen_v2.model.TaiKhoan;


public class MainDangKy extends AppCompatActivity {
    EditText edtDKTaiKhoan,edtDKMatKhau,edtDKEmail;
    Button btnDKDangKy,btnDKDangNhap;

    databasedoctruyen databaseDocTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dang_ky);

        // Kết nối các thành phần từ activity_main_dang_ky.
        AnhXa();

        // Khởi tạo database, truyền context hiện tại.
        databaseDocTruyen = new databasedoctruyen(this);

        // Xử lý event khi ấn vào đăng ký.
        ClickDangKy();

        // Khi click vào nút Trở về đăng nhập thì end MainDangKy để về màn hình đăng nhập.
        ReturnDangNhap();

    }

    // Method tạo tài khoản.
    private TaiKhoan CreateTaiKhoan(){

        // Lấy dữ liệu từ các input.
        String taikhoan = edtDKTaiKhoan.getText().toString();
        String matkhau = edtDKMatKhau.getText().toString();
        String email = edtDKEmail.getText().toString();

        // Thiết lập giá trị mặc định cho phân quyền là 1 (người dùng thông thường).
        int phanquyen = 1;

        // Tạo tài khoản mới và return tài khoản
        TaiKhoan tk = new TaiKhoan(taikhoan,matkhau,email,phanquyen);
        return tk;
    }

    // Ánh xạ các phần tử từ activity_main_dang_ky.xml
    private void AnhXa() {
        edtDKEmail = findViewById(R.id.dkEmail);
        edtDKMatKhau = findViewById(R.id.dkMatKhau);
        edtDKTaiKhoan = findViewById(R.id.dkTaiKhoan);
        btnDKDangKy = findViewById(R.id.dkDangKy);
        btnDKDangNhap = findViewById(R.id.dkDangNhap);

    }

    // Method đăng ký
    private void ClickDangKy() {
        btnDKDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Lấy giá trị từ input rồi chuyển thành String.
                String taikhoan = edtDKTaiKhoan.getText().toString();
                String matkhau = edtDKMatKhau.getText().toString();
                String email = edtDKEmail.getText().toString();

                // Gọi method CreateTaiKhoan() để tạo tài khoản từ dữ liệu đã nhập.
                TaiKhoan taikhoan1 = CreateTaiKhoan();

                // Kiểm tra xem đã nhập đủ thông tin chưa.
                if(taikhoan.equals("") || matkhau.equals("") || email.equals("")){
                    Toast.makeText(MainDangKy.this,"Bạn chưa nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                    Log.e("Thông báo : ","Bạn chưa nhập đầy đủ thông tin");
                }
                // Nếu đầy đủ thông tin
                else{
                    // Kiểm tra xem trùng tài khoản không để có thể hiển thị thông báo tài khoản trùng.
                    // Thêm tài khoản mới vào database bằng method AddTaiKhoan().
                    databaseDocTruyen.AddTaiKhoan(taikhoan1);
                    Toast.makeText(MainDangKy.this,"Đăng ký thành công ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method trở về đăng nhập
    private void ReturnDangNhap() {
        btnDKDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_dang_ky_landscape);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main_dang_ky);
        }
        AnhXa();
        ClickDangKy();
        ReturnDangNhap();
    }
}