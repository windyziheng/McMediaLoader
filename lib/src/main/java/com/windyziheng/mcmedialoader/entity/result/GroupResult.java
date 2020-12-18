package com.windyziheng.mcmedialoader.entity.result;

import android.text.TextUtils;
import android.util.ArrayMap;

import androidx.annotation.Nullable;

import com.windyziheng.mcmedialoader.constant.Constant;
import com.windyziheng.mcmedialoader.entity.group.CustomGroup;
import com.windyziheng.mcmedialoader.entity.group.GroupEntity;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 对多媒体文件列表分组后的输出结果
 *
 * @Author WangZiheng
 * @CreateDate 2020/12/13
 * @Organization Convergence Ltd.
 */
public class GroupResult<T extends MediaEntity> {

    protected List<T> mediaList;
    protected List<GroupEntity<T>> typeGroupList;
    protected List<GroupEntity<T>> dirGroupList;
    protected ArrayMap<String, CustomGroup<T>> customGroupMap;

    protected boolean isDirGroupLast = Constant.DEFAULT_IS_RESULT_DIR_GROUP_LAST;

    private GroupResult(Builder<T> builder) {
        mediaList = builder.mediaList;
        typeGroupList = new ArrayList<>(builder.typeGroupList);
        dirGroupList = new ArrayList<>(builder.dirGroupList);
        customGroupMap = new ArrayMap<>(builder.customGroupMap);
    }

    /**
     * 设置按目录路径分组的分组对象列表是否在最后，即自定义分组居中
     */
    public GroupResult<T> setDirGroupLast(boolean dirGroupLast) {
        isDirGroupLast = dirGroupLast;
        return this;
    }

    /**
     * 获取初始多媒体列表
     *
     * @return 初始多媒体列表
     */
    public List<T> getMediaList() {
        return mediaList;
    }

    /**
     * 获取所有分组对象列表
     *
     * @return 所有分组对象列表
     */
    public List<GroupEntity<T>> getAllGroupList() {
        List<GroupEntity<T>> allGroupList = new ArrayList<>(typeGroupList);
        if (isDirGroupLast) {
            allGroupList.addAll(getAllCustomGroupList());
            allGroupList.addAll(dirGroupList);
        } else {
            allGroupList.addAll(dirGroupList);
            allGroupList.addAll(getAllCustomGroupList());
        }
        return allGroupList;
    }

    /**
     * 获取所有分组对象的“关键字——分组对象”ArrayMap
     *
     * @return 所有分组对象的“关键字——分组对象”ArrayMap
     */
    public ArrayMap<String, GroupEntity<T>> getAllGroupMap() {
        ArrayMap<String, GroupEntity<T>> allGroupMap = new ArrayMap<>();
        List<GroupEntity<T>> allGroupList = getAllGroupList();
        if (allGroupList.isEmpty()) {
            return allGroupMap;
        }
        for (GroupEntity<T> group : allGroupList) {
            allGroupMap.put(group.getKey(), group);
        }
        return allGroupMap;
    }

    /**
     * 获取按多媒体类型分组的分组对象列表
     *
     * @return 按多媒体类型分组的分组对象列表
     */
    public List<GroupEntity<T>> getTypeGroupList() {
        return typeGroupList;
    }

    /**
     * 获取按目录路径类型分组的分组对象列表
     *
     * @return 按目录路径类型分组的分组对象列表
     */
    public List<GroupEntity<T>> getDirGroupList() {
        return dirGroupList;
    }

    /**
     * 获取所有自定义分组对象列表
     *
     * @return 所有自定义分组对象列表
     */
    public List<GroupEntity<T>> getAllCustomGroupList() {
        List<GroupEntity<T>> customGroupList = new ArrayList<>();
        if (customGroupMap.isEmpty()) {
            return customGroupList;
        }
        for (int i = 0; i < customGroupMap.size(); i++) {
            CustomGroup<T> customGroup = customGroupMap.valueAt(i);
            customGroupList.addAll(customGroup.getCustomGroupList());
        }
        return customGroupList;
    }

    /**
     * 获取自定义分组ArrayMap
     *
     * @return 自定义分组ArrayMap
     */
    public ArrayMap<String, CustomGroup<T>> getCustomGroupMap() {
        return customGroupMap;
    }

    /**
     * 根据添加顺序获取自定义分组对象列表
     *
     * @param index 添加顺序
     * @return 自定义分组对象列表
     */
    public List<GroupEntity<T>> getCustomGroupListByIndex(int index) {
        return getCustomGroupList(getCustomGroup(index));
    }

    /**
     * 根据关键字获取自定义分组对象列表
     *
     * @param key 关键字
     * @return 自定义分组对象列表
     */
    public List<GroupEntity<T>> getCustomGroupListByKey(String key) {
        return getCustomGroupList(getCustomGroup(key));
    }

    /**
     * 更新按多媒体类型分组的分组对象列表
     *
     * @param typeGroupList 多媒体类型分组对象列表（不要传入由本对象获取分组列表）
     */
    public void updateTypeGroupList(List<GroupEntity<T>> typeGroupList) {
        if (typeGroupList != null) {
            this.typeGroupList.clear();
            this.typeGroupList.addAll(typeGroupList);
        }
    }

    /**
     * 更新按目录路径分组的分组对象列表
     *
     * @param dirGroupList 目录路径类型分组对象列表（不要传入由本对象获取分组列表）
     */
    public void updateDirGroupList(List<GroupEntity<T>> dirGroupList) {
        if (dirGroupList != null) {
            this.dirGroupList.clear();
            this.dirGroupList.addAll(dirGroupList);
        }
    }

    /**
     * 根据添加序号更新自定义分组对象列表
     *
     * @param index           添加序号
     * @param customGroupList 自定义分组对象列表（不要传入由本对象获取分组列表）
     */
    public void updateCustomGroupList(int index, List<GroupEntity<T>> customGroupList) {
        updateCustomGroupList(getCustomGroup(index), customGroupList);
    }

    /**
     * 根据关键字更新自定义分组对象列表
     *
     * @param key             关键字
     * @param customGroupList 自定义分组对象列表（不要传入由本对象获取分组列表）
     */
    public void updateCustomGroupList(String key, List<GroupEntity<T>> customGroupList) {
        updateCustomGroupList(getCustomGroup(key), customGroupList);
    }

    /**
     * 获取自定义分组的数量
     *
     * @return 自定义分组的数量
     */
    public int getCustomGroupSize() {
        return customGroupMap.size();
    }

    /**
     * 根据添加顺序获取自定义分组
     *
     * @param index 添加顺序
     * @return 获取自定义分组
     */
    private CustomGroup<T> getCustomGroup(int index) {
        if (customGroupMap.isEmpty()) {
            return null;
        } else {
            return index >= 0 && index < customGroupMap.size() ? customGroupMap.valueAt(index) : null;
        }
    }

    /**
     * 根据关键字获取自定义分组
     *
     * @param key 关键字
     * @return 获取自定义分组
     */
    private CustomGroup<T> getCustomGroup(String key) {
        if (customGroupMap.isEmpty()) {
            return null;
        } else {
            return !TextUtils.isEmpty(key) ? customGroupMap.get(key) : null;
        }
    }

    /**
     * 获取自定义分组对象列表
     *
     * @param customGroup 自定义分组
     * @return 自定义分组对象列表
     */
    private List<GroupEntity<T>> getCustomGroupList(CustomGroup<T> customGroup) {
        if (customGroup == null) {
            return new ArrayList<>();
        } else {
            return customGroup.getCustomGroupList();
        }
    }

    /**
     * 更新自定义分组对象列表
     *
     * @param customGroup     自定义分组
     * @param customGroupList 自定义分组对象列表（不要传入由本对象获取分组列表）
     */
    private void updateCustomGroupList(CustomGroup<T> customGroup, List<GroupEntity<T>> customGroupList) {
        if (customGroup != null) {
            customGroup.updateCustomGroupList(customGroupList);
        }
    }

    public static class Builder<E extends MediaEntity> {

        private List<E> mediaList;
        private List<GroupEntity<E>> typeGroupList;
        private List<GroupEntity<E>> dirGroupList;
        private ArrayMap<String, CustomGroup<E>> customGroupMap;

        public Builder(List<E> mediaList) {
            this.mediaList = mediaList;
            typeGroupList = new ArrayList<>();
            dirGroupList = new ArrayList<>();
            customGroupMap = new ArrayMap<>();
        }

        /**
         * 设置按多媒体类型分组的分组对象列表
         */
        public Builder<E> setTypeGroupList(@Nullable List<GroupEntity<E>> typeGroupList) {
            this.typeGroupList.clear();
            if (typeGroupList != null && !typeGroupList.isEmpty()) {
                this.typeGroupList.addAll(typeGroupList);
            }
            return this;
        }

        /**
         * 设置按目录路径分组的分组对象列表
         */
        public Builder<E> setDirGroupList(@Nullable List<GroupEntity<E>> dirGroupList) {
            this.dirGroupList.clear();
            if (dirGroupList != null && !dirGroupList.isEmpty()) {
                this.dirGroupList.addAll(dirGroupList);
            }
            return this;
        }

        /**
         * 添加自定义分组列表
         */
        public Builder<E> addCustomGroupList(String key, List<GroupEntity<E>> customGroupList) {
            if (TextUtils.isEmpty(key) || customGroupList == null || customGroupList.isEmpty()) {
                return this;
            }
            customGroupMap.put(key, new CustomGroup<>(customGroupList));
            return this;
        }

        public GroupResult<E> build() {
            return new GroupResult<>(this);
        }
    }
}
