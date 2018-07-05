package com.sava.friend_manager.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sava.friend_manager.AddActivity;
import com.sava.friend_manager.DetailActivity;
import com.sava.friend_manager.R;
import com.sava.friend_manager.model.Friend;
import com.sava.friend_manager.util.SFont;

import java.sql.Types;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirendAdapter extends RecyclerView.Adapter<FirendAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Friend> mList;
    public FirendAdapter(Context context,ArrayList<Friend> list){
        this.mContext = context;
        this.mList = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_friend,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Friend friend = mList.get(position);
        try{
            Uri uri = Uri.parse(friend.getAvata());
            holder.imgAvata.setImageURI(uri);
        }catch (Exception e){
            holder.imgAvata.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ms));
        }

        holder.tvName.setText(friend.getName());
        holder.tvPhone.setText(friend.getPhone());
        holder.layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("ID",friend.getId());
                mContext.startActivity(intent);
            }
        });
        holder.imgAvata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + friend.getPhone()));
                intent.putExtra("sms_body", "HALO");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imgAvata;
        TextView tvName;
        TextView tvPhone;
        LinearLayout layoutContent;
        public ViewHolder(View itemView) {
            super(itemView);
            imgAvata = itemView.findViewById(R.id.img_avata);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            layoutContent = itemView.findViewById(R.id.layout_content);
            tvName.setTypeface(SFont.setFont(mContext,SFont.FONT1), Typeface.BOLD);
            tvPhone.setTypeface(SFont.setFont(mContext,SFont.FONT2),Typeface.BOLD);
        }
    }
}
