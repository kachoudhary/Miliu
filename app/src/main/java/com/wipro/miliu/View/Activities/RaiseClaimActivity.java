package com.wipro.miliu.View.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.wipro.miliu.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class RaiseClaimActivity extends AppCompatActivity {
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
    TextView accountbutton;
    Button clearbtn;

    ImageView vinnumberimage;
    ImageView numberplateimage;
    ImageView frontrightimage;
    ImageView frontleftimage;
    ImageView backrightimage;
    ImageView backleftimage;
    ImageView sideleftimage;
    ImageView siderightimage;
    ImageView damageoneimage;
    ImageView currentImage=null;

    static Map<String, String> postParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_raise_claim);

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
        accountbutton=(TextView)findViewById(R.id.accountbutton);

        vinnumberimage=(ImageView)findViewById(R.id.vinnumberimage);
        numberplateimage=(ImageView)findViewById(R.id.numberplateimage);
        frontrightimage=(ImageView)findViewById(R.id.frontrightimage);
        frontleftimage=(ImageView)findViewById(R.id.frontleftimage);
        backrightimage=(ImageView)findViewById(R.id.backrightimage);
        backleftimage=(ImageView)findViewById(R.id.backleftimage);
        sideleftimage=(ImageView)findViewById(R.id.sideleftimage);
        siderightimage=(ImageView)findViewById(R.id.siderightimage);
        damageoneimage=(ImageView)findViewById(R.id.damageoneimage);


        footerloading();
        imageclicklistener();
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

    private void footerloading() {

        Intent mainintent=getIntent();
        String intentrecievedvalue=mainintent.getStringExtra("kartikey");

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homebutton.setTextColor(Color.parseColor("#008000"));
                homebutton.setPaintFlags(homebutton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                Intent claimbuttonactivity=new Intent(RaiseClaimActivity.this, FullscreenActivity.class);
                claimbuttonactivity.putExtra("kartikey","isgreat");
                startActivity(claimbuttonactivity);
            }
        });

        accountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountbutton.setTextColor(Color.parseColor("#008000"));
                accountbutton.setPaintFlags(accountbutton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                Intent accountactivity=new Intent(RaiseClaimActivity.this,Myaccountactivity.class);
                accountactivity.putExtra("kartikey","isgreat");
                startActivity(accountactivity);
            }
        });
    }


    private void imageclicklistener() {

        vinnumberimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("karchou","vinnumberimage pressed");
                selectImage(vinnumberimage);
            }
        });

        numberplateimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("karchou","numberplateimage pressed");
                selectImage(numberplateimage);
            }
        });

        frontrightimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("karchou","frontrightimage pressed");
                selectImage(frontrightimage);
            }
        });

        frontleftimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("karchou","frontleftimage pressed");
                selectImage(frontleftimage);
            }
        });

        backrightimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("karchou","backrightimage pressed");
                selectImage(backrightimage);
            }
        });


        backleftimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("karchou","backleftimage pressed");
                selectImage(backleftimage);
            }
        });


        sideleftimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("karchou","sideleftimage pressed");
                selectImage(sideleftimage);
            }
        });

        siderightimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("karchou","siderightimage pressed");
                selectImage(siderightimage);
            }
        });

        damageoneimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("karchou","damageoneimage pressed");
                selectImage(damageoneimage);
            }
        });
    }

    private void  selectImage(ImageView selectedImageView) {
        final CharSequence[] options={"Take Photo","Choose from Gallery","Cancel"};
        currentImage=selectedImageView;
        final AlertDialog.Builder imageuploadbuilder=new AlertDialog.Builder(RaiseClaimActivity.this);
        imageuploadbuilder.setTitle("Select Photo");
        imageuploadbuilder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {

              if (options[item].equals("Take Photo")) {
                  Intent photoclicker=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                  File f=new File(android.os.Environment.getExternalStorageDirectory(),"temp.jpg");
                  photoclicker.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                  startActivityForResult(photoclicker,1);
              }

              else if (options[item].equals("Choose from Gallery")) {
                  Intent galerychooser=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                  galerychooser.setType("image/*");
                  startActivityForResult(galerychooser,2);
              }
              else if (options[item].equals("Cancel")) {
                  dialogInterface.dismiss();
              }
            }
        });
        imageuploadbuilder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            if (requestCode==1) {
                File f=new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f=temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmpaOptions=new BitmapFactory.Options();

                    bitmap=BitmapFactory.decodeFile(f.getAbsolutePath(),bitmpaOptions);
                    currentImage.setImageBitmap(bitmap);

                    String path=android.os.Environment.getExternalStorageDirectory()
                                +File.separator
                                +"Phoenix" +File.separator+"default";
                    f.delete();


                    OutputStream outFile=null;
                    File file=new File(path,String.valueOf(System.currentTimeMillis())+".jpg");
                    try {
                        outFile=new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG,85,outFile);
                        outFile.flush();
                        outFile.close();
                    }
                    catch (FileNotFoundException ex){
                        ex.printStackTrace();
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else if (requestCode==2) {
                   try {
                    final Uri selectimage=data.getData();
                    final InputStream imageStream=getContentResolver().openInputStream(selectimage);
                    final Bitmap selectedImaage=BitmapFactory.decodeStream(imageStream);
                    currentImage.setImageBitmap(selectedImaage);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                    Toast.makeText(RaiseClaimActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

           /*   String filepath[] ={MediaStore.Images.Media.DATA};
                Cursor c=getContentResolver().query(selectimage,filepath,null,null,null);
                c.moveToFirst();
                int ColumnIndex=c.getColumnIndex(filepath[0]);
                String picturepath=c.getString(ColumnIndex);
                c.close();
                Bitmap thumbnail=(BitmapFactory.decodeFile(picturepath));
                Log.i("karchou imagepath", picturepath);
                vinnumberimage.setImageBitmap(thumbnail); */
            }
            else {
                Toast.makeText(RaiseClaimActivity.this, "Either pick Image or Click it",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void clearbtnactn(View view) {
        vinnumberimage.setImageResource(R.drawable.number_plate);
        numberplateimage.setImageResource(R.drawable.front_right);
        frontrightimage.setImageResource(R.drawable.front_left);
        frontleftimage.setImageResource(R.drawable.back_right);
        backrightimage.setImageResource(R.drawable.number_plate);
        backleftimage.setImageResource(R.drawable.back_left);
        sideleftimage.setImageResource(R.drawable.number_plate);
        siderightimage.setImageResource(R.drawable.back_right);
        damageoneimage.setImageResource(R.drawable.damage1);
        Toast.makeText(RaiseClaimActivity.this, "Cleared",Toast.LENGTH_SHORT).show();
    }

    public void uploadbtnclick(View view) {

        vinnumberimage.buildDrawingCache();
        numberplateimage.buildDrawingCache();
        frontrightimage.buildDrawingCache();
        frontleftimage.buildDrawingCache();
        backrightimage.buildDrawingCache();
        backleftimage.buildDrawingCache();
        sideleftimage.buildDrawingCache();
        siderightimage.buildDrawingCache();
        damageoneimage.buildDrawingCache();



        Bitmap bitmapvinnumberimage=vinnumberimage.getDrawingCache();
        Bitmap bitmapnumberplateimage=numberplateimage.getDrawingCache();
        Bitmap bitmapfrontrightimage=frontrightimage.getDrawingCache();
        Bitmap bitmapfrontleftimage=frontleftimage.getDrawingCache();
        Bitmap bitmapbackrightimage=backrightimage.getDrawingCache();
        Bitmap bitmapbackleftimage=backleftimage.getDrawingCache();
        Bitmap bitmapsideleftimage=sideleftimage.getDrawingCache();
        Bitmap bitmapsiderightimage=siderightimage.getDrawingCache();
        Bitmap bitmapdamageoneimage=damageoneimage.getDrawingCache();



        ByteArrayOutputStream vinnumberimagestream=new ByteArrayOutputStream();
        ByteArrayOutputStream numberplateimagestream=new ByteArrayOutputStream();
        ByteArrayOutputStream frontrightimagestream=new ByteArrayOutputStream();
        ByteArrayOutputStream frontleftimagestream=new ByteArrayOutputStream();
        ByteArrayOutputStream backrightimagestream=new ByteArrayOutputStream();
        ByteArrayOutputStream backleftimagestream=new ByteArrayOutputStream();
        ByteArrayOutputStream sideleftimagestream=new ByteArrayOutputStream();
        ByteArrayOutputStream siderightimagestream=new ByteArrayOutputStream();
        ByteArrayOutputStream damageoneimagestream=new ByteArrayOutputStream();


        bitmapvinnumberimage.compress(Bitmap.CompressFormat.JPEG,90,vinnumberimagestream);
        bitmapnumberplateimage.compress(Bitmap.CompressFormat.JPEG,90,numberplateimagestream);
        bitmapfrontrightimage.compress(Bitmap.CompressFormat.JPEG,90,frontrightimagestream);
        bitmapfrontleftimage.compress(Bitmap.CompressFormat.JPEG,90,frontleftimagestream);
        bitmapbackrightimage.compress(Bitmap.CompressFormat.JPEG,90,backrightimagestream);
        bitmapbackleftimage.compress(Bitmap.CompressFormat.JPEG,90,backleftimagestream);
        bitmapsideleftimage.compress(Bitmap.CompressFormat.JPEG,90,sideleftimagestream);
        bitmapsiderightimage.compress(Bitmap.CompressFormat.JPEG,90,siderightimagestream);
        bitmapdamageoneimage.compress(Bitmap.CompressFormat.JPEG,90,damageoneimagestream);


        byte[] vinnumberimagebyte=vinnumberimagestream.toByteArray();
        byte[] numberplateimagebyte=numberplateimagestream.toByteArray();
        byte[] frontrightimagebyte=frontrightimagestream.toByteArray();
        byte[] frontleftimagebyte=frontleftimagestream.toByteArray();
        byte[] backrightimagebyte=backrightimagestream.toByteArray();
        byte[] backleftimagebyte=backleftimagestream.toByteArray();
        byte[] sideleftimagebyte=sideleftimagestream.toByteArray();
        byte[] siderightimagebyte=siderightimagestream.toByteArray();
        byte[] damageoneimagebyte=damageoneimagestream.toByteArray();

        final String img_vinnumber= Base64.encodeToString(vinnumberimagebyte,0);
        final String img_numberplate= Base64.encodeToString(numberplateimagebyte,0);
        final String img_frontright= Base64.encodeToString(frontrightimagebyte,0);
        final String img_frontleft= Base64.encodeToString(frontleftimagebyte,0);
        final String img_backright= Base64.encodeToString(backrightimagebyte,0);
        final String img_backleft= Base64.encodeToString(backleftimagebyte,0);
        final String img_sideleft= Base64.encodeToString(sideleftimagebyte,0);
        final String img_sideright= Base64.encodeToString(siderightimagebyte,0);
        final String img_damageone= Base64.encodeToString(damageoneimagebyte,0);

        Log.i("karchoustring",img_vinnumber +"/n"+ img_numberplate +"/n"+ img_frontright +"/n"+ img_frontleft+
                "/n"+ img_backright +"/n"+ img_backleft +"/n"+ img_sideleft +"/n"+ img_sideright+"/n"+ img_damageone);

        Log.i("decode",img_vinnumber);

        postParam= new HashMap<String, String>();
        postParam.put("vinnumberimage", img_vinnumber);
        postParam.put("numberplateimage", img_numberplate);
        postParam.put("frontrightimage", img_frontright);
        postParam.put("frontleftimage", img_frontleft);
        postParam.put("backrightimage", img_backright);
        postParam.put("backleftimage", img_backleft);
        postParam.put("sideleftimage", img_sideleft);
        postParam.put("siderightimage", img_sideright);
        postParam.put("damageoneimage", img_damageone);
    }


    private void makeJSONObjreq() {

        RequestQueue queue= Volley.newRequestQueue(this);


        //showprogressdialog
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, null, new JSONObject(postParam), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response",response.toString());
                //hideprogressdialog
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Err", "Error: " + error.getMessage());
                //hideProgressDialog
            }
        }) {

            public Map<String,String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers=new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
}
