package com.windyziheng.mcmedialoader.entity.group;

import android.text.TextUtils;

import com.windyziheng.mcmedialoader.constant.GroupType;
import com.windyziheng.mcmedialoader.constant.MediaType;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import java.util.List;

/**
 * 分组对象，封装关键字，关键信息及关键字对应的多媒体列表
 * 注意，最好不要被外部类继承
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-11
 * @Organization Convergence Ltd.
 */
public class GroupEntity<T extends MediaEntity> {

    private GroupType groupType;
    private String key;
    private String name;
    private List<T> originMediaList;
    private List<T> sortedMediaList;
    private boolean isAvailable = false;

    public GroupEntity(GroupType groupType, String key, String name, List<T> originList) {
        this.groupType = groupType;
        this.key = key;
        this.name = name;
        this.originMediaList = originList;
        if (TextUtils.isEmpty(key) || originList == null || originList.isEmpty()) {
            return;
        }
        if (TextUtils.isEmpty(name)) {
            this.name = key;
        }
        isAvailable = true;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public GroupInfo<T> getGroupInfo() {
        if (!isAvailable) {
            return null;
        } else if (sortedMediaList == null || sortedMediaList.isEmpty()) {
            return new GroupInfo<>(key, name, originMediaList);
        } else {
            return new GroupInfo<>(key, name, sortedMediaList);
        }
    }

    public List<T> getMediaList() {
        return isSorted() ? getSortedMediaList() : getOriginMediaList();
    }

    private List<T> getOriginMediaList() {
        return originMediaList;
    }

    private List<T> getSortedMediaList() {
        return sortedMediaList;
    }

    private boolean isSorted() {
        return sortedMediaList != null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateSortedMediaList(List<T> sortedMediaList) {
        this.sortedMediaList = sortedMediaList;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public static class GroupInfo<E extends MediaEntity> {

        private String key;
        private String name;
        private E coverEntity;
        private MediaType coverType;
        private int count;

        private GroupInfo(String key, String name, List<E> list) {
            this.key = key;
            this.name = name;
            coverEntity = list.get(0);
            coverType = coverEntity.getMediaType();
            count = list.size();
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public E getCoverEntity() {
            return coverEntity;
        }

        public MediaType getCoverType() {
            return coverType;
        }

        public int getCount() {
            return count;
        }
    }
}
