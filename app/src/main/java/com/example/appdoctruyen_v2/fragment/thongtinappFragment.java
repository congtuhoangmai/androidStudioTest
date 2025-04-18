package com.example.appdoctruyen_v2.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appdoctruyen_v2.R;

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

        String thongtin = "Ứng dụng được phát triển bởi Nhóm 2. \n"
        + "Với ứng dụng này, bạn có thể khám phá và đọc hàng loạt truyện hấp dẫn, lưu lại, đánh giá, chia sẻ những truyện yêu thích"
        + "Hãy bắt đầu hành trình khám phá thế giới truyện ngay hôm nay!\n";
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