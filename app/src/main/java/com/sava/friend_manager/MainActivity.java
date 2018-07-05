package com.sava.friend_manager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sava.friend_manager.adapter.TabLayoutAdapter;
import com.sava.friend_manager.util.MyDB;
import com.sava.friend_manager.util.SFont;

public class MainActivity extends AppCompatActivity {
    public static MyDB myDB;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton fab;
    private TabLayoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        initPermisson();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initWidget() {
        myDB = new MyDB(this);
        mToolbar = findViewById(R.id.tb_main);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.vp_main);
        fab = findViewById(R.id.fab);
        mToolbar.setTitle("Friend List");
        setSupportActionBar(mToolbar);
        adapter = new TabLayoutAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(1);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_favorite);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_normal);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_black);
        TextView tv2 = SFont.setFontForActionBar(this, SFont.FONT1, Color.WHITE, "FRIEND LIST");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv2);
    }

    public void initPermisson() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                    (this.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) ||
                    (this.checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)) {

                this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS}, 1);
            }
        }

    }
}
