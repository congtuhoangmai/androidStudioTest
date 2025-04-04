package com.example.appdoctruyen_v2;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.appdoctruyen_v2.adapter.adapterTruyen;
import com.example.appdoctruyen_v2.database.databasedoctruyen;
import com.example.appdoctruyen_v2.model.TaiKhoan;
import com.example.appdoctruyen_v2.model.Truyen;
import com.example.appdoctruyen_v2.model.chuyenmuc;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import  com.example.appdoctruyen_v2.database.*;
import java.util.ArrayList;
import  com.example.appdoctruyen_v2.adapter.*;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    FloatingActionButton fab;
    RecyclerView listViewNew2,listViewNew;


    String email;
    String tentaikhoan;

    ArrayList<Truyen> TruyenArraylist;
    ArrayList<Truyen> TruyenArraylist2;

    com.example.appdoctruyen_v2.adapter.adapterTruyenV2 adapterTruyen;

    ArrayList<chuyenmuc> chuyenmucArrayList;
    ArrayList<TaiKhoan> taiKhoanArrayList;

    com.example.appdoctruyen_v2.database.databasedoctruyen databasedoctruyen;

    adapterchuyenmuc adapterchuyenmuc;
    adapterthongtin adapterthongtin;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databasedoctruyen = new databasedoctruyen(getContext());
        Intent intentpq = getActivity().getIntent();

        // Lấy các thông tin từ Intent được truyền vào
        int i = intentpq.getIntExtra("phanq",0);
        int idd = intentpq.getIntExtra("idd",0);
        email = intentpq.getStringExtra("email");
        tentaikhoan = intentpq.getStringExtra("tentaikhoan");

        // Các phương thức khởi tạo giao diện
        AnhXa();
        ActionViewFlipper();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Tăng bố cục cho fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    //  Hàm chạy tự động chuyển slide
    private void ActionViewFlipper() {

        ArrayList<Integer> mangquangcao = new ArrayList<>();
        mangquangcao.add(R.drawable.conan);
        mangquangcao.add(R.drawable.dragonball);
        mangquangcao.add(R.drawable.doremon);
        mangquangcao.add(R.drawable.onepiece);
        mangquangcao.add(R.drawable.inuyasha);

        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(mangquangcao.get(i)); // Load ảnh từ drawable
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            viewFlipper.addView(imageView);
        }

        // Cấu hình tự động chuyển slide
        viewFlipper.startFlipping();
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        // Thiết lập animation cho slide
        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);

        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

        // Đảm bảo nó bắt đầu chạy
        if (!viewFlipper.isFlipping()) {
            viewFlipper.startFlipping();
        }
    }


    private void AnhXa() {
        // Ánh xạ các view từ layout
        viewFlipper = getActivity().findViewById(R.id.viewflipper);
        listViewNew = getActivity().findViewById(R.id.listviewNew);
        listViewNew2 = getActivity().findViewById(R.id.listviewNew2);

        // Lấy data từ database
        Cursor cursor1 = databasedoctruyen.getData1(); // Lấy truyện mới nhất
        Cursor cursor2 = databasedoctruyen.getData2(); // Lấy all truyện

        TruyenArraylist = new ArrayList<>();
        while (cursor1.moveToNext()) {

            int id = cursor1.getInt(0);
            String tentruyen = cursor1.getString(1);
            String noidung = cursor1.getString(2);
            String anh = cursor1.getString(3);
            int id_tk = cursor1.getInt(4);

            // Truyện mới nhất
            TruyenArraylist.add(new Truyen(id,tentruyen,noidung,anh,id_tk));
        }

        TruyenArraylist2 = new ArrayList<>();
        while (cursor2.moveToNext()) {
            int id = cursor2.getInt(0);
            String tentruyen = cursor2.getString(1);
            String noidung = cursor2.getString(2);
            String anh = cursor2.getString(3);
            int id_tk = cursor2.getInt(4);

            // Tất cả truyện
            TruyenArraylist2.add(new Truyen(id,tentruyen,noidung,anh,id_tk));
        }

        // Cấu hình RecyclerView
        // MỚI NHẤT
        listViewNew.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        listViewNew.setLayoutManager(gridLayoutManager);

        // Tạo và gán Adapter cho RecyclerView
        // getContext() trả về Activity chứa Fragment (nên sử dụng khi ở bên trong Fragment)
        adapterTruyen = new adapterTruyenV2(getContext(),TruyenArraylist);
        listViewNew.setAdapter(adapterTruyen);

        // TOP TRUYỆN
        listViewNew2.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getContext(), 3);
        gridLayoutManager2.setOrientation(GridLayoutManager.VERTICAL);
        listViewNew2.setLayoutManager(gridLayoutManager2);

        adapterTruyen = new adapterTruyenV2(getContext(),TruyenArraylist2);
        listViewNew2.setAdapter(adapterTruyen);


        cursor1.moveToFirst();
        cursor1.close();
        cursor2.moveToFirst();
        cursor2.close();

        taiKhoanArrayList = new ArrayList<>();
        taiKhoanArrayList.add(new TaiKhoan(tentaikhoan,email));
    }
}
