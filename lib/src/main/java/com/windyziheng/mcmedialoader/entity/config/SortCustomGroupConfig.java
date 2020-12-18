package com.windyziheng.mcmedialoader.entity.config;

import com.windyziheng.mcmedialoader.constant.Constant;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.sort.group.rule.SortGroupRule;

/**
 * 在分组完成后，对按自定义分组对象按照指定规则进行排序
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-15
 * @Organization Convergence Ltd.
 */
public class SortCustomGroupConfig<T extends MediaEntity> extends ActionConfig {

    private SortGroupRule<T> sortGroupRule;

    public SortCustomGroupConfig() {
        super(Constant.DEFAULT_IS_SORT_CUSTOM_GROUP_ACTION);
    }

    public SortGroupRule<T> getSortGroupRule() {
        return sortGroupRule != null ? sortGroupRule : SortGroupRule.createListSizeRule();
    }

    public void setSortGroupRule(SortGroupRule<T> sortGroupRule) {
        this.sortGroupRule = sortGroupRule;
    }
}
