package com.windyziheng.mcmedialoader.entity.config;

import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

/**
 * 操作配置基类
 *
 * @Author WangZiheng
 * @CreateDate 2020/12/14
 * @Organization Convergence Ltd.
 */
public abstract class ActionConfig {

    protected boolean isAction;

    public ActionConfig(boolean isAction) {
        this.isAction = isAction;
    }

    /**
     * 创建检索成功后进行排序的默认配置
     *
     * @param <T> 多媒体类
     * @return 默认配置
     */
    public static <T extends MediaEntity> QuerySortMediaConfig<T> createDefaultQuerySortMediaConfig() {
        return new QuerySortMediaConfig<>();
    }

    /**
     * 创建分组成功后，按统一多媒体排序规则将所有分组类的多媒体列表进行排序的默认配置
     *
     * @param <T> 多媒体类
     * @return 默认配置
     */
    public static <T extends MediaEntity> GroupSortMediaConfig<T> createDefaultGroupSortMediaConfig() {
        return new GroupSortMediaConfig<>();
    }

    /**
     * 创建分组成功后，按统一分组排序规则将按目录路径分组的分组对象列表排序的默认配置
     *
     * @param <T> 多媒体类
     * @return 默认配置
     */
    public static <T extends MediaEntity> SortDirGroupConfig<T> createDefaultSortDirGroupConfig() {
        return new SortDirGroupConfig<>();
    }

    /**
     * 创建分组成功后，按统一分组排序规则将按自定义分组对象列表排序的默认配置
     *
     * @param <T> 多媒体类
     * @return 默认配置
     */
    public static <T extends MediaEntity> SortCustomGroupConfig<T> createDefaultSortCustomGroupConfig() {
        return new SortCustomGroupConfig<>();
    }

    public boolean isAction() {
        return isAction;
    }

    public void setAction(boolean action) {
        isAction = action;
    }
}
