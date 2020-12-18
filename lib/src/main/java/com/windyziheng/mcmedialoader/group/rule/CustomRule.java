package com.windyziheng.mcmedialoader.group.rule;

import com.windyziheng.mcmedialoader.constant.GroupType;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

/**
 * 自定义多媒体分组规则
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-11
 * @Organization Convergence Ltd.
 */
public abstract class CustomRule<E extends MediaEntity> extends GroupRule<E> {

    private String key;

    public CustomRule(String key) {
        super(GroupType.Custom);
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
