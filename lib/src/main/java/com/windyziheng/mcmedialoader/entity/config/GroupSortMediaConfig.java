package com.windyziheng.mcmedialoader.entity.config;

import com.windyziheng.mcmedialoader.constant.Constant;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.sort.media.rule.SortMediaRule;

/**
 * 在分组完成后，对所有分组对象的多媒体列表按照统一的规则进行排序的配置
 *
 * @Author WangZiheng
 * @CreateDate 2020/12/14
 * @Organization Convergence Ltd.
 */
public class GroupSortMediaConfig<T extends MediaEntity> extends ActionConfig {

    protected SortMediaRule<T> sortMediaRule;

    public GroupSortMediaConfig() {
        super(Constant.DEFAULT_IS_GROUP_SORT_MEDIA_ACTION);
    }

    public SortMediaRule<T> getSortMediaRule() {
        return sortMediaRule != null ? sortMediaRule : SortMediaRule.createDefaultRule();
    }

    public void setSortMediaRule(SortMediaRule<T> sortMediaRule) {
        this.sortMediaRule = sortMediaRule;
    }
}
