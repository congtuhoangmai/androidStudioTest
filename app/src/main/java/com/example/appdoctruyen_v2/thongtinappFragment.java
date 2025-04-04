package com.example.appdoctruyen_v2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link thongtinappFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class thongtinappFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView txtThongTin;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public thongtinappFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment thongtinappFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static thongtinappFragment newInstance(String param1, String param2) {
        thongtinappFragment fragment = new thongtinappFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtThongTin = getActivity().findViewById(R.id.textviewthongtin);

        String thongtin = " Ứng dụng được phát triển bởi Nhóm 2, mang đến cho bạn trải nghiệm đọc truyện tuyệt vời. Với ứng dụng này, bạn có thể \n" +
                " Khám phá và đọc hàng loạt truyện hấp dẫn. \n"+" Lưu lại những truyện yêu thích để đọc sau. \n"+" Đăng bài và chia sẻ cảm nhận của bạn với cộng đồng. \n" +" Đánh giá và theo dõi các truyện mà bạn yêu thích. \n"+
                "Hãy bắt đầu hành trình khám phá thế giới truyện ngay hôm nay! Chúng tôi luôn nỗ lực để mang đến trải nghiệm tốt nhất cho bạn.";
        txtThongTin.setText(thongtin);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thongtinapp, container, false);
    }
}