package com.windyziheng.mcmedialoader.sort.media;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.sort.SortFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 多媒体列表排序AsyncTask
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-10
 * @Organization Convergence Ltd.
 */
public class MediaSortTask<T extends MediaEntity> extends AsyncTask<T, Void, List<T>> {

    private MediaSortFactory<T> sortFactory;
    private SortFactory.OnSortListener<T> listener;

    public MediaSortTask(@NonNull MediaSortFactory<T> sortFactory, @NonNull SortFactory.OnSortListener<T> listener) {
        this.sortFactory = sortFactory;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        if (sortFactory == null || !sortFactory.setup()) {
            cancel(true);
        }
    }

    @Override
    protected List<T> doInBackground(T... medias) {
        List<T> mediaList = new ArrayList<>(Arrays.asList(medias));
        return sortFactory.sort(mediaList);
    }

    @Override
    protected void onPostExecute(List<T> mediaList) {
        sortFactory.release();
        listener.onSortDone(true, mediaList);
    }

    @Override
    protected void onCancelled() {
        sortFactory.release();
        listener.onSortDone(false, null);
    }
}
