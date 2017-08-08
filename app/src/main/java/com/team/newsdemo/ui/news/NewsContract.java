package com.team.newsdemo.ui.news;

import android.content.Context;
import android.content.Intent;

import com.team.newsdemo.base.BasePresenter;
import com.team.newsdemo.base.BaseView;
import com.team.newsdemo.model.NewsResponse;

import java.util.List;

/**
 * Created by Android_ZzT on 17/8/8.
 */

public class NewsContract {

    interface View extends BaseView {

        void onNewsListUpdated(List<NewsResponse.New> news);

        void onShowNetError();

        void onRefreshStateChanged(boolean isRefresh);
    }

    interface Presenter extends BasePresenter {

        void jumpToTarget(Context who, Intent intent);
    }
}
