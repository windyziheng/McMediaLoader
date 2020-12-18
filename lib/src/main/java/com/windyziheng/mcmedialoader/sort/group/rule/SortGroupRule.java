package com.windyziheng.mcmedialoader.sort.group.rule;

import com.windyziheng.mcmedialoader.entity.group.GroupEntity;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import java.util.Comparator;

/**
 * 分组对象排序规则抽象类，实现Comparator接口，子类均须重写onCompare方法
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-11
 * @Organization Convergence Ltd.
 */
public abstract class SortGroupRule<T extends MediaEntity> implements Comparator<GroupEntity<T>> {

    /**
     * 创建按照名称——关键字升降序排序的规则
     *
     * @param <E> 多媒体类
     * @return 分组排序规则
     */
    public static <E extends MediaEntity> KeyNameRule<E> createKeyNameRule() {
        return new KeyNameRule<>();
    }

    /**
     * 按照列表数量升降序排序的规则
     *
     * @param <E> 多媒体类
     * @return 分组排序规则
     */
    public static <E extends MediaEntity> ListSizeRule<E> createListSizeRule() {
        return new ListSizeRule<>();
    }

    /**
     * 重写此方法以实现排序
     *
     * @param group1 分组对象1
     * @param group2 分组对象2
     * @return 比较结果
     */
    protected abstract int onCompare(GroupEntity<T> group1, GroupEntity<T> group2);

    @Override
    public int compare(GroupEntity<T> group1, GroupEntity<T> group2) {
        if (group1 == null || group2 == null || !group1.isAvailable() || !group2.isAvailable()) {
            return 0;
        } else {
            return onCompare(group1, group2);
        }
    }
}
