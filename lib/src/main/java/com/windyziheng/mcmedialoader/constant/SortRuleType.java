package com.windyziheng.mcmedialoader.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 排序规则适用类型
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-10
 * @Organization Convergence Ltd.
 */
public enum SortRuleType {

    //通用
    Common(MediaType.Image, MediaType.Video),
    //仅图片
    Image(MediaType.Image),
    //仅视频
    Video(MediaType.Video);

    private List<MediaType> availableTypeList;

    SortRuleType(MediaType... mediaTypes) {
        availableTypeList = new ArrayList<>();
        availableTypeList.addAll(Arrays.asList(mediaTypes));
    }

    public boolean isTypeAvailable(MediaType mediaType) {
        return availableTypeList.contains(mediaType);
    }
}
