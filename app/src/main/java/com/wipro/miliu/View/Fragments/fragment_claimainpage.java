package com.wipro.miliu.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.wipro.miliu.R;
import com.wipro.miliu.View.Fragments.fragment_accountupload;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_claimainpage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_claimainpage extends Fragment {

    public fragment_claimainpage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_claimainpage.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_claimainpage newInstance(String param1, String param2) {
        fragment_claimainpage fragment = new fragment_claimainpage();
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
        View viewinflater=inflater.inflate(R.layout.fragment_claimainpage, container, false);
        Button uploadbtn=(Button)viewinflater.findViewById(R.id.uploadbtn);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaduploadfragment();
            }
        });
        return viewinflater;
    }

    private void loaduploadfragment() {
        fragment_accountupload fragmentaccountupload=new fragment_accountupload();
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_accountmain,fragmentaccountupload);
        fragmentTransaction.addToBackStack(fragmentaccountupload.toString());
        fragmentTransaction.commit();
    }

}
