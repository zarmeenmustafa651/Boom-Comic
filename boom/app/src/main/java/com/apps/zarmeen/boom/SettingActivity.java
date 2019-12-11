package com.apps.zarmeen.boom;

import android.app.ListActivity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.WrapperListAdapter;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatCallback callback = new AppCompatCallback() {
            @Override
            public void onSupportActionModeStarted(ActionMode actionMode) {
            }

            @Override
            public void onSupportActionModeFinished(ActionMode actionMode) {
            }

            @Nullable
            @Override
            public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
                return null;
            }
        };
        AppCompatDelegate delegate = AppCompatDelegate.create(this, callback);
        delegate.onCreate(savedInstanceState);
        delegate.setContentView(R.layout.activity_setting);
        Toolbar toolbar= (Toolbar) findViewById(R.id.tToolbar);
        delegate.setSupportActionBar(toolbar);
        delegate.getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.settings_icon);
        String[] values = new String[] { "My Account", "Publish", "Change Password","Sign Out"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
        if(item.equals("My Account")){
            Intent intent = new Intent(this, MyAccountActivity.class);
            startActivity(intent);
        }
        else if(item.equals("Publish")){
            Intent intent = new Intent(this, PublishActivity.class);
            startActivity(intent);
        }
        else if(item.equals("Change Password")){
            Intent intent = new Intent(this, ResetPasswordActivity.class);
            startActivity(intent);
        }
        else if(item.equals("Sign Out")){
            FirebaseAuth auth= FirebaseAuth.getInstance();
            auth.signOut();
            Intent intent = new Intent(this, ActivitySplash.class);
            startActivity(intent);
        }
    }
}
