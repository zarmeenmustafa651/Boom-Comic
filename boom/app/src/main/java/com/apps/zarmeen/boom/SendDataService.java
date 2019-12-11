package com.apps.zarmeen.boom;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//import static android.view.View.X;
//import static java.nio.file.attribute.AclEntryPermission.DELETE;
//
///**
// * An {@link IntentService} subclass for handling asynchronous task requests in
// * a service on a separate handler thread.
// * <p>
// * TODO: Customize class - update intent actions, extra parameters and static
// * helper methods.
// */
public class SendDataService extends IntentService {

    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;
    DBhelper mDbHelper=new DBhelper(this);
    public SendDataService()
    {
        super("SendDataService");
    }
    Context context;

    // TODO: Customize helper method

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
           Writedata();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = getApplicationContext();
         mDbHelper= new DBhelper(context);
        return super.onStartCommand(intent, flags, startId);
    }
    private void Writedata()
    {
        database = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        removeDataFromDatabase();
        InsertallComics();
        InsertallEpisodes();
        InsertallSlides();
        InsertallBookmarks();
    }
    /**
     * Creating new user node under 'users'
     */
    private void InsertallComics()
    {
       databaseRef = database.getReference("comics");
        String comicsId = null;
       ArrayList<comics> com= mDbHelper.getAllComics();
        for(int i=0;i<com.size();i++)
        {
            if (TextUtils.isEmpty(comicsId)) {
                comicsId = databaseRef.push().getKey();
            }
            comics c = com.get(i);
            databaseRef.child(comicsId).setValue(c);
        }
    }

    private void InsertallEpisodes()
    {

        databaseRef = database.getReference("episodes");
        String episodesId =null;
        ArrayList<episodes> com= mDbHelper.getAllEpisodes();
        for(int i=0;i<com.size();i++)
        {
            if (TextUtils.isEmpty(episodesId)) {
                episodesId = databaseRef.push().getKey();
            }
            episodes c = com.get(i);
            databaseRef.child(episodesId).setValue(c);
        }
    }

    private void InsertallSlides()
    {
        String Id=null;
        databaseRef = database.getReference("slides");

        ArrayList<slides> com= mDbHelper.getAllSlides();
        for(int i=0;i<com.size();i++)
        {
            if (TextUtils.isEmpty(Id)) {
                Id = databaseRef.push().getKey();
            }
            slides c = com.get(i);
            databaseRef.child(Id).setValue(c);
        }
    }

    private void InsertallBookmarks()
    {
        String Id=null;
        databaseRef = database.getReference("bookmarks");

        ArrayList<bookmark> com= mDbHelper.getAllBookmarks();
        for(int i=0;i<com.size();i++)
        {
            if (TextUtils.isEmpty(Id)) {
                Id = databaseRef.push().getKey();
            }
            bookmark c = com.get(i);
            databaseRef.child(Id).setValue(c);
        }
    }

    void removeDataFromDatabase(){
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        root.setValue(null);
    }

}

