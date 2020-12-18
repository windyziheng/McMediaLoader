package com.windyziheng.mcmedialoader.group.rule;

import androidx.annotation.NonNull;

import com.windyziheng.mcmedialoader.constant.Constant;
import com.windyziheng.mcmedialoader.constant.GroupType;
import com.windyziheng.mcmedialoader.constant.MediaType;
import com.windyziheng.mcmedialoader.entity.group.GroupEntity;
import com.windyziheng.mcmedialoader.entity.media.MediaEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据多媒体类型进行分组的规则
 *
 * @Author WangZiheng
 * @CreateDate 2020/12/11
 * @Organization Convergence Ltd.
 */
public final class TypeRule<E extends MediaEntity> extends GroupRule<E> {

    TypeRule() {
        super(GroupType.Type);
    }

    @Override
    public List<GroupEntity<E>> createGroups(@NonNull List<E> mediaList) {
        List<GroupEntity<E>> result = new ArrayList<>();
        if (mediaList.isEmpty()) {
            return result;
        }
        GroupEntity<E> groupAll = createAllGroup(mediaList);
        if (groupAll.isAvailable()){
            result.add(groupAll);
        }
        GroupEntity<E> groupImage = createImageGroup(mediaList);
        if (groupImage.isAvailable()){
            result.add(groupImage);
        }
        GroupEntity<E> groupVideo = createVideoGroup(mediaList);
        if (groupVideo.isAvailable()){
            result.add(groupVideo);
        }
        return result;
    }

    private GroupEntity<E> createTypeGroup(String key, List<E> mediaList) {
        return createGroup(key, key, mediaList);
    }

    private GroupEntity<E> createAllGroup(List<E> originList) {
        return createTypeGroup(Constant.GROUP_KEY_ALL, originList);
    }

    private GroupEntity<E> createImageGroup(List<E> originList) {
        List<E> imageList = new ArrayList<>();
        for (E media : originList) {
            if (media.getMediaType() == MediaType.Image) {
                imageList.add(media);
            }
        }
        return createTypeGroup(Constant.GROUP_KEY_IMAGE, imageList);
    }

    private GroupEntity<E> createVideoGroup(List<E> originList) {
        List<E> videoList = new ArrayList<>();
        for (E media : originList) {
            if (media.getMediaType() == MediaType.Video) {
                videoList.add(media);
            }
        }
        return createTypeGroup(Constant.GROUP_KEY_VIDEO, videoList);
    }
}
