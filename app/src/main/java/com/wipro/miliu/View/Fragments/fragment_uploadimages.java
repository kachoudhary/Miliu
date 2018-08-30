package com.wipro.miliu.View.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wipro.miliu.R;
import com.wipro.miliu.View.Activities.RaiseClaimActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_uploadimages#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_uploadimages extends Fragment {

    public fragment_uploadimages() {
        // Required empty public constructor
    }

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
    final static String posturl="https://jsonplaceholder.typicode.com/posts/";


    public static fragment_uploadimages newInstance(String param1, String param2) {
        fragment_uploadimages fragment = new fragment_uploadimages();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View createinflater=inflater.inflate(R.layout.fragment_uploadimages, container, false);

        vinnumberimage=(ImageView)createinflater.findViewById(R.id.vinnumberimage);
        numberplateimage=(ImageView)createinflater.findViewById(R.id.numberplateimage);
        frontrightimage=(ImageView)createinflater.findViewById(R.id.frontrightimage);
        frontleftimage=(ImageView)createinflater.findViewById(R.id.frontleftimage);
        backrightimage=(ImageView)createinflater.findViewById(R.id.backrightimage);
        backleftimage=(ImageView)createinflater.findViewById(R.id.backleftimage);
        sideleftimage=(ImageView)createinflater.findViewById(R.id.sideleftimage);
        siderightimage=(ImageView)createinflater.findViewById(R.id.siderightimage);
        damageoneimage=(ImageView)createinflater.findViewById(R.id.damageoneimage);
        imageclicklistener();


        Button clearbtn=(Button)createinflater.findViewById(R.id.clearbtn);
        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vinnumberimage.setImageResource(R.drawable.vin);
                numberplateimage.setImageResource(R.drawable.number_plate);
                frontrightimage.setImageResource(R.drawable.front_right);
                frontleftimage.setImageResource(R.drawable.front_left);
                backrightimage.setImageResource(R.drawable.back_right);
                backleftimage.setImageResource(R.drawable.back_left);
                sideleftimage.setImageResource(R.drawable.side_left);
                siderightimage.setImageResource(R.drawable.back_right);
                damageoneimage.setImageResource(R.drawable.damage1);
                Toast.makeText(getActivity(), "Cleared",Toast.LENGTH_SHORT).show();
            }
        });


        Button uploadbtn=(Button)createinflater.findViewById(R.id.uploadbtn);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    makeJSONObjreq();
            }
        });
        return createinflater;
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
        final AlertDialog.Builder imageuploadbuilder=new AlertDialog.Builder(getContext());
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RaiseClaimActivity.RESULT_OK) {
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
                    final InputStream imageStream=getContext().getContentResolver().openInputStream(selectimage);
                    final Bitmap selectedImaage=BitmapFactory.decodeStream(imageStream);
                    currentImage.setImageBitmap(selectedImaage);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                }

            /*  String filepath[] ={MediaStore.Images.Media.DATA};
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
                Toast.makeText(getActivity(), "Either pick Image or Click it",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void makeJSONObjreq() {

        RequestQueue queue= Volley.newRequestQueue(getContext());

        //showprogressdialog
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, posturl, new JSONObject(postParam), new Response.Listener<JSONObject>() {
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

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";

            }
        };
        queue.add(jsonObjectRequest);
    }
}
