package com.windyziheng.mcmedialoader.entity.config;

import com.windyziheng.mcmedialoader.constant.Constant;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.sort.media.rule.SortMediaRule;

/**
 * 在检索完成后，对多媒体列表按照指定规则进行排序的配置
 *
 * @Author WangZiheng
 * @CreateDate 2020/12/14
 * @Organization Convergence Ltd.
 */
public class QuerySortMediaConfig<T extends MediaEntity> extends ActionConfig {

    protected SortMediaRule<T> sortMediaRule;

    public QuerySortMediaConfig() {
        super(Constant.DEFAULT_IS_QUERY_SORT_MEDIA_ACTION);
    }

    public SortMediaRule<T> getSortMediaRule() {
        return sortMediaRule != null ? sortMediaRule : SortMediaRule.createDefaultRule();
    }

    public QuerySortMediaConfig<T> setSortMediaRule(SortMediaRule<T> sortMediaRule) {
        this.sortMediaRule = sortMediaRule;
        return this;
    }
}
