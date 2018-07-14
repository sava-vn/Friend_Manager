package com.sava.friend_manager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sava.friend_manager.model.Friend;
import com.sava.friend_manager.util.SFont;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageView imgSelect;
    private TextView tvImgSelect;
    private EditText edtName;
    private EditText edtBD;
    private EditText edtPhone;
    private EditText edtEmail;
    private EditText edtAddress;
    private String sUri;
    private Friend friend = null;
    private boolean isAdd = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initWidget();
        initData(getIntent());
        setFontandStyle();
        setAction();
    }
    public void initWidget(){
        mToolbar = findViewById(R.id.tb_add);
        imgSelect = findViewById(R.id.img_select);
        tvImgSelect = findViewById(R.id.tv_img_select);
        edtName = findViewById(R.id.edt_name);
        edtBD = findViewById(R.id.edt_bd);
        edtPhone = findViewById(R.id.edt_phone);
        edtEmail = findViewById(R.id.edt_email);
        edtAddress = findViewById(R.id.edt_addresss);
    }

    public void setFontandStyle(){
        tvImgSelect.setTypeface(SFont.setFont(this,SFont.FONT1),Typeface.BOLD);
        edtName.setTypeface(SFont.setFont(this,SFont.FONT1),Typeface.BOLD);
        edtPhone.setTypeface(SFont.setFont(this,SFont.FONT1),Typeface.BOLD);
        edtEmail.setTypeface(SFont.setFont(this,SFont.FONT1),Typeface.BOLD);
        edtAddress.setTypeface(SFont.setFont(this,SFont.FONT1),Typeface.BOLD);
        setSupportActionBar(mToolbar);
        String abc;
        if(isAdd)
            abc="ADD NEW FRIEND";
        else
            abc = "EDIT FIREND";
        TextView tv2 = SFont.setFontForActionBar(this,SFont.FONT1,Color.WHITE,abc);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv2);
    }

    public void setAction(){
        edtBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supportDatetiem();
            }
        });
        imgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropMenuCropButtonTitle("Done")
                        .setCropMenuCropButtonIcon(R.drawable.ic_add)
                        .start(AddActivity.this);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE&&resultCode ==RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Uri uri = result.getUri();
            sUri = uri.toString();
            imgSelect.setImageURI(uri);
        }
    }
    public void supportDatetiem(){
        final Calendar calendar = Calendar.getInstance();
        int mYear = 1995;
        int mMonth = 1;
        int mDay = 1;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String bd = ((i2<10)?"0":"") +i2  + " - " + ((++i1<10)?"0":"") +i1 + " - " + i;
                edtBD.setText(bd);
            }
        },mYear,mMonth,mDay);
        datePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_cancel)
            this.finish();
        else{
            if(isAdd)
                friend = new Friend();
            friend.setAvata(sUri);
            friend.setName(edtName.getText().toString());
            friend.setBrithDay(edtBD.getText().toString());
            friend.setPhone(edtPhone.getText().toString());
            friend.setEmail(edtEmail.getText().toString());
            friend.setAddress(edtAddress.getText().toString());
            if(isAdd)
                MainActivity.myDB.addFriend(friend);
            else{
                Intent intent = new Intent();
                intent.putExtra("ID",friend.getId());
                MainActivity.myDB.editFriend(friend);
                setResult(9,intent);
            }
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void initData(Intent intent){
        try{
            friend = MainActivity.myDB.getFriendById(intent.getExtras().getInt("ID"));
            try {
                imgSelect.setImageURI(Uri.parse(friend.getAvata()));
            }catch (Exception e){
                imgSelect.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ms));
            }
            sUri = friend.getAvata();
            tvImgSelect.setText("change image");
            edtName.setText(friend.getName());
            edtBD.setText(friend.getBrithDay());
            edtPhone.setText(friend.getPhone());
            edtEmail.setText(friend.getEmail());
            edtAddress.setText(friend.getAddress());
        }catch (Exception e){
                isAdd = true;
        }
    }
}
