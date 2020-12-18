package com.windyziheng.mcmedialoader.sort.group.rule;

import com.windyziheng.mcmedialoader.entity.group.GroupEntity;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import java.util.Objects;

/**
 * 按照名称——关键字升降序排序的规则
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-11
 * @Organization Convergence Ltd.
 */
public final class KeyNameRule<T extends MediaEntity> extends SortGroupRule<T> {

    private boolean isAsc;

    public KeyNameRule() {
        this(true);
    }

    public KeyNameRule(boolean isAsc) {
        this.isAsc = isAsc;
    }

    @Override
    protected int onCompare(GroupEntity<T> group1, GroupEntity<T> group2) {
        String name1 = group1.getName();
        String name2 = group2.getName();
        if (Objects.equals(name1, name2)) {
            String key1 = group1.getKey();
            String key2 = group2.getKey();
            if (isAsc) {
                return key1.compareTo(key2);
            } else {
                return key2.compareTo(key1);
            }
        } else {
            if (isAsc) {
                return name1.compareTo(name2);
            } else {
                return name2.compareTo(name1);
            }
        }
    }
}
