package com.windyziheng.mcmedialoader.entity.result;

import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

/**
 * 多媒体加载结果
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-17
 * @Organization Convergence Ltd.
 */
public class MediaLoaderResult<T extends MediaEntity> {

    protected QueryResult<T> queryResult;
    protected GroupResult<T> groupResult;

    public MediaLoaderResult() {

    }

    public QueryResult<T> getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(QueryResult<T> queryResult) {
        this.queryResult = queryResult;
    }

    public GroupResult<T> getGroupResult() {
        return groupResult;
    }

    public void setGroupResult(GroupResult<T> groupResult) {
        this.groupResult = groupResult;
    }

    public boolean isSuccess() {
        return isQuerySuccess() && isGroupResultDone();
    }

    public boolean isQuerySuccess() {
        return queryResult != null && queryResult.isSuccess();
    }

    public boolean isGroupResultDone() {
        return groupResult != null;
    }
}
