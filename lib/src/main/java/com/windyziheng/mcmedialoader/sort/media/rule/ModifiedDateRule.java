package com.windyziheng.mcmedialoader.sort.media.rule;

import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

/**
 * 按修改时间排序的规则
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-10
 * @Organization Convergence Ltd.
 */
public final class ModifiedDateRule<T extends MediaEntity> extends SortMediaRule<T> {

    private boolean isAsc;

    public ModifiedDateRule() {
        this(false);
    }

    public ModifiedDateRule(boolean isAsc) {
        super();
        this.isAsc = isAsc;
    }

    public void setAsc(boolean asc) {
        isAsc = asc;
    }

    @Override
    protected int onCompare(T media1, T media2) {
        if (isAsc) {
            return (int) (media1.getModifiedDate() - media2.getModifiedDate());
        } else {
            return (int) (media2.getModifiedDate() - media1.getModifiedDate());
        }
    }
}
