package com.windyziheng.mcmedialoader.entity.group;

import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import java.util.List;

/**
 * 自定义分组封装
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-14
 * @Organization Convergence Ltd.
 */
public class CustomGroup<E extends MediaEntity> {

    private List<GroupEntity<E>> customGroupList;

    public CustomGroup(List<GroupEntity<E>> customGroupList) {
        this.customGroupList = customGroupList;
    }

    public void updateCustomGroupList(List<GroupEntity<E>> customGroupList) {
        if (customGroupList != null) {
            this.customGroupList.clear();
            this.customGroupList.addAll(customGroupList);
        }
    }

    public List<GroupEntity<E>> getCustomGroupList() {
        return customGroupList;
    }
}