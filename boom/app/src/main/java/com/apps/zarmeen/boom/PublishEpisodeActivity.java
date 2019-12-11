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
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PublishEpisodeActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int PICK_FROM_GALLERY = 1;
    String title;
    DBhelper mDbHelper = new DBhelper(this);
    SQLiteDatabase db;
    ImageView a;
    List<Item> image=new ArrayList<>();
    int cid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mIntent = getIntent();
        cid= mIntent.getIntExtra("cid", 0);
        setContentView(R.layout.activity_publish_episode);
        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        a=findViewById(R.id.imgView);
        buttonLoadImage.setOnClickListener(new View.OnClickListener()
        {
                public void onClick(View v){
                    try {
                        if (ActivityCompat.checkSelfPermission(PublishEpisodeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(PublishEpisodeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                        } else {
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults){
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Item i=new Item("",BitmapFactory.decodeFile(picturePath));
            image.add(i);
            ListView gv = (ListView) findViewById(R.id.imglist);
            GridViewAdapter adapter=new GridViewAdapter(this,R.layout.grid_item,image);
            gv.setAdapter(adapter);
        }
    }
    public void NextOnClick(View view){
        EditText dt=findViewById(R.id.title);
        title=dt.getText().toString();
        if(image!=null&&title!=""){
            db = mDbHelper.getReadableDatabase();
            ContentValues insertValues = new ContentValues();
            insertValues.put("ETitle", title);
            insertValues.put("CId",cid);
            db.insert("Episodes", null, insertValues);
            Cursor cursor=db.rawQuery("select EId FROM Episodes where ETitle='"+title+"'", null);
            int eid=0;
            if (cursor.moveToFirst()) {
                eid= Integer.parseInt(cursor.getString(cursor.getColumnIndex("EId")));
            }
            cursor.close();
            for(int i=0;i<image.size();i++){
                Bitmap bitmap= image.get(i).getmImageDrawable();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress( Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bitmapData = stream.toByteArray();
                insertValues = new ContentValues();
                insertValues.put("EId", eid);
                insertValues.put("CId",cid);
                insertValues.put("Slide",bitmapData);
                db.insert("Slides", null, insertValues);
            }
            mDbHelper.change=true;
            Intent intent =  new Intent(this,PublishEpisodeActivity.class);
            intent.putExtra("cid",cid);
            startActivity(intent);
        }
    }
    public void DoneOnClick(View view){
        Intent intent =  new Intent(this,HomeActivity.class);
        startActivity(intent);
    }

}