package com.windyziheng.mcmedialoader.group.rule;

import androidx.annotation.NonNull;

import com.windyziheng.mcmedialoader.constant.GroupType;
import com.windyziheng.mcmedialoader.entity.group.GroupEntity;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 根据目录路径进行多媒体分组的规则
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-11
 * @Organization Convergence Ltd.
 */
public final class DirRule<E extends MediaEntity> extends GroupRule<E> {

    DirRule() {
        super(GroupType.Dir);
    }

    @Override
    public List<GroupEntity<E>> createGroups(@NonNull List<E> mediaList) {
        List<GroupEntity<E>> result = new ArrayList<>();
        if (mediaList.isEmpty()) {
            return result;
        }
        //先遍历多媒体列表，获取"关键字——分组名"Map
        Map<String, String> keyNameMap = new HashMap<>();
        for (E media : mediaList) {
            String key = media.getParentPath();
            String name = media.getParentName();
            keyNameMap.put(key, name);
        }
        //遍历"关键字——分组名"Map
        for (Map.Entry<String, String> entry : keyNameMap.entrySet()) {
            String key = entry.getKey();
            String name = entry.getValue();
            //每一组关键字，都遍历一次初始列表，获取符合条件的列表并创建分组
            List<E> eligibleList = new ArrayList<>();
            for (E media : mediaList) {
                if (Objects.equals(key, media.getParentPath())) {
                    eligibleList.add(media);
                }
            }
            GroupEntity<E> group = createGroup(key, name, eligibleList);
            if (group.isAvailable()){
                result.add(group);
            }
        }
        return result;
    }
}
