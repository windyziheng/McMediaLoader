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
public final class ImageQueryInfo extends QueryInfo {

    public ImageQueryInfo() {
        super(MediaType.Image, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, PROJECTION_IMAGE, SELECTION_ARGS_IMAGE);
    }
}
