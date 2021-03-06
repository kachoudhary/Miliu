package com.wipro.miliu.View.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wipro.miliu.R;
import com.wipro.miliu.View.Fragments.AvamoChatFragment;
import com.wipro.miliu.View.Fragments.Fragment1;
import com.wipro.miliu.View.Fragments.Fragment2;
import com.wipro.miliu.View.Fragments.Fragment3;
import com.wipro.miliu.View.Fragments.Fragment4;
import com.wipro.miliu.View.Fragments.Fragment5;
import com.wipro.miliu.View.Fragments.Fragment6;
import com.wipro.miliu.View.Fragments.Fragment7;
import com.wipro.miliu.View.Fragments.Fragment8;
import com.wipro.miliu.View.Fragments.Fragment9;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    TextView homebutton;
    TextView claimbutton;
    TextView accountbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/glyphicons_halflings_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        setContentView(R.layout.activity_fullscreen);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
        homebutton =(TextView)findViewById(R.id.homebutton);
        claimbutton=(TextView)findViewById(R.id.claimbutton);
        accountbutton=(TextView)findViewById(R.id.accountbutton);

        fragmentloading();
        footerloading();

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private void fragmentloading() {

        Fragment1 fragment1=new Fragment1();
        Fragment2 fragment2=new Fragment2();
        Fragment3 fragment3=new Fragment3();
        Fragment4 fragment4=new Fragment4();
        Fragment5 fragment5=new Fragment5();
        Fragment6 fragment6=new Fragment6();
        Fragment7 fragment7=new Fragment7();
        Fragment8 fragment8=new Fragment8();
        Fragment9 fragment9=new Fragment9();
        AvamoChatFragment fragment10=new AvamoChatFragment();

        FragmentTransaction fragment1Transaction=getSupportFragmentManager().beginTransaction();

        fragment1Transaction.add(R.id.framlayout1,fragment1);
        fragment1Transaction.add(R.id.framlayout2,fragment2);
        fragment1Transaction.add(R.id.framlayout3,fragment3);
        fragment1Transaction.add(R.id.framlayout4,fragment4);
        fragment1Transaction.add(R.id.framlayout5,fragment5);
        fragment1Transaction.add(R.id.framlayout6,fragment6);
        fragment1Transaction.add(R.id.framlayout7,fragment7);
        fragment1Transaction.add(R.id.framlayout8,fragment8);
        fragment1Transaction.add(R.id.framlayout9,fragment9);
        //fragment1Transaction.add(R.id.fragment_avamo_chat,fragment10);

        fragment1Transaction.commit();


        final WebView avamochat=(WebView)findViewById(R.id.avamochat);

        avamochat.getSettings().setJavaScriptEnabled(true);
        avamochat.evaluateJavascript("javascript: " +
                        "var AvaamoChatBot=function(t){function o(t,o){var n=document.createElement(\"script\");n.setAttribute(\"src\",t),n.onload=o,document.body.appendChild(n)}return this.options=t||{},this.load=function(t){o(this.options.url,function(){window.Avaamo.addFrame(),t&&\"function\"==typeof(t)&&t(window.Avaamo)})},this};\n" +
                        "var chatBox = new AvaamoChatBot({url: 'https://c0.avaamo.com/web_channels/d9d2ac61-22ed-494a-bcf7-33337e2a2771.js?audio_visible=true&audio_on=true&history=false&banner_text=HI&banner_title=Hi%2C+I%27m+Connie%2C+your+personal+change+assistant%2E+What+can+I+do+for+you+today%3F&user_info=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJGaXJzdE5hbWUiOiJDbGFpcmUiLCJMYXN0TmFtZSI6Ikdvb2R3aWxsIiwiZ2VuZGVyIjoiZmVtYWxlIiwiTWFyaXRhbFN0YXR1cyI6IlNpbmdsZSIsIk9jY3VwYXRpb24iOiJDb25zdWx0YW50IiwiRWR1Y2F0aW9uIjoiTUJBIiwiU29jaWFsU2VnbWVudCI6Ik1pbGxlbmlhbCIsIlNvY2lhbElEIjoiQENsYWlyZWhhc0dvb2R3aWxsIiwiSW5zdXJhbmNlTmVlZHMiOiJQZXJzb25hbCBhdXRvIGluc3VyYW5jZSIsIkxpZmVFdmVudCI6IlByb21vdGlvbiIsIlRyaWdnZXJGb3JJbnN1cmFuY2VOZWVkcyI6ImJ1eWluZyBhIGNhciIsIlRyaWdnZXJEZXRhaWxzIjoiSG9uZGEgQ1JWIn0.46AMiA_JTo8fYC27JgS6ZnfPDZEj4hGb3JjhohNP1PY'});\n" +
                        "chatBox.load();\n",
                null);
    }

    private void footerloading() {

        Intent mainintent=getIntent();
        String intentrecievedvalue=mainintent.getStringExtra("kartikey");

        claimbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                claimbutton.setTextColor(Color.parseColor("#008000"));
                claimbutton.setPaintFlags(claimbutton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                Intent claimbuttonactivity=new Intent(FullscreenActivity.this, RaiseClaimActivity.class);
                claimbuttonactivity.putExtra("kartikey","isgreat");
                startActivity(claimbuttonactivity);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });

        accountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountbutton.setTextColor(Color.parseColor("#008000"));
                accountbutton.setPaintFlags(claimbutton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                Intent accountactivity=new Intent(FullscreenActivity.this,Myaccountactivity.class);
                accountactivity.putExtra("kartikey","isgreat");
                startActivity(accountactivity);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
    }
}