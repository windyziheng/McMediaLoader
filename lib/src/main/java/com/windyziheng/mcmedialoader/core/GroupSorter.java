package com.windyziheng.mcmedialoader.core;

import androidx.annotation.NonNull;

import com.windyziheng.mcmedialoader.entity.group.GroupEntity;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.sort.SortFactory;
import com.windyziheng.mcmedialoader.sort.group.GroupSortFactory;
import com.windyziheng.mcmedialoader.sort.group.GroupSortTask;
import com.windyziheng.mcmedialoader.sort.group.rule.SortGroupRule;

import java.lang.reflect.Array;
import java.util.List;

/**
 * 分组排序器
 *
 * @Author WangZiheng
 * @CreateDate 2020/12/12
 * @Organization Convergence Ltd.
 */
public class GroupSorter<T extends MediaEntity> {

    protected GroupSorter() {

    }

    /**
     * 创建分组排序操作执行类
     *
     * @param rule 分组排序规则
     * @param <E>  多媒体类
     * @return 分组排序操作执行类
     */
    public static <E extends MediaEntity> Sorter<E> createSorter(SortGroupRule<E> rule) {
        return new Sorter<>(rule);
    }

    /**
     * 分组排序操作执行类
     *
     * @param <Y> 多媒体类
     */
    public static class Sorter<Y extends MediaEntity> {

        private GroupSortFactory<Y> sortFactory;

        private Sorter(@NonNull SortGroupRule<Y> rule) {
            sortFactory = new GroupSortFactory<>(rule);
        }

        /**
         * 执行排序操作
         *
         * @param groupList 分组对象列表
         * @param listener  排序监听
         */
        public void sort(@NonNull List<GroupEntity<Y>> groupList, @NonNull SortFactory.OnSortListener<GroupEntity<Y>> listener) {
            GroupSortTask<Y> groupSortTask = new GroupSortTask<>(sortFactory, listener);
            GroupEntity<MediaEntity>[] groups = (GroupEntity<MediaEntity>[]) Array.newInstance(GroupEntity.class, groupList.size());
            for (int i = 0; i < groupList.size(); i++) {
                groups[i] = (GroupEntity<MediaEntity>) groupList.get(i);
            }
            groupSortTask.execute((GroupEntity<Y>[]) groups);
        }
    }
}
