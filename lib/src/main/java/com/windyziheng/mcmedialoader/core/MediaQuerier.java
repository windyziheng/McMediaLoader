package com.windyziheng.mcmedialoader.core;

import android.content.Context;

import androidx.annotation.NonNull;

import com.windyziheng.mcmedialoader.constant.QueryType;
import com.windyziheng.mcmedialoader.core.method.QueryMethod;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.entity.result.QueryResult;
import com.windyziheng.mcmedialoader.query.DefaultQueryFactory;
import com.windyziheng.mcmedialoader.query.QueryFactory;
import com.windyziheng.mcmedialoader.sort.media.rule.SortMediaRule;

/**
 * 多媒体本地检索器
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-09
 * @Organization Convergence Ltd.
 */
public class MediaQuerier {

    private MediaQuerier() {

    }

    /**
     * 创建多媒体检索器
     *
     * @param queryFactory 查询工厂
     * @param <T>          查询结果泛型
     * @return 多媒体检索器
     */
    public static <T extends MediaEntity> Querier<T> createQuerier(QueryFactory<T> queryFactory) {
        return new Querier<>(queryFactory);
    }

    /**
     * 创建默认多媒体检索器
     *
     * @param context 上下文
     * @return 默认多媒体检索器
     */
    public static Querier<MediaEntity> createDefaultQuerier(Context context) {
        return createQuerier(createDefaultFactory(context, QueryType.All));
    }

    /**
     * 创建默认多媒体检索器
     *
     * @param context   上下文
     * @param queryType 查询类型
     * @return 默认多媒体检索器
     */
    public static Querier<MediaEntity> createDefaultQuerier(Context context, QueryType queryType) {
        return createQuerier(createDefaultFactory(context, queryType));
    }

    /**
     * 创建默认检索工厂类
     *
     * @param context   上下文
     * @param queryType 查询类型
     * @return 默认检索工厂类
     */
    public static DefaultQueryFactory createDefaultFactory(Context context, QueryType queryType) {
        return new DefaultQueryFactory.Creator(context, queryType).create();
    }

    /**
     * 多媒体检索操作执行类
     *
     * @param <E> 多媒体类
     */
    public static class Querier<E extends MediaEntity> {

        private QueryMethod<E> queryMethod;

        public Querier(@NonNull QueryFactory<E> queryFactory) {
            queryMethod = new QueryMethod<>(queryFactory);
        }

        /**
         * 设置检索完成后，对多媒体列表按照指定规则进行排序的操作是否执行
         *
         * @param isAction 是否执行
         * @return 当前对象
         */
        public Querier<E> setQuerySortMediaIsAction(boolean isAction) {
            queryMethod.setQuerySortMediaIsAction(isAction);
            return this;
        }

        /**
         * 设置检索完成后，对多媒体列表按照指定规则进行排序的排序规则
         *
         * @param rule 排序规则
         * @return 当前对象
         */
        public Querier<E> setQuerySortMediaRule(SortMediaRule<E> rule) {
            queryMethod.setQuerySortMediaRule(rule);
            return this;
        }

        /**
         * 执行本地多媒体检索操作
         *
         * @param callback 检索监听
         */
        public void query(@NonNull QueryCallback<E> callback) {
            queryMethod.query(callback);
        }

        /**
         * 给检索结果排序（供外部直接调用）
         *
         * @param queryResult   检索结果
         * @param sortMediaRule 排序规则
         */
        public void sort(@NonNull QueryResult<E> queryResult, @NonNull SortMediaRule<E> sortMediaRule) {
            queryMethod.sort(queryResult, sortMediaRule);
        }
    }

    /**
     * 检索回调
     *
     * @param <E> 多媒体类型
     */
    public interface QueryCallback<E extends MediaEntity> {

        /**
         * 检索开始
         */
        void onQueryStart();

        /**
         * 检索完成
         *
         * @param queryResult 检索结果
         */
        void onQueryDone(QueryResult<E> queryResult);
    }
}
