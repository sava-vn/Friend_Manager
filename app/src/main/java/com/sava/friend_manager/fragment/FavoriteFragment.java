package com.sava.friend_manager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sava.friend_manager.MainActivity;
import com.sava.friend_manager.R;
import com.sava.friend_manager.adapter.FirendAdapter;
import com.sava.friend_manager.model.Friend;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FirendAdapter adapter;
    private ArrayList<Friend> list;

    public  FavoriteFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        initWidget(view);
        return view;
    }
    public void initWidget(View view){
        mRecyclerView = view.findViewById(R.id.rv_friend);
        mRecyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        list.addAll(MainActivity.myDB.getAllFriendByType(0));
        adapter = new FirendAdapter(getActivity(),list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        list.clear();
        list.addAll(MainActivity.myDB.getAllFriendByType(0));
        adapter.notifyDataSetChanged();
        super.onResume();
    }

}
