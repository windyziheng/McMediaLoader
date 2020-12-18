package com.windyziheng.mcmedialoader.sort.group;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.windyziheng.mcmedialoader.entity.group.GroupEntity;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.sort.SortFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 分组对象排序AsyncTask
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-11
 * @Organization Convergence Ltd.
 */
public class GroupSortTask<T extends MediaEntity> extends AsyncTask<GroupEntity<T>, Void, List<GroupEntity<T>>> {

    private GroupSortFactory<T> sortFactory;
    private SortFactory.OnSortListener<GroupEntity<T>> listener;

    public GroupSortTask(@NonNull GroupSortFactory<T> sortFactory, @NonNull SortFactory.OnSortListener<GroupEntity<T>> listener) {
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
    protected List<GroupEntity<T>> doInBackground(GroupEntity<T>... groups) {
        List<GroupEntity<T>> originList = new ArrayList<>(Arrays.asList(groups));
        return sortFactory.sort(originList);
    }

    @Override
    protected void onPostExecute(List<GroupEntity<T>> groupList) {
        sortFactory.release();
        listener.onSortDone(true, groupList);
    }

    @Override
    protected void onCancelled() {
        sortFactory.release();
        listener.onSortDone(false, null);
    }
}
