package com.wipro.miliu.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.wipro.miliu.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvamoChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvamoChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AvamoChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvamoChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvamoChatFragment newInstance(String param1, String param2) {
        AvamoChatFragment fragment = new AvamoChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        View view=inflater.inflate(R.layout.fragment_avamo_chat, container, false);

        WebView avamochat=(WebView)view.findViewById(R.id.avamochat);

        avamochat.getSettings().setJavaScriptEnabled(true);
        avamochat.getSettings().setLoadWithOverviewMode(true);
        avamochat.evaluateJavascript("javascript: " +
                        "var AvaamoChatBot=function(t){function o(t,o){var n=document.createElement(\"script\");n.setAttribute(\"src\",t),n.onload=o,document.body.appendChild(n)}return this.options=t||{},this.load=function(t){o(this.options.url,function(){window.Avaamo.addFrame(),t&&\"function\"==typeof(t)&&t(window.Avaamo)})},this};\n" +
                        "var chatBox = new AvaamoChatBot({url: 'https://c0.avaamo.com/web_channels/d9d2ac61-22ed-494a-bcf7-33337e2a2771.js?audio_visible=true&audio_on=true&history=false&banner_text=HI&banner_title=Hi%2C+I%27m+Connie%2C+your+personal+change+assistant%2E+What+can+I+do+for+you+today%3F&user_info=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJGaXJzdE5hbWUiOiJDbGFpcmUiLCJMYXN0TmFtZSI6Ikdvb2R3aWxsIiwiZ2VuZGVyIjoiZmVtYWxlIiwiTWFyaXRhbFN0YXR1cyI6IlNpbmdsZSIsIk9jY3VwYXRpb24iOiJDb25zdWx0YW50IiwiRWR1Y2F0aW9uIjoiTUJBIiwiU29jaWFsU2VnbWVudCI6Ik1pbGxlbmlhbCIsIlNvY2lhbElEIjoiQENsYWlyZWhhc0dvb2R3aWxsIiwiSW5zdXJhbmNlTmVlZHMiOiJQZXJzb25hbCBhdXRvIGluc3VyYW5jZSIsIkxpZmVFdmVudCI6IlByb21vdGlvbiIsIlRyaWdnZXJGb3JJbnN1cmFuY2VOZWVkcyI6ImJ1eWluZyBhIGNhciIsIlRyaWdnZXJEZXRhaWxzIjoiSG9uZGEgQ1JWIn0.46AMiA_JTo8fYC27JgS6ZnfPDZEj4hGb3JjhohNP1PY'});\n" +
                        "chatBox.load();\n",
                null);




        // Inflate the layout for this fragment
        return view;
    }

}
