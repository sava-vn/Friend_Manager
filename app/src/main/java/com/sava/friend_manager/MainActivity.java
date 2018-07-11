package com.sava.friend_manager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;
import com.sava.friend_manager.adapter.TabLayoutAdapter;
import com.sava.friend_manager.util.MyDB;
import com.sava.friend_manager.util.Suggestion;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private Toolbar mToolbar;
    public static MyDB myDB;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton fab;
    private TabLayoutAdapter adapter;
    private AppBarLayout mAppbar;
    private FloatingSearchView mSearchView;
    private List<Suggestion> mSuggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermisson();
        initWidget();
        initData();
        initView();
        initAction();
    }

    public void initWidget() {
        mToolbar = findViewById(R.id.tb_main);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.vp_main);
        fab = findViewById(R.id.fab);
        mAppbar = findViewById(R.id.appbar);
        mSearchView = findViewById(R.id.floating_search_view);
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

    public void initView() {
        setSupportActionBar(mToolbar);
        adapter = new TabLayoutAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(1);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_favorite);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_normal);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_black);
    }

    public void initAction() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        mAppbar.addOnOffsetChangedListener(this);
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {
                    mSearchView.showProgress();
                    mSearchView.swapSuggestions(getSuggestion(mSearchView.getQuery()));
                    mSearchView.hideProgress();
                }
            }
        });
        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
            }

            @Override
            public void onFocusCleared() {

            }
        });
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Suggestion suggestion = (Suggestion) searchSuggestion;
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("ID",suggestion.getmID());
                MainActivity.this.startActivity(intent);
                hideKeyboard(MainActivity.this);
                mSearchView.clearSearchFocus();
                mSearchView.clearQuery();
            }

            @Override
            public void onSearchAction(String currentQuery) {

            }
        });
        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {
                Suggestion suggestion = (Suggestion) item;
                String textColor = "#212121";
                String textLight = "#DC3545";
                if (suggestion.ismIsHistory()) {
                    leftIcon.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_searched));
                } else {
                    leftIcon.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_search));
                }
                textView.setTextColor(Color.parseColor(textColor));
                String text = suggestion.getBody()
                        .replaceFirst(mSearchView.getQuery(),
                                "<font color=\"" + textLight + "\">" + mSearchView.getQuery() + "</font>");
                textView.setText(Html.fromHtml(text));
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        mSearchView.setTranslationY(verticalOffset);
    }

    public void initData() {
        myDB = new MyDB(this);
        mSuggestions = myDB.getAllName();
    }

    private List<Suggestion> getSuggestion(String query) {
        List<Suggestion> list = new ArrayList<>();
        for (Suggestion suggestion : mSuggestions) {
            if (suggestion.getBody().toLowerCase().contains(query.toLowerCase()))
                list.add(suggestion);
        }
        return list;
    }

    private List<Suggestion> getHistorySuggestion() {
        List<Suggestion> list = new ArrayList<>();
        for (Suggestion suggestion :mSuggestions) {
            if(suggestion.ismIsHistory())
                list.add(suggestion);
            if(list.size()==4)
                break;
        }
        return list;
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
