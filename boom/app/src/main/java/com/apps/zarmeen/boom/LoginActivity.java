package com.apps.zarmeen.boom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.internal.zzs.TAG;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    DBhelper mDbHelper = new DBhelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        // set the view now
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));//chnge here reset
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    FirebaseDatabase database;
                                    DatabaseReference databaseRef;
                                    database = FirebaseDatabase.getInstance();
                                    databaseRef = database.getReference("comics");
                                    databaseRef.child("comics").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            List<comics> c = new ArrayList<comics>();
                                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                                comics com = noteDataSnapshot.getValue(comics.class);
                                                c.add(com);
                                            }
                                            for (int i = 0; i < c.size(); i++) {
                                                comics c1 = c.get(i);
                                                mDbHelper.addComicSer(c1.getTitle(), c1.getGenre(), c1.getFeature(), c1.getImage());
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // Failed to read value
                                            Log.w(TAG, "Failed to read value comics.");
                                        }
                                    });
                                    databaseRef = database.getReference("episodes");
                                    databaseRef.child("episodes").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            List<episodes> c = new ArrayList<>();
                                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                                episodes com = noteDataSnapshot.getValue(episodes.class);
                                                c.add(com);
                                            }
                                            for (int i = 0; i < c.size(); i++) {
                                                episodes c1 = c.get(i);
                                                mDbHelper.addEpisodeSer(c1.getComicId(), c1.getEpisodeTitle());
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // Failed to read value
                                            Log.w(TAG, "Failed to read value episodes.");
                                        }
                                    });
                                    databaseRef = database.getReference("slides");
                                    databaseRef.child("slides").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            List<slides> c = new ArrayList<>();
                                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                                slides com = noteDataSnapshot.getValue(slides.class);
                                                c.add(com);
                                            }
                                            for (int i = 0; i < c.size(); i++) {
                                                slides c1 = c.get(i);
                                                mDbHelper.addSlideSer(c1.getEpisodeId(), c1.getComicId(), c1.getSlide());
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // Failed to read value
                                            Log.w(TAG, "Failed to read value slides.");
                                        }
                                    });
                                    databaseRef = database.getReference("bookmarks");
                                    databaseRef.child("bookmarks").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            List<bookmark> c = new ArrayList<>();
                                            for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                                bookmark com = noteDataSnapshot.getValue(bookmark.class);
                                                c.add(com);
                                            }
                                            for (int i = 0; i < c.size(); i++) {
                                                bookmark c1 = c.get(i);
                                                mDbHelper.addBookmarkSer(c1.getUserId(), c1.getComicId(), c1.getEpisodeId());
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // Failed to read value
                                            Log.w(TAG, "Failed to read value bookmark.");
                                        }
                                    });
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}

