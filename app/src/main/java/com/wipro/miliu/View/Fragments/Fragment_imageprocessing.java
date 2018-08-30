package com.wipro.miliu.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wipro.miliu.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_imageprocessing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_imageprocessing extends Fragment {

    public Fragment_imageprocessing() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_imageprocessing.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_imageprocessing newInstance(String param1, String param2) {
        Fragment_imageprocessing fragment = new Fragment_imageprocessing();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflaterview=inflater.inflate(R.layout.fragment_imageprocessing, container, false);
        return inflaterview;
    }

}
