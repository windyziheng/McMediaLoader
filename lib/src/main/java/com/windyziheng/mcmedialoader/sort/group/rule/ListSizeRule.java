package com.windyziheng.mcmedialoader.sort.group.rule;

import com.windyziheng.mcmedialoader.entity.group.GroupEntity;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

/**
 * 按照列表数量升降序排序的规则
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-11
 * @Organization Convergence Ltd.
 */
public final class ListSizeRule<E extends MediaEntity> extends SortGroupRule<E> {

    private boolean isAsc;

    public ListSizeRule() {
        this(false);
    }

    public ListSizeRule(boolean isAsc) {
        this.isAsc = isAsc;
    }

    @Override
    protected int onCompare(GroupEntity<E> group1, GroupEntity<E> group2) {
        int size1 = group1.getGroupInfo().getCount();
        int size2 = group2.getGroupInfo().getCount();
        if (isAsc) {
            return size1 - size2;
        } else {
            return size2 - size1;
        }
    }
}
