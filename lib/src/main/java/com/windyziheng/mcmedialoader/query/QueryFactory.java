package com.windyziheng.mcmedialoader.query;

import android.content.Context;

import com.windyziheng.mcmedialoader.constant.QueryType;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import java.util.List;

/**
 * 本地存储的多媒体文件查询抽象工厂类
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-10
 * @Organization Convergence Ltd.
 */
public abstract class QueryFactory<E extends MediaEntity> {

    protected static volatile boolean isWorking = false;

    protected Context context;

    protected QueryFactory(Context context) {
        this.context = context;
    }

    /**
     * 创建默认的多媒体读取工厂实现类
     *
     * @param context   上下文
     * @param queryType 查询类型
     * @return 默认的多媒体读取工厂实现类
     */
    public static DefaultQueryFactory createDefaultQueryFactory(Context context, QueryType queryType) {
        return new DefaultQueryFactory.Creator(context, queryType).create();
    }

    /**
     * 初始化
     *
     * @return 是否成功
     */
    public boolean setup() {
        if (isWorking || !onSetup()) {
            return false;
        } else {
            isWorking = true;
            return true;
        }
    }

    /**
     * 查询本地存储获取多媒体文件
     *
     * @return 多媒体文件列表
     */
    public List<E> query() {
        return onQuery();
    }

    /**
     * 释放资源
     */
    public void release() {
        isWorking = false;
        onRelease();
    }

    /**
     * 重写此方法以进行初始化操作
     *
     * @return 默认传true才会进行下一步操作
     */
    protected abstract boolean onSetup();

    /**
     * 重写此方法以实现查询数据处理
     *
     * @return 多媒体文件列表
     */
    protected abstract List<E> onQuery();

    /**
     * 重写此方法以进行资源释放
     */
    protected abstract void onRelease();

    public static boolean isIsWorking() {
        return isWorking;
    }

    public interface OnQueryListener<E extends MediaEntity> {

        /**
         * 检索开始
         */
        void onQueryStart();

        /**
         * 检索成功
         *
         * @param mediaList 多媒体列表
         */
        void onQuerySuccess(List<E> mediaList);

        /**
         * 检索失败
         */
        void onQueryFail();
    }

    public interface SimpleQueryListener<E extends MediaEntity> {

        /**
         * 结果回调
         *
         * @param isSuccess 成功与否
         * @param result    若成功，返回结果列表；若失败，返回空
         */
        void onResult(boolean isSuccess, List<E> result);
    }
}
