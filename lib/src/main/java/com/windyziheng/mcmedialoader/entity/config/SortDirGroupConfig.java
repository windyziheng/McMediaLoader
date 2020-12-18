package com.windyziheng.mcmedialoader.entity.config;

import com.windyziheng.mcmedialoader.constant.Constant;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.sort.group.rule.SortGroupRule;

/**
 * 在分组完成后，对按目录路径分组的分组对象按照指定规则进行排序
 *
 * @Author WangZiheng
 * @CreateDate 2020/12/14
 * @Organization Convergence Ltd.
 */
public class SortDirGroupConfig<T extends MediaEntity> extends ActionConfig {

    private SortGroupRule<T> sortGroupRule;

    public SortDirGroupConfig() {
        super(Constant.DEFAULT_IS_SORT_DIR_GROUP_ACTION);
    }

    public SortGroupRule<T> getSortGroupRule() {
        return sortGroupRule != null ? sortGroupRule : SortGroupRule.createKeyNameRule();
    }

    public void setSortGroupRule(SortGroupRule<T> sortGroupRule) {
        this.sortGroupRule = sortGroupRule;
    }
}
