package com.apps.zarmeen.boom;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PublishActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int PICK_FROM_GALLERY = 1;
    String title,genre,df;
    DBhelper mDbHelper = new DBhelper(this);
    SQLiteDatabase db;
    ImageView a;
    Drawable image;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        a=findViewById(R.id.imgView);
        buttonLoadImage.setOnClickListener(new View.OnClickListener()
        {
                public void onClick(View v){
                    try {
                        if (ActivityCompat.checkSelfPermission(PublishActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(PublishActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                        } else {
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        });


        Spinner dropdown1 = findViewById(R.id.spinner1);
        String[] items1 = new String[]{"Monday","Tuesday","Wednesday","Friday","Saturday","Sunday"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        dropdown1.setAdapter(adapter1);
        dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                df=(String)parent.getItemAtPosition(position);
                Toast.makeText(PublishActivity.this,  (String) parent.getItemAtPosition(position)+" selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        Spinner dropdown2 = findViewById(R.id.spinner2);
        String[] items2 = new String[]{"Comedy", "Horror", "Slice of life"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);
        dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PublishActivity.this,  (String) parent.getItemAtPosition(position)+" selected", Toast.LENGTH_LONG).show();
                genre=(String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults){
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            image=imageView.getDrawable();
        }
    }

    public void NextOnClick(View view){
        EditText dt=findViewById(R.id.title);
        title=dt.getText().toString();
        if(image!=null&&title!=""&&df!=null&&genre!=null){
            db = mDbHelper.getReadableDatabase();
            Bitmap bitmap =  ((BitmapDrawable)image).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress( Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapData = stream.toByteArray();
            ContentValues insertValues = new ContentValues();
            insertValues.put("Title", title);
            insertValues.put("Genre", genre);
            insertValues.put("Featuring", df);
            insertValues.put("Image", bitmapData);
            db.insert("Comics", null, insertValues);
            Cursor cursor=db.rawQuery("select CId FROM Comics where Title='"+title+"'", null);
            int data=0;
            if (cursor.moveToFirst()) {
                 String datas= cursor.getString(cursor.getColumnIndex("CId"));
                data=Integer.parseInt(datas);
            }
            cursor.close();
            mDbHelper.change=true;
            Intent intent =  new Intent(this,PublishEpisodeActivity.class);
            intent.putExtra("cid",data);
            startActivity(intent);
        }
    }
}