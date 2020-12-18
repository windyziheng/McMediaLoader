package com.windyziheng.mcmedialoader.core;

import androidx.annotation.NonNull;

import com.windyziheng.mcmedialoader.core.method.GroupMethod;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.entity.result.GroupResult;
import com.windyziheng.mcmedialoader.group.GroupFactory;
import com.windyziheng.mcmedialoader.sort.group.rule.SortGroupRule;
import com.windyziheng.mcmedialoader.sort.media.rule.SortMediaRule;

import java.util.List;

/**
 * 多媒体分组器
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-10
 * @Organization Convergence Ltd.
 */
public class MediaGrouper {

    private MediaGrouper() {

    }

    /**
     * 创建分组操作执行类
     *
     * @param groupFactory 分组工厂类
     * @param <T>          多媒体类
     * @return 分组操作执行类
     */
    public static <T extends MediaEntity> Grouper<T> createGrouper(GroupFactory<T> groupFactory) {
        return new Grouper<>(groupFactory);
    }

    /**
     * 创建默认分组操作执行类
     *
     * @param <T> 多媒体类
     * @return 分组操作执行类
     */
    public static <T extends MediaEntity> Grouper<T> createDefaultGrouper() {
        return new Grouper<>(createDefaultFactory());
    }

    /**
     * 创建默认分组工厂类
     *
     * @param <T> 多媒体类
     * @return 分组工厂类
     */
    public static <T extends MediaEntity> GroupFactory<T> createDefaultFactory() {
        return new GroupFactory.Creator<T>().create();
    }

    /**
     * 分组操作执行类
     *
     * @param <E> 多媒体类
     */
    public static class Grouper<E extends MediaEntity> {

        private GroupMethod<E> groupMethod;

        private Grouper(@NonNull GroupFactory<E> groupFactory) {
            groupMethod = new GroupMethod<>(groupFactory);
        }

        /**
         * 设置检索完成后，对多媒体列表按照指定规则进行排序的操作是否执行
         *
         * @param isAction 是否执行
         * @return 当前对象
         */
        public MediaGrouper.Grouper<E> setGroupSortMediaIsAction(boolean isAction) {
            groupMethod.setGroupSortMediaIsAction(isAction);
            return this;
        }

        /**
         * 设置分组完成后，对按目录路径分组的分组对象按照指定规则进行排序的操作是否执行
         *
         * @param isAction 是否执行
         * @return 当前对象
         */
        public MediaGrouper.Grouper<E> setSortDirGroupIsAction(boolean isAction) {
            groupMethod.setSortDirGroupIsAction(isAction);
            return this;
        }

        /**
         * 设置分组完成后，对按自定义分组对象按照指定规则进行排序的操作是否执行
         *
         * @param isAction 是否执行
         * @return 当前对象
         */
        public MediaGrouper.Grouper<E> setSortCustomGroupIsAction(boolean isAction) {
            groupMethod.setSortCustomGroupIsAction(isAction);
            return this;
        }

        /**
         * 设置检索完成后，对多媒体列表按照指定规则进行排序的排序规则
         *
         * @param rule 多媒体排序规则
         * @return 当前对象
         */
        public MediaGrouper.Grouper<E> setGroupSortMediaRule(SortMediaRule<E> rule) {
            groupMethod.setGroupSortMediaRule(rule);
            return this;
        }

        /**
         * 设置分组完成后，对按目录路径分组的分组对象按照指定规则进行排序的排序规则
         *
         * @param rule 分组排序规则
         * @return 当前对象
         */
        public MediaGrouper.Grouper<E> setSortDirGroupRule(SortGroupRule<E> rule) {
            groupMethod.setSortDirGroupRule(rule);
            return this;
        }

        /**
         * 设置分组完成后，对按自定义分组对象按照指定规则进行排序的排序规则
         *
         * @param rule 分组排序规则
         * @return 当前对象
         */
        public MediaGrouper.Grouper<E> setSortCustomGroupRule(SortGroupRule<E> rule) {
            groupMethod.setSortCustomGroupRule(rule);
            return this;
        }

        /**
         * 执行分组操作
         *
         * @param mediaList 多媒体列表
         * @param callback  分组回调
         */
        public void group(@NonNull List<E> mediaList, @NonNull MediaGrouper.GroupCallback<E> callback) {
            groupMethod.group(mediaList, callback);
        }

        /**
         * 分组完成后，按同一多媒体排序规则将所有分组类的多媒体列表进行排序
         *
         * @param groupResult 分组结果
         */
        public void sortMedia(GroupResult<E> groupResult) {
            groupMethod.sortMedia(groupResult);
        }

        /**
         * 分组完成后，对按目录路径分组的分组对象列表进行排序
         *
         * @param groupResult 分组结果
         */
        public void sortDirGroup(GroupResult<E> groupResult) {
            groupMethod.sortDirGroup(groupResult);
        }

        /**
         * 分组完成后，对所有自定义分组的分组对象列表按照统一规则排序
         *
         * @param groupResult 分组结果
         */
        public void sortCustomGroup(GroupResult<E> groupResult) {
            groupMethod.sortCustomGroup(groupResult);
        }
    }

    public interface GroupCallback<E extends MediaEntity> {

        /**
         * 分组开始
         */
        void onGroupStart();

        /**
         * 分组成功
         *
         * @param groupResult 分组结果
         */
        void onGroupSuccess(GroupResult<E> groupResult);

        /**
         * 分组失败
         */
        void onGroupFail();
    }

    public interface OnUnitListener {

        void onUnitDone();
    }
}
