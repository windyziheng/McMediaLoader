package com.windyziheng.mcmedialoader.group;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.entity.result.GroupResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 多媒体分组AsyncTask
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-11
 * @Organization Convergence Ltd.
 */
public class GroupTask<E extends MediaEntity> extends AsyncTask<E, Void, GroupResult<E>> {

    private GroupFactory<E> groupFactory;
    private GroupFactory.OnGroupListener<E> listener;

    public GroupTask(@NonNull GroupFactory<E> groupFactory, @NonNull GroupFactory.OnGroupListener<E> listener) {
        this.groupFactory = groupFactory;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        if (groupFactory == null || !groupFactory.setup()) {
            cancel(true);
            return;
        }
        listener.onGroupStart();
    }

    @Override
    protected GroupResult<E> doInBackground(E... medias) {
        List<E> mediaList = new ArrayList<>(Arrays.asList(medias));
        return groupFactory.group(mediaList);
    }

    @Override
    protected void onPostExecute(GroupResult<E> groupResult) {
        groupFactory.release();
        if (groupResult != null) {
            listener.onGroupSuccess(groupResult);
        } else {
            listener.onGroupFail();
        }
    }

    @Override
    protected void onCancelled() {
        groupFactory.release();
        listener.onGroupFail();
    }
}
