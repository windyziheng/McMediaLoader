package com.windyziheng.mcmedialoader.core;

import android.content.Context;

import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.entity.result.GroupResult;
import com.windyziheng.mcmedialoader.entity.result.MediaLoaderResult;
import com.windyziheng.mcmedialoader.entity.result.QueryResult;

/**
 * 本地多媒体加载器
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-10
 * @Organization Convergence Ltd.
 */
public class MediaLoader<T extends MediaEntity> {

    protected MediaQuerier.Querier<T> querier;
    protected MediaGrouper.Grouper<T> grouper;

    protected MediaLoaderResult<T> result;
    protected OnProcessListener<T> onProcessListener;

    protected boolean isProcessing = false;

    protected MediaLoader(Provider<T> provider) {
        querier = provider.provideQuerier();
        grouper = provider.providerGrouper();
    }

    /**
     * 创建默认本地多媒体加载器
     *
     * @param context 上下文
     * @return 默认本地多媒体加载器
     */
    public static MediaLoader<MediaEntity> createDefaultMediaLoader(Context context) {
        return new MediaLoader<>(new Provider<MediaEntity>() {
            @Override
            public MediaQuerier.Querier<MediaEntity> provideQuerier() {
                return MediaQuerier.createDefaultQuerier(context);
            }

            @Override
            public MediaGrouper.Grouper<MediaEntity> providerGrouper() {
                return MediaGrouper.createDefaultGrouper();
            }
        });
    }

    /**
     * 创建本地多媒体加载器
     *
     * @param provider 部件供应者
     * @param <E>      多媒体类
     * @return 本地多媒体加载器
     */
    public static <E extends MediaEntity> MediaLoader<E> create(Provider<E> provider) {
        return new MediaLoader<>(provider);
    }

    /**
     * 运行多媒体加载逻辑
     * 根据MediaQuerier查询本地多媒体列表，并根据MediaGrouper执行分组
     */
    public void runLogic() {
        if (querier == null) {
            return;
        }
        result = new MediaLoaderResult<>();
        querier.query(new MediaQuerier.QueryCallback<T>() {
            @Override
            public void onQueryStart() {
                startProcess();
            }

            @Override
            public void onQueryDone(QueryResult<T> queryResult) {
                result.setQueryResult(queryResult);
                if (queryResult.isSuccess() && grouper != null) {
                    group();
                } else {
                    stopProcess();
                }
            }
        });
    }

    /**
     * 执行分组操作
     */
    private void group() {
        if (!result.isQuerySuccess() || grouper == null) {
            stopProcess();
            return;
        }
        grouper.group(result.getQueryResult().getMediaList(), new MediaGrouper.GroupCallback<T>() {
            @Override
            public void onGroupStart() {

            }

            @Override
            public void onGroupSuccess(GroupResult<T> groupResult) {
                result.setGroupResult(groupResult);
                stopProcess();
            }

            @Override
            public void onGroupFail() {
                stopProcess();
            }
        });
    }

    /**
     * 释放资源
     */
    public void release() {
        isProcessing = false;
        result = null;
    }

    /**
     * 获取运行结果
     *
     * @return 运行结果
     */
    public MediaLoaderResult<T> getResult() {
        return result;
    }

    /**
     * 设置操作执行监听
     *
     * @param onProcessListener 监听
     * @return 当前对象
     */
    public MediaLoader<T> setOnProcessListener(OnProcessListener<T> onProcessListener) {
        this.onProcessListener = onProcessListener;
        return this;
    }

    /**
     * 开始操作
     */
    private void startProcess() {
        if (isProcessing) {
            return;
        }
        isProcessing = true;
        if (onProcessListener != null) {
            onProcessListener.onLoadMediaStart();
        }
    }

    /**
     * 结束操作
     */
    private void stopProcess() {
        if (!isProcessing) {
            return;
        }
        isProcessing = false;
        if (onProcessListener != null) {
            onProcessListener.onLoadMediaDone(result);
        }
    }

    public interface OnProcessListener<E extends MediaEntity> {

        /**
         * 操作开始
         */
        void onLoadMediaStart();

        /**
         * 操作结束
         *
         * @param result 多媒体操作结果
         */
        void onLoadMediaDone(MediaLoaderResult<E> result);
    }

    public interface Provider<E extends MediaEntity> {

        /**
         * 绑定多媒体检索器
         *
         * @return 多媒体检索器
         */
        MediaQuerier.Querier<E> provideQuerier();

        /**
         * 绑定多媒体分组器
         *
         * @return 多媒体检索器
         */
        MediaGrouper.Grouper<E> providerGrouper();
    }
}
