package com.windyziheng.mcmedialoader.sort.media.rule;

import com.windyziheng.mcmedialoader.constant.SortRuleType;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.entity.media.VideoEntity;

/**
 * 按视频时长排序的规则
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-10
 * @Organization Convergence Ltd.
 */
public final class DurationRule<T extends MediaEntity> extends SortMediaRule<T> {

    private boolean isAsc;

    public DurationRule() {
        this(true);
    }

    public DurationRule(boolean isAsc) {
        super(SortRuleType.Video);
        this.isAsc = isAsc;
    }

    public void setAsc(boolean asc) {
        isAsc = asc;
    }

    @Override
    protected int onCompare(T media1, T media2) {
        if (media1 instanceof VideoEntity && media2 instanceof VideoEntity) {
            VideoEntity video1 = (VideoEntity) media1;
            VideoEntity video2 = (VideoEntity) media2;
            if (isAsc) {
                return (int) (video1.getDuration() - video2.getDuration());
            } else {
                return (int) (video2.getDuration() - video1.getDuration());
            }
        } else {
            return getMediaOrder(media1) - getMediaOrder(media2);
        }
    }
}
