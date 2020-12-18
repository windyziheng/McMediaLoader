package com.windyziheng.mcmedialoader.core.method;

import android.util.ArrayMap;

import androidx.annotation.NonNull;

import com.windyziheng.mcmedialoader.core.GroupSorter;
import com.windyziheng.mcmedialoader.core.MediaGrouper;
import com.windyziheng.mcmedialoader.core.MediaSorter;
import com.windyziheng.mcmedialoader.entity.config.ActionConfig;
import com.windyziheng.mcmedialoader.entity.config.GroupSortMediaConfig;
import com.windyziheng.mcmedialoader.entity.config.SortCustomGroupConfig;
import com.windyziheng.mcmedialoader.entity.config.SortDirGroupConfig;
import com.windyziheng.mcmedialoader.entity.group.CustomGroup;
import com.windyziheng.mcmedialoader.entity.group.GroupEntity;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;
import com.windyziheng.mcmedialoader.entity.result.GroupResult;
import com.windyziheng.mcmedialoader.group.GroupFactory;
import com.windyziheng.mcmedialoader.group.GroupTask;
import com.windyziheng.mcmedialoader.sort.group.rule.SortGroupRule;
import com.windyziheng.mcmedialoader.sort.media.rule.SortMediaRule;

import java.lang.reflect.Array;
import java.util.List;

/**
 * 分组操作封装类
 *
 * @Author WangZiheng
 * @CreateDate 2020/12/17
 * @Organization Convergence Ltd.
 */
public class GroupMethod<T extends MediaEntity> {

    private GroupFactory<T> groupFactory;
    private GroupSortMediaConfig<T> groupSortMediaConfig = ActionConfig.createDefaultGroupSortMediaConfig();
    private SortDirGroupConfig<T> sortDirGroupConfig = ActionConfig.createDefaultSortDirGroupConfig();
    private SortCustomGroupConfig<T> sortCustomGroupConfig = ActionConfig.createDefaultSortCustomGroupConfig();

    public GroupMethod(GroupFactory<T> groupFactory) {
        this.groupFactory = groupFactory;
    }

    /**
     * 设置检索完成后，对多媒体列表按照指定规则进行排序的操作是否执行
     *
     * @param isAction 是否执行
     */
    public void setGroupSortMediaIsAction(boolean isAction) {
        groupSortMediaConfig.setAction(isAction);
    }

    /**
     * 设置分组完成后，对按目录路径分组的分组对象按照指定规则进行排序的操作是否执行
     *
     * @param isAction 是否执行
     */
    public void setSortDirGroupIsAction(boolean isAction) {
        sortDirGroupConfig.setAction(isAction);
    }

    /**
     * 设置分组完成后，对按自定义分组对象按照指定规则进行排序的操作是否执行
     *
     * @param isAction 是否执行
     */
    public void setSortCustomGroupIsAction(boolean isAction) {
        sortCustomGroupConfig.setAction(isAction);
    }

    /**
     * 设置检索完成后，对多媒体列表按照指定规则进行排序的排序规则
     *
     * @param rule 多媒体排序规则
     */
    public void setGroupSortMediaRule(SortMediaRule<T> rule) {
        groupSortMediaConfig.setSortMediaRule(rule);
    }

    /**
     * 设置分组完成后，对按目录路径分组的分组对象按照指定规则进行排序的排序规则
     *
     * @param rule 分组排序规则
     */
    public void setSortDirGroupRule(SortGroupRule<T> rule) {
        sortDirGroupConfig.setSortGroupRule(rule);
    }

    /**
     * 设置分组完成后，对按自定义分组对象按照指定规则进行排序的排序规则
     *
     * @param rule 分组排序规则
     */
    public void setSortCustomGroupRule(SortGroupRule<T> rule) {
        sortCustomGroupConfig.setSortGroupRule(rule);
    }

    /**
     * 执行分组操作
     *
     * @param mediaList 多媒体列表
     * @param callback  分组回调
     */
    public void group(@NonNull List<T> mediaList, @NonNull MediaGrouper.GroupCallback<T> callback) {
        if (mediaList.isEmpty()) {
            callback.onGroupFail();
            return;
        }
        GroupTask<T> groupTask = new GroupTask<>(groupFactory, new GroupFactory.OnGroupListener<T>() {
            @Override
            public void onGroupStart() {
                callback.onGroupStart();
            }

            @Override
            public void onGroupSuccess(GroupResult<T> groupResult) {
                List<GroupEntity<T>> allGroupList = groupResult.getAllGroupList();
                if (!allGroupList.isEmpty()) {
                    sortMedia(groupResult, callback);
                } else {
                    callback.onGroupSuccess(groupResult);
                }
            }

            @Override
            public void onGroupFail() {
                callback.onGroupFail();
            }
        });
        MediaEntity[] medias = (MediaEntity[]) Array.newInstance(MediaEntity.class, mediaList.size());
        for (int i = 0; i < mediaList.size(); i++) {
            medias[i] = mediaList.get(i);
        }
        groupTask.execute((T[]) medias);
    }

    /**
     * 分组完成后，按同一多媒体排序规则将所有分组类的多媒体列表进行排序
     *
     * @param groupResult 分组结果
     */
    public void sortMedia(GroupResult<T> groupResult) {
        sortMedia(groupResult, null);
    }

    /**
     * 分组完成后，对按目录路径分组的分组对象列表进行排序
     *
     * @param groupResult 分组结果
     */
    public void sortDirGroup(GroupResult<T> groupResult) {
        sortDirGroup(groupResult, null);
    }

    /**
     * 分组完成后，对所有自定义分组的分组对象列表按照统一规则排序
     *
     * @param groupResult 分组结果
     */
    public void sortCustomGroup(GroupResult<T> groupResult) {
        sortCustomGroup(groupResult, null);
    }

    /**
     * 分组完成后，按同一多媒体排序规则将所有分组类的多媒体列表进行排序
     *
     * @param groupResult 分组结果
     * @param callback    分组回调
     */
    private void sortMedia(GroupResult<T> groupResult, MediaGrouper.GroupCallback<T> callback) {
        boolean isAfterGroup = callback != null;
        boolean isAction = !isAfterGroup || groupSortMediaConfig.isAction();
        List<GroupEntity<T>> allGroupList = groupResult.getAllGroupList();
        if (isAction && !allGroupList.isEmpty()) {
            MediaSorter.Sorter<T> sorter = MediaSorter.createSorter(groupSortMediaConfig.getSortMediaRule());
            sortMediaUnit(sorter, allGroupList, 0, () -> sortDirGroup(groupResult, callback));
        } else {
            sortDirGroup(groupResult, callback);
        }
    }

    /**
     * 分组完成后，对按目录路径分组的分组对象列表进行排序
     *
     * @param groupResult 分组结果
     * @param callback    分组回调
     */
    private void sortDirGroup(GroupResult<T> groupResult, MediaGrouper.GroupCallback<T> callback) {
        boolean isAfterGroup = callback != null;
        boolean isAction = !isAfterGroup || sortDirGroupConfig.isAction();
        List<GroupEntity<T>> dirGroupList = groupResult.getDirGroupList();
        if (isAction && !dirGroupList.isEmpty()) {
            GroupSorter.Sorter<T> sorter = GroupSorter.createSorter(sortDirGroupConfig.getSortGroupRule());
            sorter.sort(dirGroupList, (isSuccess, resultList) -> {
                if (isSuccess) {
                    groupResult.updateDirGroupList(resultList);
                }
                sortCustomGroup(groupResult, callback);
            });
        } else {
            sortCustomGroup(groupResult, callback);
        }
    }

    /**
     * 分组完成后，对所有自定义分组的分组对象列表按照统一规则排序
     *
     * @param groupResult 分组结果
     * @param callback    分组回调
     */
    private void sortCustomGroup(GroupResult<T> groupResult, MediaGrouper.GroupCallback<T> callback) {
        boolean isAfterGroup = callback != null;
        boolean isAction = !isAfterGroup || sortCustomGroupConfig.isAction();
        ArrayMap<String, CustomGroup<T>> customGroupMap = groupResult.getCustomGroupMap();
        if (isAction && !customGroupMap.isEmpty()) {
            GroupSorter.Sorter<T> sorter = GroupSorter.createSorter(sortCustomGroupConfig.getSortGroupRule());
            sortCustomGroupUnit(sorter, customGroupMap, 0, () -> {
                if (callback != null) {
                    callback.onGroupSuccess(groupResult);
                }
            });
        } else {
            if (callback != null) {
                callback.onGroupSuccess(groupResult);
            }
        }
    }

    private void sortMediaUnit(MediaSorter.Sorter<T> sorter, List<GroupEntity<T>> groupList,
                               int i, MediaGrouper.OnUnitListener listener) {
        if (i >= groupList.size()) {
            listener.onUnitDone();
        } else {
            GroupEntity<T> group = groupList.get(i);
            List<T> mediaList = group.getMediaList();
            if (!mediaList.isEmpty()) {
                sorter.sort(group.getMediaList(), (isSuccess, resultList) -> {
                    if (isSuccess) {
                        group.updateSortedMediaList(resultList);
                    }
                    sortMediaUnit(sorter, groupList, i + 1, listener);
                });
            } else {
                sortMediaUnit(sorter, groupList, i + 1, listener);
            }
        }
    }

    private void sortCustomGroupUnit(GroupSorter.Sorter<T> sorter, ArrayMap<String, CustomGroup<T>> customGroupMap,
                                     int i, MediaGrouper.OnUnitListener listener) {
        if (i >= customGroupMap.size()) {
            listener.onUnitDone();
        } else {
            CustomGroup<T> customGroup = customGroupMap.valueAt(i);
            List<GroupEntity<T>> groupList = customGroup.getCustomGroupList();
            if (!groupList.isEmpty()) {
                sorter.sort(customGroup.getCustomGroupList(), (isSuccess, resultList) -> {
                    if (isSuccess) {
                        customGroup.updateCustomGroupList(resultList);
                    }
                    sortCustomGroupUnit(sorter, customGroupMap, i + 1, listener);
                });
            } else {
                sortCustomGroupUnit(sorter, customGroupMap, i + 1, listener);
            }
        }
    }
}
