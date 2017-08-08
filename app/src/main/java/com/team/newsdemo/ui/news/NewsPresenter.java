package com.team.newsdemo.ui.news;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.team.newsdemo.model.NewsApi;
import com.team.newsdemo.model.NewsResponse;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android_ZzT on 17/8/8.
 */

public class NewsPresenter implements NewsContract.Presenter {

    private NewsContract.View mView;

    private NewsApi mNewsApi;

    private CompositeDisposable mCompositeDisposable;

    public NewsPresenter(NewsContract.View view) {
        mView = view;
        mNewsApi = new NewsModel().getNewsApi();
    }

    @Override
    public void loadData() {
        Observable<NewsResponse> observable = mNewsApi.getNews();

        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        registerRx(d);
                        mView.onRefreshStateChanged(true);
                    }

                    @Override
                    public void onNext(@NonNull NewsResponse newsResponse) {
                        Log.e("zzt", "data--->" + newsResponse.toString());
                        if (newsResponse.getCode() == 0 && TextUtils.equals("ok", newsResponse.getMessage())) {
                            mView.onNewsListUpdated(newsResponse.getNews());
                        } else {
                            mView.onShowNetError();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("zzt", e.getMessage().toString());
                        mView.onRefreshStateChanged(false);
                        mView.onShowNetError();
                    }

                    @Override
                    public void onComplete() {
                        mView.onRefreshStateChanged(false);
                    }
                });
    }

    @Override
    public void releaseData() {
        clearRx();
    }

    private void clearRx() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    private void registerRx(Disposable d) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(d);
    }

    @Override
    public void jumpToTarget(Context who, Intent intent) {
        who.startActivity(intent);
    }
}
