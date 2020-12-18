package com.windyziheng.mcmedialoader.sort.media.rule;

import android.text.TextUtils;

import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

/**
 * 按文件名排序的规则
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-10
 * @Organization Convergence Ltd.
 */
public final class FileNameRule<T extends MediaEntity> extends SortMediaRule<T> {

    private boolean isAsc;

    public FileNameRule() {
        this(true);
    }

    public FileNameRule(boolean isAsc) {
        super();
        this.isAsc = isAsc;
    }

    public void setAsc(boolean asc) {
        isAsc = asc;
    }

    @Override
    protected int onCompare(T media1, T media2) {
        String name1 = media1.getName();
        String name2 = media2.getName();
        if (TextUtils.isEmpty(name1) || TextUtils.isEmpty(name2)) {
            return 0;
        }
        if (isAsc) {
            return name1.compareTo(name2);
        } else {
            return name2.compareTo(name1);
        }
    }
}
