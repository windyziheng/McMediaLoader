package com.windyziheng.mcmedialoader.sort.media.rule;

import android.text.TextUtils;

import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

/**
 * 按文件夹名排序的规则
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-10
 * @Organization Convergence Ltd.
 */
public final class DirNameRule<T extends MediaEntity> extends SortMediaRule<T> {

    private boolean isAsc;

    public DirNameRule() {
        this(true);
    }

    public DirNameRule(boolean isAsc) {
        super();
        this.isAsc = isAsc;
    }

    public void setAsc(boolean asc) {
        isAsc = asc;
    }

    @Override
    protected int onCompare(T media1, T media2) {
        String parentName1 = media1.getParentName();
        String parentName2 = media2.getParentName();
        if (TextUtils.isEmpty(parentName1) || TextUtils.isEmpty(parentName2)) {
            return 0;
        }
        if (isAsc) {
            return parentName1.compareTo(parentName2);
        } else {
            return parentName2.compareTo(parentName1);
        }
    }
}
