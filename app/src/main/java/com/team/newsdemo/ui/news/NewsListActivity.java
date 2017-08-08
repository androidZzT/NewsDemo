package com.team.newsdemo.ui.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.team.newsdemo.R;
import com.team.newsdemo.ui.collection.CollectionListActivity;
import com.team.newsdemo.model.NewsResponse;
import com.team.newsdemo.ui.web.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android_ZzT on 17/8/8.
 */

public class NewsListActivity extends AppCompatActivity implements NewsContract.View , NewsAdapter.OnItemClickListener {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private NewsContract.Presenter mPresenter;

    private NewsAdapter mAdapter;

    private List<NewsResponse.New> mData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(mToolBar);
        mToolBar.setTitle(getString(R.string.app_name));
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadData();
            }
        });

        mPresenter = new NewsPresenter(this);
        mAdapter = new NewsAdapter(mData);
        mAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(NewsListActivity.this).resumeRequests();
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    Glide.with(NewsListActivity.this).pauseRequests();
                }
            }
        });
        mPresenter.loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_collect_list) {
            mPresenter.jumpToTarget(this , new Intent(this, CollectionListActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNewsListUpdated(List<NewsResponse.New> news) {
        mData.clear();
        mData.addAll(news);
        mAdapter.setData(mData);
    }

    @Override
    public void onShowNetError() {
        Toast.makeText(this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefreshStateChanged(boolean isRefresh) {
        mRefreshLayout.setRefreshing(isRefresh);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.releaseData();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("new", mData.get(position));
        mPresenter.jumpToTarget(this, intent);
    }
}
