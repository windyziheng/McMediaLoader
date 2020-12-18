package com.windyziheng.mcmedialoader.sort.media;

import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.sort.SortFactory;
import com.windyziheng.mcmedialoader.sort.media.rule.SortMediaRule;

import java.util.Collections;
import java.util.List;

/**
 * 多媒体列表排序的抽象工厂类
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-10
 * @Organization Convergence Ltd.
 */
public class MediaSortFactory<T extends MediaEntity> extends SortFactory<T> {

    protected SortMediaRule<T> rule;

    public MediaSortFactory(SortMediaRule<T> rule) {
        this.rule = rule;
    }

    @Override
    protected boolean onSetup() {
        return true;
    }

    @Override
    protected List<T> onSort(List<T> originList) {
        Collections.sort(originList, rule);
        return originList;
    }

    @Override
    protected void onRelease() {

    }
}
