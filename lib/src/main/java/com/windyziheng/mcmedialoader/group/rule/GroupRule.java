package com.windyziheng.mcmedialoader.group.rule;

import androidx.annotation.NonNull;

import com.windyziheng.mcmedialoader.constant.GroupType;
import com.windyziheng.mcmedialoader.entity.group.GroupEntity;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import java.util.List;

/**
 * 分组规则
 * 子类构造方法均不公布，构造由类方法完成
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-11
 * @Organization Convergence Ltd.
 */
public abstract class GroupRule<T extends MediaEntity> {

    protected GroupType groupType;

    protected GroupRule(GroupType groupType) {
        this.groupType = groupType;
    }

    public static <E extends MediaEntity> TypeRule<E> createTypeRule() {
        return new TypeRule<>();
    }

    public static <E extends MediaEntity> DirRule<E> createCommonRule() {
        return new DirRule<>();
    }

    /**
     * 创建分组对象的抽象方法
     *
     * @param mediaList 初始多媒体列表
     * @return 根据按照规则将多媒体列表区分的分组结果
     */
    public abstract List<GroupEntity<T>> createGroups(@NonNull List<T> mediaList);

    /**
     * 创建分组对象
     *
     * @param key       查询关键字
     * @param name      分组名
     * @param mediaList 多媒体列表
     * @return 分组对象
     */
    protected GroupEntity<T> createGroup(String key, String name, List<T> mediaList) {
        return new GroupEntity<>(groupType, key, name, mediaList);
    }
}
