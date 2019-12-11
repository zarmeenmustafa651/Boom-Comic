package com.apps.zarmeen.boom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MyAccountActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
       String Email= auth.getCurrentUser().getEmail();
        TextView v=findViewById(R.id.email);
        v.setText(Email);
    }
}
