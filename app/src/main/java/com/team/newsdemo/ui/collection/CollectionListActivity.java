package com.team.newsdemo.ui.collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.team.newsdemo.R;
import com.team.newsdemo.model.NewsResponse;
import com.team.newsdemo.model.database.NewsDAO;
import com.team.newsdemo.ui.web.WebViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android_ZzT on 17/8/8.
 */

public class CollectionListActivity extends AppCompatActivity implements CollectionsAdapter.OnItemClickListener {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private NewsDAO mDAO;

    private List<NewsResponse.New> mCollections;

    private CollectionsAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.collect_list));
        mDAO = new NewsDAO(this);
        mAdapter = new CollectionsAdapter(mCollections);
        mAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("new", mCollections.get(position));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCollections = mDAO.getNewsList();
        if (mCollections == null) {
            Toast.makeText(this, getString(R.string.no_collections), Toast.LENGTH_SHORT).show();
        }
        mAdapter.setData(mCollections);
    }
}
