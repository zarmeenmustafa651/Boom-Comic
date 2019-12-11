package com.apps.zarmeen.boom;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EpisodesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);
        Fragment fragment=new EpisodeFragment();
        Bundle cid=new Bundle();
        Intent i=getIntent();
        i.getIntExtra("CId",0);
        cid.putInt("CId",i.getIntExtra("CId",0));
        fragment.setArguments(cid);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.episodes_fragment, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            setContentView(R.layout.activity_episodes_land);
            Fragment fragment=new EpisodeFragment();
            Bundle cid=new Bundle();
            Intent i=getIntent();
            i.getIntExtra("CId",0);
            cid.putInt("CId",i.getIntExtra("CId",0));
            fragment.setArguments(cid);
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.episodes_fragment, fragment);
            fragmentTransaction.commit();




            fragment=new SlidesFragment();
            cid=new Bundle();
            cid.putInt("CId",i.getIntExtra("CId",0));
            cid.putInt("EId",0);
            fragment.setArguments(cid);
            fragmentManager = this.getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.slides_fragment, fragment);
            fragmentTransaction.commit();
        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            setContentView(R.layout.activity_episodes);
            Fragment fragment=new EpisodeFragment();
            Bundle cid=new Bundle();
            Intent i=getIntent();
            i.getIntExtra("CId",0);
            cid.putInt("CId",i.getIntExtra("CId",0));
            fragment.setArguments(cid);
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.episodes_fragment, fragment);
            fragmentTransaction.commit();
        }
    }
}