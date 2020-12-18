package com.windyziheng.mcmedialoader.query.info;

import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.windyziheng.mcmedialoader.constant.MediaType;

/**
 * 本地多媒体数据查询信息基类
 *
 * @Author WangZiheng
 * @CreateDate 2020-12-09
 * @Organization Convergence Ltd.
 */
public abstract class QueryInfo {

    /**
     * 图片查询数据项
     */
    protected static final String[] PROJECTION_IMAGE = {
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DATE_MODIFIED,
            MediaStore.MediaColumns.SIZE,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.WIDTH,
            MediaStore.MediaColumns.HEIGHT
    };

    /**
     * 视频查询数据项
     */
    protected static final String[] PROJECTION_VIDEO = {
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DATE_MODIFIED,
            MediaStore.MediaColumns.SIZE,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.Video.VideoColumns.RESOLUTION,
            MediaStore.Video.VideoColumns.DURATION
    };

    /**
     * 图片查询关键字
     */
    protected static final String[] SELECTION_ARGS_IMAGE = {
            "image/jpeg",
            "image/png",
            "image/jpg"
    };

    /**
     * 视频查询关键字
     */
    protected static final String[] SELECTION_ARGS_VIDEO = {
            "video/mp4",
            "video/3gp",
            "video/avi",
            "video/rmvb",
            "video/vob",
            "video/flv",
            "video/mkv",
            "video/mov",
            "video/mpg"
    };

    protected static final String DEFAULT_ORDER_ATTR = MediaStore.MediaColumns.DATE_MODIFIED;
    protected static final boolean DEFAULT_ORDER_IS_ASC = false;
    protected static final String DEFAULT_ORDER = formatOrder(DEFAULT_ORDER_ATTR, DEFAULT_ORDER_IS_ASC);

    protected MediaType mediaType;
    protected Uri uri;
    protected String[] projection;
    protected String[] selectionArgs;
    protected String order;
    protected boolean isAvailable;

    public QueryInfo(MediaType mediaType, Uri uri, String[] projection, String[] selectionArgs) {
        this(mediaType, uri, projection, selectionArgs, DEFAULT_ORDER);
    }

    public QueryInfo(MediaType mediaType, Uri uri, String[] projection, String[] selectionArgs, String order) {
        this.mediaType = mediaType;
        this.uri = uri;
        this.projection = projection;
        this.selectionArgs = selectionArgs;
        this.order = order;
        isAvailable = uri != null && !TextUtils.isEmpty(order) &&
                projection.length > 0 && selectionArgs.length > 0;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public Uri getUri() {
        return uri;
    }

    public String[] getProjection() {
        return projection;
    }

    public String getSelection() {
        StringBuilder stringBuilder = new StringBuilder();
        if (selectionArgs.length > 0) {
            for (int i = 0; i < selectionArgs.length; i++) {
                if (i > 0) {
                    stringBuilder.append(" or ");
                }
                stringBuilder.append(MediaStore.MediaColumns.MIME_TYPE).append("=?");
            }
        }
        return stringBuilder.toString();
    }

    public String[] getSelectionArgs() {
        return selectionArgs;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String attr, boolean isAsc) {
        order = formatOrder(attr, isAsc);
    }

    protected static String formatOrder(String attr, boolean isAsc) {
        return attr + " " + (isAsc ? "ASC" : "DESC");
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
