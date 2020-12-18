package com.windyziheng.mcmedialoader.query.info;

import android.provider.MediaStore;

import com.windyziheng.mcmedialoader.constant.MediaType;

/**
 * 本地图片查询信息
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-09
 * @Organization Convergence Ltd.
 */
public final class VideoQueryInfo extends QueryInfo {

    public VideoQueryInfo() {
        super(MediaType.Video, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, PROJECTION_VIDEO, SELECTION_ARGS_VIDEO);
    }
}
