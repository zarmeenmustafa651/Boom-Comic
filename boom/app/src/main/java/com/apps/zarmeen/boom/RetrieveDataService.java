package com.apps.zarmeen.boom;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RetrieveDataService extends IntentService {

    private FirebaseDatabase database;
    private DatabaseReference databaseRef;
    SQLiteDatabase db;
    private FirebaseAuth mAuth;
    DBhelper mDbHelper = new DBhelper(this);
    Context context;
    public RetrieveDataService() {
        super("RetrieveDataService");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context = getApplicationContext();
        mDbHelper = new DBhelper(context);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
////              int i=db.getVersion();
////              mDbHelper.onUpgrade(db,i,2);
//            database = FirebaseDatabase.getInstance();
//            databaseRef = database.getReference("comics");
//            databaseRef.child("comics").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    List<comics> c = new ArrayList<comics>();
//                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
//                        comics com = noteDataSnapshot.getValue(comics.class);
//                        c.add(com);
//                    }
//                    for (int i = 0; i < c.size(); i++) {
//                        comics c1 = c.get(i);
//                        mDbHelper.addComicSer(c1.getTitle(), c1.getGenre(), c1.getFeature(), c1.getImage());
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    // Failed to read value
//                    Log.w(TAG, "Failed to read value comics.");
//                }
//            });
//            databaseRef = database.getReference("episodes");
//            databaseRef.child("episodes").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    List<episodes> c = new ArrayList<>();
//                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
//                        episodes com = noteDataSnapshot.getValue(episodes.class);
//                        c.add(com);
//                    }
//                    for (int i = 0; i < c.size(); i++) {
//                        episodes c1 = c.get(i);
//                        mDbHelper.addEpisodeSer(c1.getComicId(), c1.getEpisodeTitle());
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    // Failed to read value
//                    Log.w(TAG, "Failed to read value episodes.");
//                }
//            });
//            databaseRef = database.getReference("slides");
//            databaseRef.child("slides").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    List<slides> c = new ArrayList<>();
//                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
//                        slides com = noteDataSnapshot.getValue(slides.class);
//                        c.add(com);
//                    }
//                    for (int i = 0; i < c.size(); i++) {
//                        slides c1 = c.get(i);
//                        mDbHelper.addSlideSer(c1.getEpisodeId(), c1.getComicId(), c1.getSlide());
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    // Failed to read value
//                    Log.w(TAG, "Failed to read value slides.");
//                }
//            });
//            databaseRef = database.getReference("bookmarks");
//            databaseRef.child("bookmarks").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    List<bookmark> c = new ArrayList<>();
//                    for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
//                        bookmark com = noteDataSnapshot.getValue(bookmark.class);
//                        c.add(com);
//                    }
//                    for (int i = 0; i < c.size(); i++) {
//                        bookmark c1 = c.get(i);
//                        mDbHelper.addBookmarkSer(c1.getUserId(), c1.getComicId(), c1.getEpisodeId());
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    // Failed to read value
//                    Log.w(TAG, "Failed to read value bookmark.");
//                }
//            });
            if (mDbHelper.change) {
                Writedata();
                mDbHelper.change=false;
            }
        }

    }
    private void Writedata() {
        database = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        removeDataFromDatabase();
        InsertallComics();
        InsertallEpisodes();
        InsertallSlides();
        InsertallBookmarks();
    }
    private void InsertallComics() {
        databaseRef = database.getReference("comics");
        String comicsId = null;
        ArrayList<comics> com = mDbHelper.getAllComics();
        for (int i = 0; i < com.size(); i++) {
            if (TextUtils.isEmpty(comicsId)) {
                comicsId = databaseRef.push().getKey();
            }
            comics c = com.get(i);
            databaseRef.child(comicsId).setValue(c);
        }
    }
    private void InsertallEpisodes() {

        databaseRef = database.getReference("episodes");
        String episodesId = null;
        ArrayList<episodes> com = mDbHelper.getAllEpisodes();
        for (int i = 0; i < com.size(); i++) {
            if (TextUtils.isEmpty(episodesId)) {
                episodesId = databaseRef.push().getKey();
            }
            episodes c = com.get(i);
            databaseRef.child(episodesId).setValue(c);
        }
    }
    private void InsertallSlides() {
        String Id = null;
        databaseRef = database.getReference("slides");

        ArrayList<slides> com = mDbHelper.getAllSlides();
        for (int i = 0; i < com.size(); i++) {
            if (TextUtils.isEmpty(Id)) {
                Id = databaseRef.push().getKey();
            }
            slides c = com.get(i);
            databaseRef.child(Id).setValue(c);
        }
    }
    private void InsertallBookmarks() {
        String Id = null;
        databaseRef = database.getReference("bookmarks");

        ArrayList<bookmark> com = mDbHelper.getAllBookmarks();
        for (int i = 0; i < com.size(); i++) {
            if (TextUtils.isEmpty(Id)) {
                Id = databaseRef.push().getKey();
            }
            bookmark c = com.get(i);
            databaseRef.child(Id).setValue(c);
        }
    }
    void removeDataFromDatabase() {
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        root.setValue(null);
    }
}