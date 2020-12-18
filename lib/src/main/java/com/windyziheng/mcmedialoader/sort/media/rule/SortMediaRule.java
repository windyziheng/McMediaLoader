package com.windyziheng.mcmedialoader.sort.media.rule;

import com.windyziheng.mcmedialoader.constant.SortRuleType;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import java.util.Comparator;

/**
 * 多媒体排序规则抽象类，实现Comparator接口，子类均须重写onCompare方法
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-10
 * @Organization Convergence Ltd.
 */
public abstract class SortMediaRule<T extends MediaEntity> implements Comparator<T> {

    /**
     * 规则适用多媒体类型，默认通用
     */
    protected SortRuleType sortRuleType;

    public SortMediaRule() {
        this(SortRuleType.Common);
    }

    public SortMediaRule(SortRuleType sortRuleType) {
        this.sortRuleType = sortRuleType;
    }

    /**
     * 创建默认多媒体排序规则
     * 即按修改时间排序
     *
     * @param <T> 多媒体类
     * @return 默认多媒体排序规则
     */
    public static <T extends MediaEntity> SortMediaRule<T> createDefaultRule() {
        return createModifiedDateRule();
    }

    /**
     * 创建按修改时间排序的规则
     *
     * @param <T> 多媒体类
     * @return 按修改时间排序的规则
     */
    public static <T extends MediaEntity> ModifiedDateRule<T> createModifiedDateRule() {
        return new ModifiedDateRule<>();
    }

    /**
     * 创建按目录名排序的规则
     *
     * @param <T> 多媒体类
     * @return 按目录名排序的规则
     */
    public static <T extends MediaEntity> DirNameRule<T> createDirNameRule() {
        return new DirNameRule<>();
    }

    /**
     * 创建按文件名排序的规则
     *
     * @param <T> 多媒体类
     * @return 按修改时间排序的规则
     */
    public static <T extends MediaEntity> FileNameRule<T> createFileNameRule() {
        return new FileNameRule<>();
    }

    /**
     * 重写此方法以实现排序
     *
     * @param media1 对象1
     * @param media2 对象2
     * @return 比较结果
     */
    protected abstract int onCompare(T media1, T media2);

    public SortRuleType getSortRuleType() {
        return sortRuleType;
    }

    @Override
    public int compare(T media1, T media2) {
        if (media1 == null || media2 == null) {
            return 0;
        } else if (sortRuleType != SortRuleType.Common) {
            int order1 = getMediaOrder(media1);
            int order2 = getMediaOrder(media2);
            if (order1 == 1 && order2 == 1) {
                return onCompare(media1, media2);
            } else {
                return order1 - order2;
            }
        } else {
            return onCompare(media1, media2);
        }
    }

    protected int getMediaOrder(T media) {
        return sortRuleType.isTypeAvailable(media.getMediaType()) ? 1 : 0;
    }
}
