package com.sava.friend_manager;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sava.friend_manager.model.Friend;
import com.sava.friend_manager.util.SFont;

import java.lang.reflect.Type;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private CircleImageView imgAvata;
    private ImageButton btnLove;
    private ImageButton btnBlack;
    private ImageButton btnCall;
    private ImageButton btnSms;
    private ImageButton btnEmail;
    private TextView tvName;
    private TextView tvPhoneLB;
    private TextView tvPhone;
    private TextView tvEmailLB;
    private TextView tvEmail;
    private TextView tvBDLB;
    private TextView tvDB;
    private TextView tvAddLB;
    private TextView tvAdd;
    private Friend friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initWidget();
        setFontandStyle();
        initData();
        setAction();
    }

    public void initWidget() {
        mToolbar = findViewById(R.id.tb_detail);
        imgAvata = findViewById(R.id.img_detail_avata);
        btnLove = findViewById(R.id.btn_detail_love);
        btnBlack = findViewById(R.id.btn_detail_black);
        btnCall = findViewById(R.id.btn_detail_call);
        btnSms = findViewById(R.id.btn_detail_sms);
        btnEmail = findViewById(R.id.btn_detail_email);
        tvName = findViewById(R.id.tv_detail_name);
        tvPhoneLB = findViewById(R.id.tv_detail_phone_lb);
        tvPhone = findViewById(R.id.tv_detail_phone);
        tvEmail = findViewById(R.id.tv_detail_email);
        tvEmailLB = findViewById(R.id.tv_detail_email_lable);
        tvBDLB = findViewById(R.id.tv_detail_bd_lable);
        tvDB = findViewById(R.id.tv_detail_db);
        tvAdd = findViewById(R.id.tv_detail_address);
        tvAddLB = findViewById(R.id.tv_detail_address_lable);

    }

    public void setFontandStyle() {
        setSupportActionBar(mToolbar);
        tvName.setTypeface(SFont.setFont(this, SFont.FONT1), Typeface.BOLD);
        tvPhoneLB.setTypeface(SFont.setFont(this, SFont.FONT2), Typeface.BOLD);
        tvEmailLB.setTypeface(SFont.setFont(this, SFont.FONT2), Typeface.BOLD);
        tvAddLB.setTypeface(SFont.setFont(this, SFont.FONT2), Typeface.BOLD);
        tvBDLB.setTypeface(SFont.setFont(this, SFont.FONT2), Typeface.BOLD);
        tvDB.setTypeface(SFont.setFont(this, SFont.FONT1), Typeface.BOLD);
        tvPhone.setTypeface(SFont.setFont(this, SFont.FONT1), Typeface.BOLD);
        tvEmail.setTypeface(SFont.setFont(this, SFont.FONT1), Typeface.BOLD);
        tvAdd.setTypeface(SFont.setFont(this, SFont.FONT1), Typeface.BOLD);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(SFont.setFontForActionBar(this, SFont.FONT1, Color.WHITE, "DETAIL"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setAction() {
        btnLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (friend.getType() != 0) {
                    btnBlack.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_black_3));
                    btnLove.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_love_2));
                    friend.setType(0);
                } else {
                    btnLove.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_love_3));
                    friend.setType(1);
                }
            }
        });
        btnBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (friend.getType() != 2) {
                    btnBlack.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_black_2));
                    btnLove.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_love_3));
                    friend.setType(2);
                } else {
                    btnBlack.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_black_3));
                    friend.setType(1);
                }
            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + friend.getPhone()));
                if (ActivityCompat.checkSelfPermission(DetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    DetailActivity.this.requestPermissions(new String[]{Manifest.permission.CALL_PHONE},3);
                }
                DetailActivity.this.startActivity(intent);
            }
        });
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + friend.getPhone()));
                intent.putExtra("sms_body", "HALO");
                startActivity(intent);
            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailto = friend.getEmail() +
                        "?cc=" + "" +
                        "&subject=" + Uri.encode("") +
                        "&body=" + Uri.encode("");
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));
                try {
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException e) {
                }
            }
        });
    }

    public void initData() {
        Intent intent = this.getIntent();
        friend = MainActivity.myDB.getFriendById(intent.getExtras().getInt("ID"));
        try {
            imgAvata.setImageURI(Uri.parse(friend.getAvata()));
        } catch (Exception e) {
            imgAvata.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ms));
        }
        tvName.setText(friend.getName());
        tvPhone.setText(friend.getPhone());
        tvEmail.setText(friend.getEmail());
        tvDB.setText(friend.getBrithDay());
        tvAdd.setText(friend.getAddress());
        switch (friend.getType()) {
            case 0:
                btnBlack.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_black_3));
                break;
            case 1:
                btnBlack.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_black_3));
                btnLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_love_3));
                break;
            case 2:
                btnLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_love_3));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:
                Intent intent = new Intent(DetailActivity.this, AddActivity.class);
                intent.putExtra("ID", friend.getId());
                startActivityForResult(intent, 11);
                break;
            case R.id.menu_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(friend.getName());
                builder.setMessage("Bạn có muốn xóa không?");
                builder.setCancelable(false);
                builder.setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.myDB.deleteFriend(friend.getId());
                        DetailActivity.this.finish();
                    }
                });
                builder.setNegativeButton("HỦY BỎ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            default:
                MainActivity.myDB.editFriend(friend);
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 9) {
            initData();
        }
    }
}
