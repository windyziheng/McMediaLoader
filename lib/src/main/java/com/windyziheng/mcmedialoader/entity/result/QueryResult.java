package com.windyziheng.mcmedialoader.entity.result;

import androidx.annotation.NonNull;

import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import java.util.List;

/**
 * 检索本地多媒体文件后的输出结果
 *
 * @Author WangZiheng
 * @CreateDate 2020/12/13
 * @Organization Convergence Ltd.
 */
public class QueryResult<T extends MediaEntity> {

    protected boolean isSuccess;
    protected List<T> originMediaList;
    protected List<T> sortedMediaList;

    protected QueryResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    protected QueryResult(boolean isSuccess, List<T> originMediaList) {
        this.isSuccess = isSuccess;
        this.originMediaList = originMediaList;
    }

    public static <E extends MediaEntity> QueryResult<E> createQuerySuccessResult(@NonNull List<E> originMediaList) {
        return new QueryResult<>(true, originMediaList);
    }

    public static <E extends MediaEntity> QueryResult<E> createQueryFailResult() {
        return new QueryResult<>(false);
    }

    public void setSortedMediaList(@NonNull List<T> sortedMediaList) {
        if (isSuccess && originMediaList.size() == sortedMediaList.size()) {
            this.sortedMediaList = sortedMediaList;
        }
    }

    public List<T> getMediaList() {
        return isSorted() ? getSortedMediaList() : getOriginMediaList();
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    private List<T> getOriginMediaList() {
        return isSuccess ? originMediaList : null;
    }

    private List<T> getSortedMediaList() {
        return isSuccess ? sortedMediaList : null;
    }

    private boolean isSorted() {
        return sortedMediaList != null;
    }
}
