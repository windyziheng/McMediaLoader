package com.windyziheng.mcmedialoader.query;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import java.util.List;

/**
 * 本地存储的多媒体文件查询AsyncTask
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-10
 * @Organization Convergence Ltd.
 */
public class QueryTask<T extends MediaEntity> extends AsyncTask<Void, Void, List<T>> {

    private QueryFactory<T> queryFactory;
    private QueryFactory.OnQueryListener<T> listener;

    public QueryTask(@NonNull QueryFactory<T> queryFactory, @NonNull QueryFactory.OnQueryListener<T> listener) {
        this.queryFactory = queryFactory;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        if (queryFactory == null || !queryFactory.setup()) {
            cancel(true);
            return;
        }
        listener.onQueryStart();
    }

    @Override
    protected List<T> doInBackground(Void... voids) {
        return queryFactory.query();
    }

    @Override
    protected void onPostExecute(List<T> mediaList) {
        queryFactory.release();
        if (mediaList != null) {
            listener.onQuerySuccess(mediaList);
        } else {
            listener.onQueryFail();
        }
    }

    @Override
    protected void onCancelled() {
        queryFactory.release();
        listener.onQueryFail();
    }
}
