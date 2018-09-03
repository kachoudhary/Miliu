package com.wipro.miliu.View.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wipro.miliu.R;
import com.wipro.miliu.View.Activities.RaiseClaimActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_accountupload#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_accountupload extends Fragment {


    public fragment_accountupload() {
        // Required empty public constructor
    }

    ImageView currentImage=null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_accountupload.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_accountupload newInstance(String param1, String param2) {
        fragment_accountupload fragment = new fragment_accountupload();
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
        View inflaterview=inflater.inflate(R.layout.fragment_accountupload, container, false);

        final ImageView drivinglicensescan=(ImageView)inflaterview.findViewById(R.id.scnddriviglicenseimg);
        final ImageView policereportscan=(ImageView)inflaterview.findViewById(R.id.scndpolicerprtimg);
        final Button continuebtn=(Button)inflaterview.findViewById(R.id.continuebtn);
        final Button clearbtn=(Button)inflaterview.findViewById(R.id.clrbtn);


        drivinglicensescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("karchou","drivinglicenseimage pressed");
                selectImage(drivinglicensescan);
            }
        });


        policereportscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("karchou","policereportimage pressed");
                selectImage(policereportscan);
            }
        });

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drivinglicensescan.buildDrawingCache();
                policereportscan.buildDrawingCache();

                Bitmap bitmapdrivinglicense=drivinglicensescan.getDrawingCache();
                Bitmap bitmappolicereport=policereportscan.getDrawingCache();

                ByteArrayOutputStream drivinglicensestream=new ByteArrayOutputStream();
                ByteArrayOutputStream policereportstream=new ByteArrayOutputStream();

                bitmapdrivinglicense.compress(Bitmap.CompressFormat.JPEG,90,drivinglicensestream);
                bitmappolicereport.compress(Bitmap.CompressFormat.JPEG,90,policereportstream);

                byte[] drivinglicensebyte=drivinglicensestream.toByteArray();
                byte[] policereportbyte=policereportstream.toByteArray();

                final String img_drivinglicense= Base64.encodeToString(drivinglicensebyte,0);
                final String img_policereport= Base64.encodeToString(policereportbyte,0);

                Log.i("karchoustring",img_drivinglicense + img_policereport);

                loaderenabled();

            }
        });

        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drivinglicensescan.setImageResource(android.R.color.transparent);
                policereportscan.setImageResource(android.R.color.transparent);
            }
        });

        return inflaterview;
    }

    private void loaderenabled(){
        Fragment_imageprocessing fragment_imageprocessing=new Fragment_imageprocessing();
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_accountmain,fragment_imageprocessing);
        fragmentTransaction.addToBackStack(fragment_imageprocessing.toString());
        fragmentTransaction.commit();
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
        if (resultCode== RaiseClaimActivity.RESULT_OK) {
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

}
