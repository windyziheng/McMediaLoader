package com.windyziheng.mcmedialoader.group;

import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.entity.result.GroupResult;
import com.windyziheng.mcmedialoader.group.rule.CustomRule;
import com.windyziheng.mcmedialoader.group.rule.DirRule;
import com.windyziheng.mcmedialoader.group.rule.GroupRule;
import com.windyziheng.mcmedialoader.group.rule.TypeRule;

import java.util.ArrayList;
import java.util.List;

/**
 * 分组工厂类，非抽象，不允许继承，仅允许通过新增自定义分组来实现自定义
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-11
 * @Organization Convergence Ltd.
 */
public final class GroupFactory<T extends MediaEntity> {

    private boolean isGroupByType;
    private boolean isGroupByDir;
    private List<CustomRule<T>> customRuleList;
    private TypeRule<T> typeRule;
    private DirRule<T> dirRule;

    private GroupFactory(Creator<T> creator) {
        isGroupByType = creator.isGroupByType;
        isGroupByDir = creator.isGroupByDir;
        customRuleList = creator.customRuleList;
        typeRule = GroupRule.createTypeRule();
        dirRule = GroupRule.createCommonRule();
    }

    /**
     * 初始化
     *
     * @return 是否成功
     */
    boolean setup() {
        return true;
    }

    /**
     * 分组
     *
     * @param mediaList 待分组的多媒体列表
     * @return 分组后的分组对象列表
     */
    GroupResult<T> group(List<T> mediaList) {
        GroupResult.Builder<T> builder = new GroupResult.Builder<>(mediaList);
        if (isGroupByType) {
            builder.setTypeGroupList(typeRule.createGroups(mediaList));
        }
        if (isGroupByDir) {
            builder.setDirGroupList(dirRule.createGroups(mediaList));
        }
        if (!customRuleList.isEmpty()) {
            for (CustomRule<T> customRule : customRuleList) {
                builder.addCustomGroupList(customRule.getKey(), customRule.createGroups(mediaList));
            }
        }
        return builder.build();
    }

    /**
     * 释放资源
     */
    void release() {

    }

    /**
     * 设置是否添加按照文件类型分组（所有多媒体分组、所有图片分组、所有视频分组）
     */
    public GroupFactory<T> setIsGroupByType(boolean isGroupByType) {
        this.isGroupByType = isGroupByType;
        return this;
    }

    /**
     * 设置是否添加按照文件目录路径分组（key为目录路径，name为目录名，name可后期自行修改）
     */
    public GroupFactory<T> setIsGroupByDir(boolean isGroupByDir) {
        this.isGroupByDir = isGroupByDir;
        return this;
    }

    /**
     * 添加自定义分组规则
     *
     * @param rule 自定义分组规则
     */
    public GroupFactory<T> addCustomRule(CustomRule<T> rule) {
        customRuleList.add(rule);
        return this;
    }

    /**
     * 清除所有自定义分组规则
     */
    public GroupFactory<T> clearCustomRule() {
        customRuleList.clear();
        return this;
    }

    public static class Creator<E extends MediaEntity> {

        private boolean isGroupByType = true;
        private boolean isGroupByDir = true;
        private List<CustomRule<E>> customRuleList;

        public Creator() {
            customRuleList = new ArrayList<>();
        }

        /**
         * 设置是否添加按照文件类型分组（所有多媒体分组、所有图片分组、所有视频分组）
         */
        public Creator<E> setIsGroupByType(boolean isGroupByType) {
            this.isGroupByType = isGroupByType;
            return this;
        }

        /**
         * 设置是否添加按照文件目录路径分组（key为目录路径，name为目录名，name可后期自行修改）
         */
        public Creator<E> setIsGroupByDir(boolean isGroupByDir) {
            this.isGroupByDir = isGroupByDir;
            return this;
        }

        /**
         * 添加自定义分组规则
         * 注意key值不要与
         */
        public Creator<E> addCustomRule(CustomRule<E> rule) {
            customRuleList.add(rule);
            return this;
        }

        public GroupFactory<E> create() {
            return new GroupFactory<>(this);
        }
    }

    public interface OnGroupListener<E extends MediaEntity> {

        /**
         * 分组开始
         */
        void onGroupStart();

        /**
         * 分组成功
         *
         * @param groupResult 分组结果
         */
        void onGroupSuccess(GroupResult<E> groupResult);

        /**
         * 分组失败
         */
        void onGroupFail();
    }
}
