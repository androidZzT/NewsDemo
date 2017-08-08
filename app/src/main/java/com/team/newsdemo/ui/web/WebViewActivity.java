package com.team.newsdemo.ui.web;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.team.newsdemo.R;
import com.team.newsdemo.model.NewsResponse;
import com.team.newsdemo.model.database.NewsDAO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android_ZzT on 17/8/8.
 */

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.checkbox)
    CheckBox mCheckBox;

    private NewsResponse.New mNew;

    private NewsDAO mDAO;

    private boolean loadFinish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mDAO = new NewsDAO(this);
        mRefreshLayout.setRefreshing(true);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!loadFinish) {
                    mWebView.loadUrl(mNew.getPage_url());
                } else {
                    mRefreshLayout.setRefreshing(false);
                }
            }
        });

        Intent intent = getIntent();
        mNew = intent.getParcelableExtra("new");
        WebViewClient client = new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                loadFinish = true;
                mRefreshLayout.setRefreshing(false);
            }
        };
        mWebView.setWebViewClient(client);

        if (mNew != null) {
            mWebView.loadUrl(mNew.getPage_url());
        } else {
            Toast.makeText(this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
        }

        NewsResponse.New queryNew = mDAO.getNewById(mNew.getId());
        Log.d("zzt", "queryNew --->" + queryNew);
        mCheckBox.setChecked(queryNew != null);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDAO.insert(mNew);
                    Toast.makeText(WebViewActivity.this, getString(R.string.collect_success), Toast.LENGTH_SHORT).show();
                } else {
                    mDAO.delete(mNew.getId());
                    Toast.makeText(WebViewActivity.this, getString(R.string.cancel_collect), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
