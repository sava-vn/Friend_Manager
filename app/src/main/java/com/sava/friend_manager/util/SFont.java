package com.sava.friend_manager.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SFont {
    public static final  String FONT1 = "fonts/Geogrotesque-Light.ttf";
    public static final String FONT2 = "fonts/SVN-Aguda Regular.otf";
    public static Typeface setFont(Context context,String file){
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),file);
        return typeface;
    }
    public static TextView setFontForActionBar(Context context, String file, int color,String title){
        TextView tv = new TextView(context.getApplicationContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(lp);
        tv.setText(title);
        tv.setTextSize(20);
        tv.setTextColor(color);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), file);
        tv.setTypeface(tf,Typeface.BOLD);
        return  tv;
    }
}
