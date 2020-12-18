package com.windyziheng.mcmedialoader.core.method;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.windyziheng.mcmedialoader.core.MediaQuerier;
import com.windyziheng.mcmedialoader.core.MediaSorter;
import com.windyziheng.mcmedialoader.entity.config.ActionConfig;
import com.windyziheng.mcmedialoader.entity.config.QuerySortMediaConfig;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.entity.result.QueryResult;
import com.windyziheng.mcmedialoader.query.QueryFactory;
import com.windyziheng.mcmedialoader.query.QueryTask;
import com.windyziheng.mcmedialoader.sort.media.rule.SortMediaRule;

import java.util.List;

/**
 * 检索操作封装类
 *
 * @Author WangZiheng
 * @CreateDate 2020/12/17
 * @Organization Convergence Ltd.
 */
public class QueryMethod<T extends MediaEntity> {

    private QueryFactory<T> queryFactory;
    private QuerySortMediaConfig<T> querySortMediaConfig = ActionConfig.createDefaultQuerySortMediaConfig();

    public QueryMethod(QueryFactory<T> queryFactory) {
        this.queryFactory = queryFactory;
    }

    /**
     * 设置检索完成后，对多媒体列表按照指定规则进行排序的操作是否执行
     *
     * @param isAction 是否执行
     */
    public void setQuerySortMediaIsAction(boolean isAction) {
        querySortMediaConfig.setAction(isAction);
    }

    /**
     * 设置检索完成后，对多媒体列表按照指定规则进行排序的排序规则
     *
     * @param rule 排序规则
     */
    public void setQuerySortMediaRule(SortMediaRule<T> rule) {
        querySortMediaConfig.setSortMediaRule(rule);
    }

    /**
     * 执行本地多媒体检索操作
     *
     * @param callback 检索监听
     */
    public void query(@NonNull MediaQuerier.QueryCallback<T> callback) {
        QueryTask<T> queryTask = new QueryTask<>(queryFactory, new QueryFactory.OnQueryListener<T>() {
            @Override
            public void onQueryStart() {
                callback.onQueryStart();
            }

            @Override
            public void onQuerySuccess(List<T> mediaList) {
                QueryResult<T> queryResult = QueryResult.createQuerySuccessResult(mediaList);
                if (querySortMediaConfig.isAction()) {
                    sort(queryResult, querySortMediaConfig.getSortMediaRule(), callback);
                } else {
                    callback.onQueryDone(queryResult);
                }
            }

            @Override
            public void onQueryFail() {
                callback.onQueryDone(QueryResult.createQueryFailResult());
            }
        });
        queryTask.execute();
    }

    /**
     * 给检索结果排序（供外部直接调用）
     *
     * @param queryResult   检索结果
     * @param sortMediaRule 排序规则
     */
    public void sort(@NonNull QueryResult<T> queryResult, @NonNull SortMediaRule<T> sortMediaRule) {
        sort(queryResult, sortMediaRule, null);
    }

    /**
     * 给检索结果排序（当检索成功后排序时，queryCallback不为空）
     *
     * @param queryResult   检索结果
     * @param sortMediaRule 排序规则
     * @param queryCallback 检索回调
     */
    private void sort(@NonNull QueryResult<T> queryResult, @NonNull SortMediaRule<T> sortMediaRule,
                      @Nullable MediaQuerier.QueryCallback<T> queryCallback) {
        if (queryResult.getMediaList().isEmpty()) {
            if (queryCallback != null) {
                queryCallback.onQueryDone(queryResult);
            }
            return;
        }
        MediaSorter.createSorter(sortMediaRule).sort(queryResult.getMediaList(), (isSuccess, resultList) -> {
            if (isSuccess) {
                queryResult.setSortedMediaList(resultList);
            }
            if (queryCallback != null) {
                queryCallback.onQueryDone(queryResult);
            }
        });
    }
}
