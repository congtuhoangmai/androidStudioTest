package com.example.appdoctruyen_v2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen_v2.adapter.adapterTruyen;
import com.example.appdoctruyen_v2.database.databasedoctruyen;
import com.example.appdoctruyen_v2.model.Truyen;

import java.util.ArrayList;

//import android.widget.SearchView;
//import android.widget.Toolbar;
//import android.widget.SearchView;

public class MainTimKiem extends AppCompatActivity {

    RecyclerView listView;
    // Toolbar toolbar;
    // SearchView searchView;
    EditText edt;

    ArrayList<Truyen> TruyenArrayList;
    //
    ArrayList<Truyen> arrayList;

    adapterTruyen adaptertruyen;
    databasedoctruyen databaseDocTruyen;
    ImageView homeImage;

//  ArrayAdapter<Truyen> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tim_kiem);

        homeImage = findViewById(R.id.homeIcon);
        listView = findViewById(R.id.listviewtimkiem);
        // toolbar = findViewById(R.id.toolbartimkiem);
        edt = findViewById(R.id.timkiem);

        //ActionBar();
        initList();

        // Xử lý sự kiến click vào home icon
        homeImage.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });


        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    // Search
    private void filter(String text){

        // Xóa sau mỗi lần gọi tới filter
        arrayList.clear();

        ArrayList<Truyen> filteredList = new ArrayList<>();

        for(Truyen item : TruyenArrayList){
            if (item.getTenTruyen().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);

                // Thêm dữ liệu để hiển thị ra item nội dung
                arrayList.add(item);
            }
        }
        adaptertruyen.filterList(filteredList);
    }

    // Hàm gán dữ liệu từ CSDL vào listview
    public void initList(){
        TruyenArrayList = new ArrayList<>();
        arrayList = new ArrayList<>();
        databaseDocTruyen = new databasedoctruyen(this);

        Cursor cursor1 = databaseDocTruyen.getData2();
        while (cursor1.moveToNext()){

            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);

            TruyenArrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk));

            //Thêm dữ liệu vào mảng
            arrayList.add(new Truyen(id,tentruyen,noidung,anh,id_tk));
        }

        // Không dùng getApplicationContext() => this
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        listView.setLayoutManager(linearLayoutManager);

        // adaptertruyen = new adapterTruyen(getApplicationContext(), TruyenArrayList);
        adaptertruyen = new adapterTruyen(this, TruyenArrayList);
        listView.setAdapter(adaptertruyen);
        cursor1.moveToFirst();
        cursor1.close();
    }

//    Tạo thanh action bar với toolbar
//    private void ActionBar() {
//        //Hàm hỗ trợ toolBar
//        setSupportActionBar(toolbar);
//        //set nút của toolbar là true
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//    }
}