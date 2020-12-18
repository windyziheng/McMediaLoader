package com.windyziheng.mcmedialoader.sort.group;

import com.windyziheng.mcmedialoader.entity.group.GroupEntity;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.sort.SortFactory;
import com.windyziheng.mcmedialoader.sort.group.rule.SortGroupRule;

import java.util.Collections;
import java.util.List;

/**
 * 分组对象排序的工厂抽象类
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-11
 * @Organization Convergence Ltd.
 */
public class GroupSortFactory<T extends MediaEntity> extends SortFactory<GroupEntity<T>> {

    protected SortGroupRule<T> rule;

    public GroupSortFactory(SortGroupRule<T> rule) {
        this.rule = rule;
    }

    @Override
    protected boolean onSetup() {
        return true;
    }

    @Override
    protected List<GroupEntity<T>> onSort(List<GroupEntity<T>> originList) {
        Collections.sort(originList, rule);
        return originList;
    }

    @Override
    protected void onRelease() {

    }
}
